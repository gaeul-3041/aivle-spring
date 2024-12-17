package com.example.news.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String password;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @JsonBackReference
    private News news;
}
