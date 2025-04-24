package com.project_lms.lms.service;

import com.project_lms.lms.dto.BookDTO;
import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
}