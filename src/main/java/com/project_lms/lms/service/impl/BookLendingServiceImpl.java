package com.project_lms.lms.service.impl;

import com.project_lms.lms.dto.BookLendingDTO;
import com.project_lms.lms.entity.Book;
import com.project_lms.lms.entity.BookLending;
import com.project_lms.lms.entity.User;
import com.project_lms.lms.repo.BookLendingRepository;
import com.project_lms.lms.repo.BookRepository;
import com.project_lms.lms.repo.UserRepository;
import com.project_lms.lms.service.BookLendingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookLendingServiceImpl implements BookLendingService {
    private final BookLendingRepository bookLendingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookLendingServiceImpl(BookLendingRepository bookLendingRepository,
                                BookRepository bookRepository,
                                UserRepository userRepository) {
        this.bookLendingRepository = bookLendingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public BookLendingDTO issueBook(BookLendingDTO bookLendingDTO) {
        Book book = bookRepository.findById(bookLendingDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(bookLendingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is not available for lending");
        }

        BookLending lending = new BookLending();
        lending.setBook(book);
        lending.setUser(user);
        lending.setIssueDate(LocalDateTime.now());
        lending.setDueDate(bookLendingDTO.getDueDate() != null ? bookLendingDTO.getDueDate() : LocalDateTime.now().plusDays(14));
        lending.setStatus("ISSUED");

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        lending = bookLendingRepository.save(lending);
        return convertToDTO(lending);
    }

    @Override
    @Transactional
    public BookLendingDTO returnBook(Long lendingId) {
        BookLending lending = bookLendingRepository.findById(lendingId)
                .orElseThrow(() -> new RuntimeException("Lending record not found"));

        if ("RETURNED".equals(lending.getStatus())) {
            throw new RuntimeException("Book already returned");
        }

        Book book = lending.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);

        lending.setReturnDate(LocalDateTime.now());
        lending.setStatus("RETURNED");
        lending = bookLendingRepository.save(lending);

        return convertToDTO(lending);
    }

    @Override
    public List<BookLendingDTO> getLendingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookLendingRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLendingDTO> getLendingsByBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookLendingRepository.findByBook(book).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLendingDTO> getAllLendings() {
        return bookLendingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookLendingDTO getLendingById(Long id) {
        BookLending lending = bookLendingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lending record not found"));
        return convertToDTO(lending);
    }

    private BookLendingDTO convertToDTO(BookLending lending) {
        BookLendingDTO dto = new BookLendingDTO();
        dto.setId(lending.getId());
        dto.setBookId(lending.getBook().getId());
        dto.setUserId(lending.getUser().getId());
        dto.setIssueDate(lending.getIssueDate());
        dto.setDueDate(lending.getDueDate());
        dto.setReturnDate(lending.getReturnDate());
        dto.setStatus(lending.getStatus());
        
        // Set book details
        dto.setBookTitle(lending.getBook().getTitle());
        dto.setBookAuthor(lending.getBook().getAuthor());
        
        // Set user details
        dto.setUserName(lending.getUser().getName());
        dto.setUserEmail(lending.getUser().getEmail());
        
        return dto;
    }
}