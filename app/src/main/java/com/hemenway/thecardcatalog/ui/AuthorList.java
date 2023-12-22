package com.hemenway.thecardcatalog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.database.Repository;
import com.hemenway.thecardcatalog.entities.Author;

import java.util.List;

public class AuthorList extends AppCompatActivity {

    private Repository repository;
    int authorId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_list);



        RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
        final AuthorAdapter authorAdapter = new AuthorAdapter(this);

        recyclerView.setAdapter(authorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());
        List<Author> allAuthors = repository.getAllAuthors();

        authorAdapter.setAuthors(allAuthors);


        Button button = findViewById(R.id.enterBookDetails);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthorList.this, AuthorCreate.class);
                startActivity(intent);
            }
        });


        System.out.println(getIntent().getStringExtra("test"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_author_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.authorRefresh) {
            RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
            final AuthorAdapter link = new AuthorAdapter(this);
            recyclerView.setAdapter(link);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            link.setAuthors(repository.getAllAuthors());

            return true;
        }
        else if (id == R.id.return_home) {
            Intent intent = new Intent(AuthorList.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
        final AuthorAdapter authorAdapter = new AuthorAdapter(this);
        recyclerView.setAdapter(authorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        authorAdapter.setAuthors(repository.getAllAuthors());
    }
}