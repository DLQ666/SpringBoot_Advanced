package com.dlq.provider.controller;

import com.dlq.provider.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-19 16:16
 */
@RestController
public class TickerController {

    @Autowired
    TickerService tickerService;

    @GetMapping("/ticket")
    public String getTicket(){
        return tickerService.getTicket();
    }
}
