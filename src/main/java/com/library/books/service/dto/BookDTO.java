package com.library.books.service.dto;

import lombok.Data;

// @Data : Generates getters/setters, a constructor, toString, hashCode and equals.
// Use of DTO - Data Transfer Object to not show/neglect id/sensitive data in client view
@Data
public class BookDTO {
    private String bookTitle, authors, publisher, isbn;
    private Integer yearPublished, price;
}
