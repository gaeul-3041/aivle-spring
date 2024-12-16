package com.example.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HelloUserController {
    @GetMapping("/hello")
    public String helloUser(Model model) {
        model.addAttribute("userName", "홍길동");
        return "news/helloUser";
    }
}