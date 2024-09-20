package com.library.books.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "book")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column required if the member != the db column name i.e. book_title != bookTitle
    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "year_published")
    private Integer yearPublished;

    private String authors; // table column is "authors" so no need for @Column here
    private String publisher;
    private String isbn;
    private Integer price;
}
