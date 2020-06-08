package com.tangcz.springbootdemo.demo6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UploadController {

    @RequestMapping(value = "/upload.html")
    public Object getUploadPage() {
        return "upload";
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("head_img")MultipartFile file, HttpServletRequest request) {
        String name = request.getParameter("name");
        System.out.println("用户名：" + name);

        String filename = file.getOriginalFilename();
        System.out.println("文件名：" + filename);

        return filename;
    }

}
