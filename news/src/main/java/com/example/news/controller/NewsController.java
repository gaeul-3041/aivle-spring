package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsRepository newsRepository;

    private final NewsMapper mapper;  // 의존성 주입(DI)

    @GetMapping("/new")
    public String newArticleForm() {
        return "news/new";
    }

    @PostMapping("/create")
    public String createNews(NewsDto.Post post) {
        News news = mapper.newsPostDtoToNews(post);
        newsRepository.save(news);
        return "redirect:/news/" + news.getNewsId();
    }

    @GetMapping("/{newsId}")
    public String getNews(@PathVariable("newsId") Long newsId, Model model) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));  // Optional 객체의 if-else 간결화
        model.addAttribute("news", news);
        return "news/detail";
    }

    @GetMapping("/list")
    public String getNewsList(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<News> newsPage = newsRepository.findAll(pageable);
        model.addAttribute("newsPage", newsPage);

        model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", newsPage.hasNext());
        model.addAttribute("hasPrev", newsPage.hasPrevious());

        return "news/list";

        // 페이지 번호별 버튼은 일단 패스
    }

    @GetMapping("/{newsId}/delete")
    public String deleteNews(@PathVariable("newsId") Long newsId) {
        newsRepository.deleteById(newsId);
        return "redirect:/news/list";
    }
}