package com.dlq.cache.controller;

import com.dlq.cache.bean.Department;
import com.dlq.cache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-09 18:05
 */
@RestController
public class DeptController {

    @Autowired
    DeptService deptService;

    @GetMapping("/dept/{id}")
    public Department getDept(@PathVariable("id") Integer id){
        Department deptById = deptService.getDeptById(id);
        return deptById;
    }
}
