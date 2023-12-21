package com.hemenway.thecardcatalog.entities;


//PART B - Polymorphism Example
public class BookNumber extends Book {

    private String isbn;
    public String getIsbn() {
        return super.getIsbn();
    }

    public BookNumber (int bookId, int authorId, String bookTitle, String bookPublisher, String bookFormat, String bookIsbn) {
        super(bookId, authorId, bookTitle, bookPublisher, bookFormat, bookIsbn);
    }
    public void addIsbn(String isbn) {
        this.isbn = isbn;
    }


}
