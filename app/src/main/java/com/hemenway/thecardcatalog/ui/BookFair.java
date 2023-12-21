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
    String[] name = {"Albuquerque Comic Con \n\t\tAlbuquerque, NM \n\t\tJanuary 19-21, 2024",
            "Sunshine State Book Festival \n\t\tGainesville, FL \n\t\tJanuary 26-27, 2024",
            "AWP Conference & Bookfair \n\t\tKansas City, MO \n\t\tFebruary 7-10, 2024",
            "Savannah Book Festival \n\t\tSavannah, GA \n\t\tFebruary 15-18, 2024",
            "Children's Literature Festival \n\t\tRedlands, CA \n\t\tMarch 1-2, 2024",
            "Dahlonega Literary Festival \n\t\tDahlonega, GA \n\t\tMarch 2, 2024",
            "Southwest Florida Reading Festival \n\t\tFort Myers, FL \n\t\tMarch 2, 2024",
            "Tucson Festival of Books \n\t\tTucson, AZL \n\t\tMarch 9-10",
            "New Orleans Book Festival at Tulane University \n\t\tNew Orleans, LA \n\t\tMarch 14-16, 2024",
            "Palm Beach Book Festival \n\t\tPalm Beach, FL \n\t\tMarch 15-16, 2024"};

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