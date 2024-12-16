package com.example.news.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    @GetMapping("/news/new")
    public String newArticleForm() {
        return "news/new";
    }
}
