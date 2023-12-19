package com.hemenway.thecardcatalog.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hemenway.thecardcatalog.dao.AuthorDao;
import com.hemenway.thecardcatalog.dao.BookDao;
import com.hemenway.thecardcatalog.entities.Author;
import com.hemenway.thecardcatalog.entities.Book;

@Database(entities = {Author.class, Book.class}, version = 8, exportSchema = false)
public abstract class AuthorDatabaseBuilder extends RoomDatabase {
    public abstract AuthorDao authorDao();
    public abstract BookDao bookDao();
    private static volatile AuthorDatabaseBuilder INSTANCE;

    static AuthorDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE==null) {
            synchronized (AuthorDatabaseBuilder.class) {
                if(INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AuthorDatabaseBuilder.class, "MyAuthorDatabase.db")
                            .fallbackToDestructiveMigration()
                            //for synchronous use the following:
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
