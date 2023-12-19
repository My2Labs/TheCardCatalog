package com.hemenway.thecardcatalog.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
// PART B: (Inheritance & Encapsulation Examples)
public class Book extends Author {

    @PrimaryKey(autoGenerate = true)
    private int bookId;

    private int authorId;

    private String bookTitle;

    private String bookPublisher;

    private String bookFormat;
    private String bookIsbn;

    @Ignore
    public Book() {}


    public Book(int bookId, int authorId, String bookTitle, String bookPublisher, String bookFormat, String bookIsbn) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.bookTitle = bookTitle;
        this.bookPublisher = bookPublisher;
        this.bookFormat = bookFormat;
        this.bookIsbn = bookIsbn;
    }

    @Ignore
    public Book(int bookId, int authorId, String bookTitle) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.bookTitle = bookTitle;

    }

    public int getBookId() { return bookId; }

    public Book(String type) { this.bookTitle = type; }

    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getAuthorId() { return authorId; }

    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getBookTitle() { return bookTitle; }

    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBookPublisher() { return bookPublisher; }

    public void setBookPublisher(String bookPublisher) { this.bookPublisher = bookPublisher; }

    public String getBookFormat() { return bookFormat; }

    public void setBookFormat(String bookFormat) { this.bookFormat = bookFormat; }

    public String getBookIsbn() { return bookIsbn; }

    public void setBookIsbn(String bookFormat) { this.bookIsbn = bookIsbn; }

    //PART B - Polymorphism Example
    public String addIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
        return this.bookIsbn;
    }
}

