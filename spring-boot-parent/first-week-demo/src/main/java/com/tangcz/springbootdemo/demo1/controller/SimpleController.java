package com.tangcz.springbootdemo.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@Controller
@RestController
public class SimpleController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World";
    }

    @RequestMapping("/test")
    public Map<String, String> testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "tangcz");
        return map;
    }

}
