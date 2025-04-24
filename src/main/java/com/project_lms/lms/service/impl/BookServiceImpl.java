package com.project_lms.lms.service.impl;

import com.project_lms.lms.dto.BookDTO;
import com.project_lms.lms.entity.Book;
import com.project_lms.lms.repo.BookRepository;
import com.project_lms.lms.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        book.setAvailableQuantity(bookDTO.getQuantity());
        book = bookRepository.save(book);
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        BeanUtils.copyProperties(bookDTO, book);
        book.setId(id);
        book = bookRepository.save(book);
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookDTO bookDTO = new BookDTO();
                    BeanUtils.copyProperties(book, bookDTO);
                    return bookDTO;
                })
                .collect(Collectors.toList());
    }
}