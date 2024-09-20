package com.library.books.controller;

import com.library.books.repository.entity.Book;
import com.library.books.service.BookService;
import com.library.books.service.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book, UriComponentsBuilder uriComponentsBuilder){
        BookDTO bookDTO = bookService.createBook(book);
        URI uri = uriComponentsBuilder
                .path("/books/"+bookDTO.getIsbn())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(uri)
                .body(bookDTO);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<BookDTO> books = bookService.retrieveBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> getBookByISBN(@PathVariable String isbn){
        BookDTO book = bookService.retrieveBookByISBN(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping(path = "/book", params = "authorName")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String authorName){
        List<BookDTO> books = bookService.retrieveBooksByAuthor(authorName);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<String> updateBookByISBN(@PathVariable String isbn, @RequestBody Book book){
        bookService.updateBookByISBN(isbn, book);
        return ResponseEntity.status(HttpStatus.OK).body("Book updated successfully.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBooks(){
        bookService.deleteBooks();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBookByISBN(@PathVariable String isbn){
        bookService.deleteBookByISBN(isbn);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping
    public ResponseEntity<String> putNotSupported(){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<String> postNotSupported(){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }

    // @OptionsMapping  - not available
    // @HeadMapping     - not available
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsCollectionOfBooks(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE)
                .build();
    }

    @RequestMapping(path = "/{isbn}", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsIndividualBook(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .build();
    }

    @RequestMapping(path = "/book", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsIndividualBookRequestParam(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET)
                .build();
    }
}
