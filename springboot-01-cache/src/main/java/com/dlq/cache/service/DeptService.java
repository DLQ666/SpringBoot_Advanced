package com.dlq.cache.service;

import com.dlq.cache.bean.Department;
import com.dlq.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-09 18:00
 */
@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Cacheable(cacheNames = "dept")
    public Department getDeptById(Integer id){
        System.out.println("查询部门:"+id);
        Department deptById = departmentMapper.getDeptById(id);
        return deptById;
    }
}
