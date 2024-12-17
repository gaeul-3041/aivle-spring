package com.example.book.mapper;

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
