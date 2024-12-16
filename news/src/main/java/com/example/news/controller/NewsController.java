package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/news/{newsId}")
    public String getNews(@PathVariable("newsId") Long newsId, Model model) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));  // Optional 객체의 if-else 간결화
        model.addAttribute("news", news);
        return "news/detail";
    }
}
