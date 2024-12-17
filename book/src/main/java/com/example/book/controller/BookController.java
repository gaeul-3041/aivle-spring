package com.example.book.controller;

import com.example.book.domain.Book;
import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    public final BookService bookService;

    @GetMapping
    public List<BookDTO.Response> getBooks() {
        return bookService.findBooks();
    }

    @GetMapping("/{bookId}")
    public BookDTO.Response getBook(@PathVariable("bookId") Long id) {
        return bookService.findBook(id);
    }

    @PostMapping
    public Book insertBook(@Valid @RequestBody BookDTO.Post dto) {
        return bookService.insertBook(dto);
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable("bookId") Long id, @Valid @RequestBody BookDTO.Put dto) {
        return bookService.updateBook(id, dto);
    }

    @PatchMapping("/{bookId}")
    public Book updateBookStatus(@PathVariable("bookId") Long id, @Valid @RequestBody BookDTO.Patch dto) {
        return bookService.updateBookStatus(id, dto);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long id) {
        bookService.deleteBook(id);
    }
}
