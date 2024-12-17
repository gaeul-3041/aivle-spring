package com.example.news.mapper;

import com.example.news.domain.Comment;
import com.example.news.dto.CommentDto;
import com.example.news.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "news", ignore = true)
    Comment commentDtoToEntity(CommentDto.Post post);
}
