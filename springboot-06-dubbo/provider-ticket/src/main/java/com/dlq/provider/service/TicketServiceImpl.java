package com.dlq.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-19 12:01
 */
@Component
@DubboService //将服务发布出去
public class TicketServiceImpl implements TicketService{

    @Override
    public String getTicket() {
        return "《厉害了，我的国》";
    }
}
