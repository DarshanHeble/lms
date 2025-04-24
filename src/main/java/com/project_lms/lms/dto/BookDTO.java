package com.project_lms.lms.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer publicationYear;
    private Integer quantity;
    private Integer availableQuantity;
}