package com.dlq.deploy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-19 17:38
 */
@Controller
public class HelloController {

    @GetMapping("/abc")
    public String hello(){
        return "hello";
    }
}
