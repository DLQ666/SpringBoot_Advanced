package com.dlq.provider.service;

import org.springframework.stereotype.Service;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-19 16:15
 */
@Service
public class TickerService {

    public String getTicket(){
        System.out.println("8803");
        return "《厉害了，我的国》-8803";
    }
}
