package com.hemenway.thecardcatalog.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hemenway.thecardcatalog.entities.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("Select * from books order by bookTitle asc")
    List<Book> getAllBooks();

    @Query("Select * from books where bookId = bookId order by bookTitle asc")
    List<Book> getBooksById();
}