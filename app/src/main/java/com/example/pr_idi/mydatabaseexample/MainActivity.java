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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pr_idi.mydatabaseexample.Adapters.filmsAdapter;
import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;



public class MainActivity extends ListActivity implements View.OnClickListener
{
    private FilmData filmData;
    private ArrayList<Film> filmArray;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /** (+) Add button */
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);


        filmData = new FilmData(this);
        filmData.open();

        filmArray = new ArrayList<>(filmData.getAllFilms());

        /**
        Film film = filmData.createFilm("Pítulo","Director","Country",9999,"Protagonist",10);

        filmArray.add(film); */


        /**
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter); */


        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new filmsAdapter(filmArray);
        recyclerView.setAdapter(adapter);


    }



    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.addButton:

                filmData.close();
                Intent i = new Intent(MainActivity.this, NewFilmActivity.class);
                startActivity(i);

                break;



            case R.id.add:
                String[] newFilm = new String[] { "Blade Runner", "Ridley Scott", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Toy Story", "John Lasseter" };
                int nextInt = new Random().nextInt(4);
                // save the new film to the database
                film = filmData.createFilm(newFilm[nextInt*2], newFilm[nextInt*2 + 1],"Country",9999,"Protagonist",10);
                adapter.add(film);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    film = (Film) getListAdapter().getItem(0);
                    filmData.deleteFilm(film);
                    adapter.remove(film);

                }
                break;
        }
        /**adapter.notifyDataSetChanged();*/
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

        adapter = new filmsAdapter(filmArray);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

}