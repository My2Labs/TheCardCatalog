package com.hemenway.thecardcatalog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Author;
import com.hemenway.thecardcatalog.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class AuthorCreate extends AppCompatActivity {

    EditText authorFirstNameText;
    EditText authorLastNameText;

    int authorId;
    String firstName;
    String lastName;
    int numExc;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_create);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_author_create, menu);
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

                Toast.makeText(AuthorCreate.this, "Author added successfully", Toast.LENGTH_LONG).show();

                this.finish();
            } else {

                String firstName = authorFirstNameText.getText().toString();
                String lastName = authorLastNameText.getText().toString();


                Author newAuthor = new Author(authorId, firstName, lastName);

                repository.update(newAuthor);

                Toast.makeText(AuthorCreate.this, "Author added successfully", Toast.LENGTH_LONG).show();

                this.finish();
            }
            return true;
        } else if (id == R.id.authorCancel) {
            finish();
            
        }
        return super.onOptionsItemSelected(item);
    }
}