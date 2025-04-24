package com.project_lms.lms.controller;

import com.project_lms.lms.dto.BookDTO;
import com.project_lms.lms.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        List<BookDTO> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/books/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        return "books/form";
    }

    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        BookDTO book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/form";
    }

    @PostMapping("/books/save")
    public String saveBook(@ModelAttribute BookDTO bookDTO) {
        if (bookDTO.getId() == null) {
            bookService.createBook(bookDTO);
        } else {
            bookService.updateBook(bookDTO.getId(), bookDTO);
        }
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    // REST API endpoints
    @GetMapping("/api/books")
    @ResponseBody
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/api/books/{id}")
    @ResponseBody
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/api/books")
    @ResponseBody
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @PutMapping("/api/books/{id}")
    @ResponseBody
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/api/books/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBookApi(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}