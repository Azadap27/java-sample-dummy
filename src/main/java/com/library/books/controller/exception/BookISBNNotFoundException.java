package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookISBNNotFoundException extends RuntimeException {
    public BookISBNNotFoundException(String message) {
        super(message);
    }
}
