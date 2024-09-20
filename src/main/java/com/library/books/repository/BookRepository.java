package com.library.books.repository;

import com.library.books.repository.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthors(String author);

    @Transactional
    @Modifying
    @Query("UPDATE book b SET b.bookTitle=?2, b.authors=?3, b.publisher=?4, b.yearPublished=?5, b.price=?6 WHERE b.isbn=?1")
    void updateBookByISBN(String isbn, String bookTitle, String authors, String publisher, Integer yearPublished, Integer price);

    @Transactional
    void deleteBookByIsbn(String isbn);
}
