package com.project_lms.lms.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookLendingDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private String status;
    
    // Book details
    private String bookTitle;
    private String bookAuthor;
    
    // User details
    private String userName;
    private String userEmail;
}