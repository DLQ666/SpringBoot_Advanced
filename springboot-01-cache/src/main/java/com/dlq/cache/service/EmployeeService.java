package com.dlq.cache.service;

import com.dlq.cache.bean.Employee;
import com.dlq.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-08 15:08
 */
@CacheConfig(cacheNames="emp"/*,cacheManager = "employeeCacheManager"*/) //抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不用调用方法；
     *   几个属性：
     *      cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
     *
     *      key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值  1-方法的返回值
     *              编写SpEL； #id;参数id的值   #a0  #p0  #root.args[0]
     *              getEmp[2]
     *
     *      keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     *              key/keyGenerator：二选一使用;
     *
     *
     *      cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
     *
     *      condition：指定符合条件的情况下才缓存；
     *              ,condition = "#id>0"
     *          condition = "#a0>1"：第一个参数的值》1的时候才进行缓存
     *
     *      unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
     *              unless = "#result == null"
     *              unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
     *      sync：是否使用异步模式
     * @param id
     * @return
     */
    @Cacheable(cacheNames = {"emp"}/*,keyGenerator = "mykeyGenerator",condition = "#a0>1",unless = "#a0==2"*/)
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee empById = employeeMapper.getEmpById(id);
        return empById;
    }

    /**
     * @CachePut：既调用方法，又更新缓存数据；同步更新缓存
     * 修改了数据库的某个数据，同时更新缓存；
     */
    @CachePut(value = "emp", key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }
    /**
     * @CacheEvict：缓存清除
     */
    @CacheEvict(/*value = "emp",*/ key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
//       employeeMapper.deleteEmpById(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(/*value = "emp",*/ key = "#lastName")
            },
            put = {
                    @CachePut(/*value = "emp",*/key = "#result.id"),
                    @CachePut(/*value = "emp",*/key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
