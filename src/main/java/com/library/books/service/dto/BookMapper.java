package com.library.books.service.dto;

import com.library.books.repository.entity.Book;

public class BookMapper {
    public static BookDTO mapToBookDTO(Book book, BookDTO bookDTO){
        bookDTO.setBookTitle(book.getBookTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthors(book.getAuthors());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setYearPublished(book.getYearPublished());
        bookDTO.setPrice(book.getPrice());

        return bookDTO; // map to DTO so that id is excluded from returning to client side
    }
}
