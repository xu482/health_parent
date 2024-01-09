package com.liusp.controller;

import com.liusp.constant.MessageConstant;
import com.liusp.constant.RedisConstant;
import com.liusp.entity.PageResult;
import com.liusp.entity.QueryPageBean;
import com.liusp.entity.Result;
import com.liusp.pojo.CheckGroup;
import com.liusp.pojo.Setmeal;
import com.liusp.service.SetmealService;
import com.liusp.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    StringRedisTemplate redisTemplate;  //操作k-v都是字符串的

    @Reference
    private SetmealService setmealService;

    //图片上传
    @RequestMapping("/upload.do")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
            result.setData(fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //新增
    @RequestMapping("/add.do")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            //新增套餐失败
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        //新增套餐成功
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    //分页查询
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }
    //编辑
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody Setmeal setmeal){
        try {
            setmealService.edit(setmeal);
        }catch (Exception e){
            return new Result(false,"编辑失败");
        }
        return new Result(true,"编辑成功");
    }
    //删除
    @RequestMapping("/delete.do")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
        } catch (Exception e) {
            return new Result(false,"删除失败");
        }
        return new Result(true,"删除成功");
    }
}