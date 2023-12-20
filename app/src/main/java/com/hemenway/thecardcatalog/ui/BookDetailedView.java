package com.hemenway.thecardcatalog.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Author;
import com.hemenway.thecardcatalog.entities.Book;
import com.hemenway.thecardcatalog.entities.BookNumber;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class BookDetailedView extends AppCompatActivity {
    EditText bookTitleText;
    EditText bookPublisherText;
    TextView bookIsbnText;
    EditText bookFormatText;

    int bookId;
    int authorId;
    String bookTitle;
    String bookPublisher;
    String bookIsbn;
    String bookFormat;

    Repository eRepository;
    int numExc;
    Book currentBook;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        eRepository = new Repository(getApplication());
        bookTitleText = findViewById(R.id.bookTitle);
        bookPublisherText = findViewById(R.id.bookPublisher);
        bookFormatText = findViewById(R.id.bookFormat);
        bookIsbnText = findViewById(R.id.bookIsbn);


        authorId = getIntent().getIntExtra("authorId", -1);
        bookId = getIntent().getIntExtra("bookId", -1);
        bookTitle = getIntent().getStringExtra("bookTitle");
        bookPublisher = getIntent().getStringExtra("bookPublisher");
        bookFormat = getIntent().getStringExtra("bookFormat");
        bookIsbn = getIntent().getStringExtra("bookIsbn");


        //PART B - Polymorphism Example
        if(bookIsbn == null) {
            Random random = new Random();
            int randomInt = random.nextInt(10000);
            bookIsbn = String.valueOf(randomInt);
            BookNumber newIsbn = new BookNumber();
            newIsbn.addIsbn(bookIsbn);
        }
        else {
            BookNumber newIsbn = new BookNumber();
            newIsbn.addIsbn(bookIsbn);
        }




        List<Author> myAuthors = eRepository.getAllAuthors();
        for (Author v : myAuthors) {
            if (v.getAuthorId() == authorId) {
                authorId = v.getAuthorId();
                break;
            }
        }
        if (bookTitle != null) {
            bookTitleText.setText(bookTitle);
            bookPublisherText.setText(bookPublisher);
            bookFormatText.setText(bookFormat);
            bookIsbnText.setText(bookIsbn);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        if (id == R.id.bookSave) {


            if (bookId == -1) {
                String title = bookTitleText.getText().toString();
                String publisher = bookPublisherText.getText().toString();
                String format = bookFormatText.getText().toString();

                Book newBook = new Book(0, authorId, title, publisher, format, bookIsbn);
//                Book newBook = new Book(0, authorId, title);
                eRepository.insert(newBook);
                Toast.makeText(BookDetailedView.this, "Book Added", Toast.LENGTH_LONG).show();
                this.finish();

            } else if (bookId >= 0) {
                String title = bookTitleText.getText().toString();
                String publisher = bookPublisherText.getText().toString();
                String format = bookFormatText.getText().toString();

                Book newBook = new Book(bookId, authorId, title, publisher, format, bookIsbn);
//                Book newBook = new Book(0, authorId, title);
                eRepository.update(newBook);
                Toast.makeText(BookDetailedView.this, "Book Updated", Toast.LENGTH_LONG).show();
                this.finish();
            }

            return true;
        }
        if (id == R.id.bookDelete) {

            for (Book bk : eRepository.getAllBooks()) {
                if (bk.getBookId() == bookId) currentBook = bk;
            }
            eRepository.delete(currentBook);
            Toast.makeText(BookDetailedView.this, currentBook.getBookTitle() + " was deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}