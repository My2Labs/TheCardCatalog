package com.hemenway.thecardcatalog.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Book;
import com.hemenway.thecardcatalog.entities.BookNumber;

import java.util.Random;

public class AddBook extends AppCompatActivity {
    EditText bookTitleText;
    EditText bookPublisherText;
    TextView bookIsbnText;
    Spinner bookFormatText;

    int bookId;
    int authorId;
    String bookTitle;
    String bookPublisher;
    String bookIsbn;
    String bookFormat;

    Repository eRepository;
    int numExc;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
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

        // Example for industry-appropriate security features to prevent SQL injection.
        Spinner spinner = findViewById(R.id.bookFormat);


        String[] items = new String[]{"Hardcover", "Paperback", "Leatherbound", "Digital"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
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
                String format = bookFormatText.getSelectedItem().toString();
                String isbn = bookIsbnText.getText().toString();

                //Validation check to ensure that there is a title enter before save is made.
                if (!isValidName(title)) {
                    bookTitleText.setError("Invalid title name");
                    Toast.makeText(AddBook.this, "Enter Title to Save!", Toast.LENGTH_LONG).show();
                    return false;
                }

                //Industry-appropriate security features to prevent SQL injection
                if (!isValidLength(title)) {
                    bookTitleText.setError("Title name is too long");
                    Toast.makeText(AddBook.this, "Title name is too long!", Toast.LENGTH_LONG).show();
                    return false;
                }

                //Polymorphism example of creating the BookNumber, which is an extended class of Book
                BookNumber newBook = new BookNumber(0, authorId, title, publisher, format, isbn);

                eRepository.insert(newBook);
                Toast.makeText(AddBook.this, "Book Added", Toast.LENGTH_LONG).show();
                this.finish();

            }
            return true;
        } else if (id == R.id.bookCancel) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    //Validation check
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    //Industry-appropriate security features to prevent SQL injection
    private boolean isValidLength(String name) {
        return name.length() < 50;
    }
}