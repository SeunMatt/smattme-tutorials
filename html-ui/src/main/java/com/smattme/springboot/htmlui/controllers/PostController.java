package com.smattme.springboot.htmlui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/post")
    public String singlePost() {
        return "front/post";
    }
}
