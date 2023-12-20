package com.hemenway.thecardcatalog.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hemenway.thecardcatalog.entities.Author;

import java.util.List;

@Dao
public interface AuthorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Author author);

    @Update
    void update(Author author);

    @Delete
    void delete(Author author);

    @Query("Select * from authors order by authorFirstName asc")
    List<Author> getAllAuthors();

    @Query("Select * from authors where authorId = authorId order by authorFirstName asc")
    List<Author> getAuthorsById();
}
