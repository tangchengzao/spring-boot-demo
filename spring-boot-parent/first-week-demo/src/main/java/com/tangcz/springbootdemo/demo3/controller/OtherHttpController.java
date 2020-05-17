package com.tangcz.springbootdemo.demo3.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OtherHttpController {

    private Map<String, Object> params = new HashMap<>();

    @PostMapping("/v1/login")
    public Object login(String id, String pwd) {
        params.clear();
        params.put("id", id);
        params.put("pwd", pwd);
        return params;
    }

    @PutMapping("/v1/put")
    public Object put(String id) {
        params.clear();
        params.put("id", id);
        return params;
    }

}
