package com.hemenway.thecardcatalog.entities;


//PART B - Polymorphism Example
public class BookNumber extends Book {

    String isbn;
    public String addIsbn(String isbn) {
        this.isbn = isbn;
        return isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
