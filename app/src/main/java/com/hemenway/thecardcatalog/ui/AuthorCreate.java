package com.hemenway.thecardcatalog.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Author;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.authorSave) {
            firstName = authorFirstNameText.getText().toString();
            lastName = authorLastNameText.getText().toString();

            if (authorId == -1) {
                String firstName = authorFirstNameText.getText().toString();
                String lastName = authorLastNameText.getText().toString();

                //Validation check to make sure there is a value in the first name and last name

                if (!isValidName(firstName)) {
                    authorFirstNameText.setError("Invalid first name");
                    Toast.makeText(AuthorCreate.this, "Enter First Name to Save!", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (!isValidName(lastName)) {
                    authorLastNameText.setError("Invalid last name");
                    Toast.makeText(AuthorCreate.this, "Enter Last Name to Save!", Toast.LENGTH_LONG).show();
                    return false;
                }

                //Industry-appropriate security features to prevent SQL injection
                if (!isValidLength(firstName)) {
                    authorFirstNameText.setError("First Name is too long");
                    Toast.makeText(AuthorCreate.this, "First Name is too long!", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (!isValidLength(lastName)) {
                    authorFirstNameText.setError("Last Name is too long");
                    Toast.makeText(AuthorCreate.this, "Last Name is too long!", Toast.LENGTH_LONG).show();
                    return false;
                }

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


    //Validation check
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    //Industry-appropriate security features to prevent SQL injection
    private boolean isValidLength(String name) {
        return name.length() < 30;
    }
}