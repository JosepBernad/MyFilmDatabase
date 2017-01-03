package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Adapters.FilmsAdapter;
import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;

import static com.example.pr_idi.mydatabaseexample.R.layout.main;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public ListView mainListView;

    private FilmData filmData;
    private ArrayList<Film> filmArray;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(main);
        mainListView = (ListView) findViewById(R.id.my_list);
        getSupportActionBar().setTitle("My Films");

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();

        setupDrawer();

        /////End Navigaion Drawer//////

    }



    // Will be called via the onClick attribute
    // of the buttons in main.xml

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) mainListView.getAdapter();
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
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //cas Add Film
                        filmData.close();
                        Intent i = new Intent(MainActivity.this, NewFilmActivity.class);
                        startActivity(i);
                        break;
                    case 1: //cas Delete Film
                        Toast.makeText(MainActivity.this, "No se borrar pelis OK?", Toast.LENGTH_SHORT).show();
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
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Es crida quan el drawer esta completament obert */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Options");
                invalidateOptionsMenu();
            }

            /** Es crida quan el drawer esta completament tancat */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("My Films");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Controlar els clicks de la action bar aqui.
        int id = item.getItemId();

        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}