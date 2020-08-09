package com.dlq.cache;

import com.dlq.cache.bean.Employee;
import com.dlq.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class Springboot01CacheApplicationTests {

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate; //操作k-v都是字符串

	@Autowired
	RedisTemplate redisTemplate; 	//k-v都是对象的

	@Autowired
	RedisTemplate<Object, Employee> empRedisTemplate;

	/**
	 * Redis常见的五大数据类型
	 * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
	 * stringRedisTemplate.opsForValue()【String（字符串）】
	 * stringRedisTemplate.opsForList()【List（列表）】
	 * stringRedisTemplate.opsForSet()【Set（集合）】
	 * stringRedisTemplate.opsForHash()【Hash（散列）】
	 * stringRedisTemplate.opsForZSet()【ZSet（有序集合）】
	 */
	@Test
	public void test01(){
		//给Redis中保存了一个数据
//		stringRedisTemplate.opsForValue().append("msg","hello");
//		String msg = stringRedisTemplate.opsForValue().get("msg");
//		System.out.println(msg);
		stringRedisTemplate.opsForList().leftPush("mylist","1");
		stringRedisTemplate.opsForList().leftPush("mylist","2");
		stringRedisTemplate.opsForList().leftPush("mylist","3");
		stringRedisTemplate.opsForList().leftPush("mylist","4");
	}

	@Test
	public void test02(){
		Employee empById = employeeMapper.getEmpById(1);
//		redisTemplate.opsForValue().set("emp-01",empById);
		empRedisTemplate.opsForValue().set("emp-01",empById );
	}

	@Test
	void contextLoads() {
		Employee empById = employeeMapper.getEmpById(1);
		System.out.println(empById);
	}

}
