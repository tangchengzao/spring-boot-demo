package com.tangcz.springbootdemo.demo5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileController {

    @RequestMapping(value = "/index.html")
    public Object index() {
        return "index";
    }

}
