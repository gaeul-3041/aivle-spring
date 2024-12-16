package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NewsController {
    private final NewsRepository newsRepository;

    @GetMapping("/news/new")
    public String newArticleForm() {
        return "news/new";
    }

    @PostMapping
    public String createNews(NewsDto.Post post) {
        News news = News.toEntity(post);
        newsRepository.save(news);
        return "";
    }
}
