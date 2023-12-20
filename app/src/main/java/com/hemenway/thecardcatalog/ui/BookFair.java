package com.hemenway.thecardcatalog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.hemenway.thecardcatalog.R;
import com.hemenway.thecardcatalog.entities.Book;

public class BookFair extends AppCompatActivity {

    //Search feature
    ListView listView;
    String[] name = {"Nashville Book Fair \n\t\tNashville, TN \n\t\t01/27/2024",
            "Atlanta Book Fair \n\t\tAtlanta, GA \n\t\t02/17/2024",
            "Colorado Springs Book Fair \n\t\tColorado Springs, CO \n\t\t03/23/2024",
            "Chicago Book Fair \n\t\tChicago, IL \n\t\t04/27/2024",
            "D.C. Book Fair \n\t\tWashington, D.C. \n\t\t05/18/2024",
            "Seattle Book Fair \n\t\tSeattle, WA \n\t\t06/15/2024",
            "Salt Lake City Book Fair \n\t\tSalt Lake City, UT \n\t\t07/20/2024",
            "Orlando Book Fair \n\t\tOrlando, FL \n\t\t08/17/2024",
            "Kansas City Book Fair \n\t\tKansas City, KS \n\t\t09/14/2024",
            "Louisville Book Fair \n\t\tLouisville, KY \n\t\t10/19/2024"};

    ArrayAdapter<String> arrayAdapter;
    //-----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_fair);

        //Search feature
        listView = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name);
        listView.setAdapter(arrayAdapter);
        //-------------

        Button button = findViewById(R.id.return_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookFair.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Search feature
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search book fairs");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    //-------------------
}