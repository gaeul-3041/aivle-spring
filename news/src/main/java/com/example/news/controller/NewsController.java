package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NewsController {
    private final NewsRepository newsRepository;

    private final NewsMapper mapper;  // 의존성 주입(DI)

    @GetMapping("/news/new")
    public String newArticleForm() {
        return "news/new";
    }

    @PostMapping("/news/create")
    public String createNews(NewsDto.Post post) {
        News news = mapper.newsPostDtoToNews(post);
        newsRepository.save(news);
        return "";
    }
}
