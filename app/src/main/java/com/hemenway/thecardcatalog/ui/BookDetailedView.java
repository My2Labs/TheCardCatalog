package com.hemenway.thecardcatalog.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Author;
import com.hemenway.thecardcatalog.entities.Book;
import com.hemenway.thecardcatalog.entities.BookNumber;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    String authorFirstName;
    String authorLastName;

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
            int randomInt = random.nextInt(10000000);
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
                authorFirstName = v.getAuthorFirstName();
                authorLastName = v.getAuthorLastName();
                break;
            }
        }
        if (bookTitle != null) {
            bookTitleText.setText(bookTitle);
            bookPublisherText.setText(bookPublisher);
            bookFormatText.setText(bookFormat);
            bookIsbnText.setText(bookIsbn);
        }

        Button createPdf = findViewById(R.id.create_pdf);
        createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdf();
            }
        });
    }

    final static int REQUEST_CODE = 5782;

    private void askPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    //Generate PDF report
    private void createPdf() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(70);



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = dateFormat.format(new Date());

        canvas.drawText("Book Details:", 50, 150, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Author: ", 50, 250, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(authorFirstName + " " + authorLastName, 350, 250, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("Book Title: ", 50, 350, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(bookTitle, 350, 350, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("Publisher: ", 50, 450, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(bookPublisher, 350, 450, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("Format: ", 50, 550, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(bookFormat, 350, 550, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("ISBN: ", 50, 650, paint);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(bookIsbn, 350, 650, paint);

        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Date/Time Printed: " + currentDateTime, 100, 850, paint);


        document.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "book_list.pdf";
        File file = new File(downloadsDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Written Success", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d("cardLog", "Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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