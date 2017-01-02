package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Adapters.FilmsAdapter;
import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;



public class MainActivity extends ListActivity implements View.OnClickListener
{
    private FilmData filmData;
    private ArrayList<Film> filmArray;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /** (+) Add button */
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);


        filmData = new FilmData(this);
        filmData.open();

        filmArray = new ArrayList<>(filmData.getAllFilms());


        /**
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter); */


        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FilmsAdapter(filmArray);
        recyclerView.setAdapter(adapter);


        ////// Navigation Drawer/////////
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //cas Add Film
                        Toast.makeText(MainActivity.this, "No se que he de posar aqui jaja", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: //cas Delete Film
                        Toast.makeText(MainActivity.this, "Aqui tampoc se que s'ha de posar", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: //cas Help
                        Toast.makeText(MainActivity.this, "Help encara s'ha de implementar XD", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: //cas About
                        Toast.makeText(MainActivity.this, "About encara s'ha de implementar XD", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        /////End Navigaion Drawer//////

    }



    // Will be called via the onClick attribute
    // of the buttons in main.xml

    public void onClick(View view) {

        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.addButton:

                filmData.close();
                Intent i = new Intent(MainActivity.this, NewFilmActivity.class);
                startActivity(i);

                break;
        }

    }

    @Override
    protected void onResume()
    {
        filmData.open();
        super.onResume();
        filmArray = new ArrayList<>(filmData.getAllFilms());
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FilmsAdapter(filmArray);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

    private void addDrawerItems() {
        String[] filmArray = { "Add Film", "Delete Film", "Help", "About" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filmArray);
        mDrawerList.setAdapter(mAdapter);
    }

}