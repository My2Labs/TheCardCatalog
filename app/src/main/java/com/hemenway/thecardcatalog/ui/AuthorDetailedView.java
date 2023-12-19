package com.hemenway.thecardcatalog.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.entities.Author;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AuthorDetailedView extends AppCompatActivity {
    EditText authorFirstNameText;
    EditText authorLastNameText;


    int authorId;
    String firstName;
    String lastName;

    String bookTitle;

    Author currentAuthor; //TODO: FIX DELETE
    int numExc;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details_with_books);

        repository = new Repository(getApplication());
        authorFirstNameText = findViewById(R.id.authorFirstName);
        authorLastNameText = findViewById(R.id.authorLastName);

        authorId = getIntent().getIntExtra("authorId", -1);
        firstName = getIntent().getStringExtra("authorFirstName");
        lastName = getIntent().getStringExtra("authorLastName");


        if (lastName != null) {

            authorId = getIntent().getIntExtra("authorId", -1);

            authorFirstNameText.setText(firstName);
            authorLastNameText.setText(lastName);

        }


        Button button = findViewById(R.id.enterBookDetails);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(AuthorDetailedView.this, BookDetailedView.class);
            intent.putExtra("authorId", authorId);
            startActivity(intent);
        });




        RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
        repository = new Repository(getApplication());
        final BookAdapter bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Book> filteredBooks = new ArrayList<>();
        for (Book e : repository.getAllBooks()) {
            if (e.getAuthorId() == authorId) filteredBooks.add(e);
        }
        bookAdapter.setBooks(filteredBooks);

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_author_details, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.authorSave) {
            firstName = authorFirstNameText.getText().toString();
            lastName = authorLastNameText.getText().toString();



                if (authorId == -1) {

                    String firstName = authorFirstNameText.getText().toString();
                    String lastName = authorLastNameText.getText().toString();

                    Author newAuthor = new Author(0, firstName, lastName);

                    repository.insert(newAuthor);

                    Toast.makeText(AuthorDetailedView.this, "Author added successfully", Toast.LENGTH_LONG).show();

                    this.finish();
                } else {

                    String firstName = authorFirstNameText.getText().toString();
                    String lastName = authorLastNameText.getText().toString();


                    Author newAuthor = new Author(authorId, firstName, lastName);

                    repository.update(newAuthor);

                    Toast.makeText(AuthorDetailedView.this, "Author added successfully", Toast.LENGTH_LONG).show();

                    this.finish();

                }

                return true;



        }

        if (id == R.id.delete) {
            for (Author auth : repository.getAllAuthors()) {
                if (auth.getAuthorId() == Integer.parseInt(String.valueOf(authorId)))
                    currentAuthor = auth;
            }

            numExc = 0;
            for (Book bk : repository.getAllBooks()) {
                if (bk.getAuthorId() == Integer.parseInt(String.valueOf(authorId)))
                    ++numExc;
            }

            if (numExc == 0) {
                repository.delete(currentAuthor);
                Toast.makeText(AuthorDetailedView.this, currentAuthor.getAuthorFirstName() + " " + currentAuthor.getAuthorLastName() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(AuthorDetailedView.this, "Can't delete an author with books.", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, authorFirstNameText.getText().toString() + " " + authorLastNameText.getText().toString() + " " );
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Wonderful choice!");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

//        if (id == R.id.startAlert)
//        {
//            String dateFromScreen = vacationStartText.getText().toString();
//            String myFormat = "MM/dd/yy";
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//            Date myDate = null;
//            try {
//                myDate = sdf.parse(dateFromScreen);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try{
//                Long trigger = myDate.getTime();
//                Intent intent = new Intent(VacationDetailedView.this, Receiver.class);
//                intent.putExtra("key", name + " vacation is starting!");
//                PendingIntent sender = PendingIntent.getBroadcast(VacationDetailedView.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);}
//            catch (Exception e){
//
//            }
//            return true;
//        }

//        if (id == R.id.endAlert) {
//            String dateFromScreen = vacationStartText.getText().toString();
//            String myFormat = "MM/dd/yy";
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//            Date myDate = null;
//            try {
//                myDate = sdf.parse(dateFromScreen);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try{
//                Long trigger = myDate.getTime();
//                Intent intent = new Intent(VacationDetailedView.this, Receiver.class);
//                intent.putExtra("key", name + " vacation is ending.");
//                PendingIntent sender = PendingIntent.getBroadcast(VacationDetailedView.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);}
//            catch (Exception e){
//
//            }
//            return true;
//
//        }

        if (id == R.id.refresh) {
            RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
            repository = new Repository(getApplication());
            final BookAdapter bookAdapter = new BookAdapter(this);
            recyclerView.setAdapter(bookAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Book> filteredBooks = new ArrayList<>();
            for (Book e : repository.getAllBooks()) {
                if (e.getAuthorId() == authorId) filteredBooks.add(e);
            }
            bookAdapter.setBooks(filteredBooks);

            return true;
        }

//        if (id == R.id.notify) {
//            String dateFromScreen=vacationStartText.getText().toString();
//            String myFormat = "MM/dd/yy";
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//            Date myDate = null;
//            try {
//                myDate = sdf.parse(dateFromScreen);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Long trigger=myDate.getTime();
//            Intent intent = new Intent(VacationDetailedView.this, Receiver.class);
//            intent.putExtra("key", name + "VACATION ALERT: D308 - Performance Assessment");
//            PendingIntent sender = PendingIntent.getBroadcast(VacationDetailedView.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
//            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
//            return true;
//
//        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
        final BookAdapter bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Book> filteredBooks = new ArrayList<>();
        for (Book e : repository.getAllBooks()) {
            if(e.getAuthorId() == authorId) filteredBooks.add(e);
        }
        bookAdapter.setBooks(filteredBooks);
    }


}
