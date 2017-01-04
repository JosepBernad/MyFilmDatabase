package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Adapters.FilmsAdapter;
import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import static com.example.pr_idi.mydatabaseexample.R.layout.main;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

    private String sortBy = "title";

    private String[] var;
    private int anInt = 0;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(main);
        mainListView = (ListView) findViewById(R.id.my_list);
        getSupportActionBar().setTitle("My Films");


        filmData = new FilmData(this);
        filmData.open();

        filmArray = new ArrayList<>(filmData.getAllFilms());
        sortArrayList();


        /** (+) Add button */
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        /**  Search button */
        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        /** Sort button */
        FloatingActionButton sortButton = (FloatingActionButton) findViewById(R.id.sortButton);
        sortButton.setOnClickListener(this);

        var = new String[3];
        var[0] = "title";
        var[1] = "director";
        var[2] = "year";

        /**
         // use the SimpleCursorAdapter to show the
         // elements in a ListView
         ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
         android.R.layout.simple_list_item_1, values);
         setListAdapter(adapter); */


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    // Will be called via the onClick attribute
    // of the buttons in main.xml

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) mainListView.getAdapter();
        Film film;
        switch (view.getId())
        {
            case R.id.addButton:
                filmData.close();
                Intent i = new Intent(MainActivity.this, NewFilmActivity.class);
                startActivity(i);
                break;


            case R.id.sortButton:

                sortBy = var[anInt];

                sortArrayList();

                ++anInt;
                if (anInt == 3)
                    anInt = 0;


                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);

                FilmsAdapter adapter1 = new FilmsAdapter(filmArray);
                recyclerView.setAdapter(adapter1);

                break;

            case R.id.searchButton:
                createRadioListDialog().show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        filmData.open();
        filmArray = new ArrayList<>(filmData.getAllFilms());
        sortArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
        String[] filmArray = {"Add Film", "Delete Film", "Help", "About"};
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

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        final CharSequence[] items = new CharSequence[3];

        items[0] = "By Film";
        items[1] = "By Actor";
        items[2] = "By Director";

        builder.setView(input);

        builder.setTitle("Search")
                //.setView(input)
                /*
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onPossitiveButtonClick();
                                Toast.makeText(
                                        MainActivity.this,
                                        "Search" + items[which],
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                */
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                MainActivity.this,
                                "S'ha seleccionat: " + items[which],
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        return builder.create();
    }


    void sortArrayList()
    {
        Collections.sort(filmArray, new Comparator<Film>(){
            public int compare(Film film1, Film film2)
            {


                switch (sortBy)
                {
                    case "title":
                        return film1.getTitle().compareToIgnoreCase(film2.getTitle());
                    case "director":
                        return film1.getDirector().compareToIgnoreCase(film2.getDirector());
                    case "year":
                        return Integer.valueOf(film1.getYear()).compareTo(film2.getYear());
                    default:
                        return film1.getTitle().compareToIgnoreCase(film2.getTitle());

                    /*
                    ## Ascending order
                    return film1.getFirstName().compareToIgnoreCase(film2.getFirstName()); // To compare string values
                    return Integer.valueOf(film1.getId()).compareTo(film2.getId()); // To compare integer values

                    ## Descending order
                    return film2.getFirstName().compareToIgnoreCase(film1.getFirstName()); // To compare string values
                    return Integer.valueOf(film2.getId()).compareTo(film1.getId()); // To compare integer values
                     */
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}