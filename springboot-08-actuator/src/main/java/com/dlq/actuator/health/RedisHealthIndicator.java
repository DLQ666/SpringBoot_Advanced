package com.dlq.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-19 19:40
 */
@Component
public class RedisHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        //自定义的检查方法
        //Health.up().build() 代表健康
        return Health.down().withDetail("msg","服务异常").build();
    }
}
