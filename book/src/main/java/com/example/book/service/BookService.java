package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.dto.BookDTO;
import com.example.book.mapper.BookControlMapper;
import com.example.book.mapper.BookResponseMapper;
import com.example.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookControlMapper controlMapper;
    private final BookResponseMapper responseMapper;

    public Book insertBook(BookDTO.Post dto) {
        Book book = controlMapper.PostDTOToEntity(dto);
        return saveBook(book);
    }

    public Book updateBook(Long id, BookDTO.Put dto) {
        Book book = findVerifiedBook(id);
        controlMapper.PutDTOToEntity(dto, book);
        return saveBook(book);
    }

    public Book updateBookStatus(Long id, BookDTO.Patch dto) {
        Book book = findVerifiedBook(id);
        controlMapper.PatchDTOToEntity(dto, book);
        return saveBook(book);
    }

    public void deleteBook(Long id) {
        Book book = findVerifiedBook(id);
        if(book.getStatus() == Book.Status.BORROWED) {
            throw new IllegalArgumentException("대출 중인 책입니다.");
        }
        bookRepository.delete(book);
    }

    public BookDTO.Response findBook(Long id) {
        Book book = findVerifiedBook(id);
        return responseMapper.entityToResponse(book);
    }

    public List<BookDTO.Response> findBooks() {
        List<Book> books = bookRepository.findAll();
        return responseMapper.booksToResponses(books);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findVerifiedBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));
    }
}
