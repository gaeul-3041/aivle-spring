package com.example.news.controller;

import com.example.news.domain.Comment;
import com.example.news.domain.News;
import com.example.news.dto.CommentDto;
import com.example.news.mapper.CommentMapper;
import com.example.news.repository.CommentRepository;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    @PostMapping("/create/{newsId}")
    public String createComment(@PathVariable("newsId") Long newsId, CommentDto.Post post) {
        News findNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 뉴스가 존재하지 않습니다."));

        Comment comment = mapper.commentDtoToEntity(post);
        comment.setNews(findNews);
        commentRepository.save(comment);

        return "redirect:/news/" + newsId;
    }

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, @RequestParam("password") String password) {
        Comment findComment = findVerifiedComment(commentId);
        News findNews = findComment.getNews();

        if(!findComment.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        commentRepository.delete(findComment);

        return "redirect:/news/" + findNews.getNewsId();
    }

    public Comment findVerifiedComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."));
    }
}
