package com.dlq.cache.mapper;

import com.dlq.cache.bean.Department;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper {

    @Select("SELECT * FROM department WHERE id=#{id}")
    Department getDeptById(Integer id);
}
