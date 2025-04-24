package com.project_lms.lms.controller;

import com.project_lms.lms.dto.BookDTO;
import com.project_lms.lms.dto.BookLendingDTO;
import com.project_lms.lms.dto.UserDTO;
import com.project_lms.lms.service.BookLendingService;
import com.project_lms.lms.service.BookService;
import com.project_lms.lms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookLendingController {
    private final BookLendingService bookLendingService;
    private final BookService bookService;
    private final UserService userService;

    public BookLendingController(BookLendingService bookLendingService, BookService bookService, UserService userService) {
        this.bookLendingService = bookLendingService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/lendings")
    public String listLendings(Model model) {
        List<BookLendingDTO> lendings = bookLendingService.getAllLendings();
        model.addAttribute("lendings", lendings);
        return "lendings/list";
    }

    @GetMapping("/lendings/new")
    public String newLendingForm(Model model) {
        List<BookDTO> availableBooks = bookService.getAllBooks().stream()
            .filter(book -> book.getAvailableQuantity() > 0)
            .toList();
        List<UserDTO> activeUsers = userService.getAllUsers().stream()
            .filter(user -> "ACTIVE".equals(user.getMembershipStatus()))
            .toList();

        model.addAttribute("lending", new BookLendingDTO());
        model.addAttribute("books", availableBooks);
        model.addAttribute("users", activeUsers);
        return "lendings/form";
    }

    @PostMapping("/lendings/save")
    public String saveLending(@ModelAttribute BookLendingDTO lendingDTO) {
        if (lendingDTO.getId() == null) {
            bookLendingService.issueBook(lendingDTO);
        }
        return "redirect:/lendings";
    }

    @GetMapping("/lendings/return/{id}")
    public String returnBook(@PathVariable Long id) {
        bookLendingService.returnBook(id);
        return "redirect:/lendings";
    }

    // REST API endpoints
    @GetMapping("/api/lendings")
    @ResponseBody
    public ResponseEntity<List<BookLendingDTO>> getAllLendings() {
        return ResponseEntity.ok(bookLendingService.getAllLendings());
    }

    @GetMapping("/api/lendings/{id}")
    @ResponseBody
    public ResponseEntity<BookLendingDTO> getLending(@PathVariable Long id) {
        return ResponseEntity.ok(bookLendingService.getLendingById(id));
    }

    @PostMapping("/api/lendings")
    @ResponseBody
    public ResponseEntity<BookLendingDTO> createLending(@RequestBody BookLendingDTO lendingDTO) {
        return ResponseEntity.ok(bookLendingService.issueBook(lendingDTO));
    }

    @PutMapping("/api/lendings/{id}/return")
    @ResponseBody
    public ResponseEntity<BookLendingDTO> returnBookApi(@PathVariable Long id) {
        return ResponseEntity.ok(bookLendingService.returnBook(id));
    }
}