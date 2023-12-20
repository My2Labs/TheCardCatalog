package com.hemenway.thecardcatalog.database;

import android.app.Application;

import com.hemenway.thecardcatalog.dao.AuthorDao;
import com.hemenway.thecardcatalog.dao.BookDao;
import com.hemenway.thecardcatalog.entities.Author;
import com.hemenway.thecardcatalog.entities.Book;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private AuthorDao mAuthorDao;
    private BookDao mBookDao;
    private List<Book> mAllBooks;
    private List<Author> mAllAuthors;
    private List<Author> mAllAuthorsById;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        AuthorDatabaseBuilder db = AuthorDatabaseBuilder.getDatabase(application);
        mAuthorDao = db.authorDao();
        mBookDao = db.bookDao();
    }

    public List<Author> getAllAuthors() {
        databaseExecutor.execute(() -> {
            mAllAuthors = mAuthorDao.getAllAuthors();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAuthors;
    }

    public List<Author> getAuthorsById() {
        databaseExecutor.execute(() -> {
            mAllAuthorsById = mAuthorDao.getAuthorsById();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAuthorsById;
    }

    public void insert(Author author) {
        databaseExecutor.execute(() -> {
            mAuthorDao.insert(author);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Author author) {
        databaseExecutor.execute(() -> {
            mAuthorDao.update(author);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Author author) {
        databaseExecutor.execute(() -> {
            mAuthorDao.delete(author);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        databaseExecutor.execute(() -> {
            mAllBooks = mBookDao.getAllBooks();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllBooks;
    }

    public void insert(Book book) {
        databaseExecutor.execute(() -> {
            mBookDao.insert(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Book book) {
        databaseExecutor.execute(() -> {
            mBookDao.update(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Book book) {
        databaseExecutor.execute(() -> {
            mBookDao.delete(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

