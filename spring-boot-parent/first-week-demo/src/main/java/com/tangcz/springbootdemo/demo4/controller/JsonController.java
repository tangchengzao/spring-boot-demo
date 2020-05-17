package com.tangcz.springbootdemo.demo4.controller;

import com.tangcz.springbootdemo.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JsonController {

    @RequestMapping("/testjson")
    public Object testJson() {
        return new User(11, "123456", null, new Date());
    }

}
