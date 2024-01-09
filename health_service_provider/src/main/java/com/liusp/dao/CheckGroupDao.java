package com.liusp.dao;
import com.github.pagehelper.Page;
import com.liusp.pojo.CheckGroup;

import java.util.List;
import java.util.Map;
/**
 * 持久层Dao接口
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(Map map);

    Page<CheckGroup> selectByCondition(String queryString);
    CheckGroup findById(Integer id);
    void edit(CheckGroup checkGroup);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    void deleteAssociation(Integer id);
    List<CheckGroup> findAll();

    void deleteById(Integer id);
}