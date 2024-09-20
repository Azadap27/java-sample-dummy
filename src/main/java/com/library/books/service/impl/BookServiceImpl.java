package com.library.books.service.impl;

import com.library.books.controller.exception.BookISBNAlreadyExistsException;
import com.library.books.controller.exception.BookISBNMismatchException;
import com.library.books.controller.exception.BookISBNNotFoundException;
import com.library.books.repository.BookRepository;
import com.library.books.repository.entity.Book;
import com.library.books.service.BookService;
import com.library.books.service.dto.BookDTO;
import com.library.books.service.dto.BookMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookDTO> retrieveBooks() {
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookRepository.findAll().forEach(book -> bookDTOList.add(BookMapper.mapToBookDTO(book, new BookDTO())));
        return bookDTOList;
    }

    @Override
    public BookDTO retrieveBookByISBN(String isbn) {
        if (!bookRepository.findByIsbn(isbn).isPresent()){
            throw new BookISBNNotFoundException("Book with given ISBN not found in database, for : "+isbn);
        }
        return BookMapper.mapToBookDTO(bookRepository.findByIsbn(isbn).get(), new BookDTO());
    }

    @Override
    public List<BookDTO> retrieveBooksByAuthor(String author) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookRepository.findByAuthors(author).forEach(book -> bookDTOList.add(BookMapper.mapToBookDTO(book, new BookDTO())));
        return bookDTOList;
    }

    @Override
    public BookDTO createBook(Book book) {
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()){
            throw new BookISBNAlreadyExistsException("Book with given ISBN already exists in database : "+book.getIsbn());
        }
        return BookMapper.mapToBookDTO(bookRepository.save(book), new BookDTO());
    }

    @Override
    public void updateBookByISBN(String isbn, Book book) {
        if (!book.getIsbn().equals(isbn)){
            throw new BookISBNMismatchException("ISBN mismatch : from URI : "+isbn+" : from entity : "+book.getIsbn());
        } else if (!bookRepository.findByIsbn(isbn).isPresent()){
            throw new BookISBNNotFoundException("Book with given ISBN not found in database, for : "+isbn);
        } else {
            bookRepository.updateBookByISBN(isbn, book.getBookTitle(), book.getAuthors(), book.getPublisher(),
                    book.getYearPublished(), book.getPrice());
        }
    }

    @Override
    public void deleteBooks() {
        bookRepository.deleteAll();
    }

    @Override
    public void deleteBookByISBN(String isbn) {
        if (!bookRepository.findByIsbn(isbn).isPresent()){
            throw new BookISBNNotFoundException("Book with given ISBN not found in database, for : "+isbn);
        }
        bookRepository.deleteBookByIsbn(isbn);
    }
}
