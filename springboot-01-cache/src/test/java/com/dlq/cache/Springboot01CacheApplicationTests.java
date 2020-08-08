package com.dlq.cache;

import com.dlq.cache.bean.Employee;
import com.dlq.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot01CacheApplicationTests {

	@Autowired
	EmployeeMapper employeeMapper;

	@Test
	void contextLoads() {
		Employee empById = employeeMapper.getEmpById(1);
		System.out.println(empById);
	}

}
