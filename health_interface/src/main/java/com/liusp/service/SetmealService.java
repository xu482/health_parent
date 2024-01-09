package com.liusp.service;
import com.liusp.entity.PageResult;
import com.liusp.pojo.Setmeal;

/**
 * 体检套餐服务接口
 */
public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void edit(Setmeal setmeal);

    void delete(Integer id);
}
