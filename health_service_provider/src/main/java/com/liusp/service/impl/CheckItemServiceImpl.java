package com.liusp.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liusp.dao.CheckItemDao;
import com.liusp.entity.PageResult;
import com.liusp.pojo.CheckItem;
import com.liusp.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    public CheckItemDao checkItemDao;

    //新增
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //完成分页查询，基于myBatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //删除
    public void delete(Integer id) throws RuntimeException{
        //查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count > 0){
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    //编辑
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }
}
