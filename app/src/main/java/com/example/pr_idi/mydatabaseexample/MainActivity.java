package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Adapters.SpinnerAdapter;
import com.example.pr_idi.mydatabaseexample.Adapters.FilmsAdapter;
import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;
import com.example.pr_idi.mydatabaseexample.Class.RecyclerItemClickListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Collections;
import java.util.Comparator;

import static com.example.pr_idi.mydatabaseexample.R.layout.main;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ListView mainListView;

    private FilmData filmData;
    private ArrayList<Film> filmArray;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FilmsAdapter adapter;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;


    private String sortBy = "title";

    private String[] var;
    private int anInt = 0;

    private EditText searchText;
    private Spinner dropdown;
    private int searchBy;

    private AlertDialog levelDialog;
    private int auxDialog = 0;

    private int expandedPosition = -1;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(main);
        expandedPosition = -1;
        mainListView = (ListView) findViewById(R.id.my_list);
        searchText = (EditText) findViewById(R.id.searchText);
        dropdown = (Spinner)findViewById(R.id.my_spinner);
        searchBy = 0;
        getSupportActionBar().setTitle("My Films");


        filmData = new FilmData(this);
        filmData.open();




        /** (+) Add button */
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);


        /** Sort button
        FloatingActionButton sortButton = (FloatingActionButton) findViewById(R.id.sortButton);
        sortButton.setOnClickListener(this);
        */

        /** Search by spinner */
        setupSpinner();

        /** Search TextEdit */

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                refreshAdapter();
                recyclerView.setAdapter(adapter);
            }
        });


        var = new String[4];
        var[0] = "title";
        var[1] = "director";
        var[2] = "year";
        var[3] = "rate";

        /**
         // use the SimpleCursorAdapter to show the
         // elements in a ListView
         ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
         android.R.layout.simple_list_item_1, values);
         setListAdapter(adapter); */


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        /** Recycler View Clickable */

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //expandedPosition = adapter.getExpandedPosition();
                if(expandedPosition != position) {
                    filmData.close();
                    Intent i = new Intent(MainActivity.this, FilmDetailsActivity.class);

                    i.putExtra("FILM_ID", filmArray.get(position).getId());
                    i.putExtra("FILM_TITLE", filmArray.get(position).getTitle());
                    i.putExtra("FILM_DIRECTOR", filmArray.get(position).getDirector());
                    i.putExtra("FILM_COUNTRY", filmArray.get(position).getCountry());
                    i.putExtra("FILM_YEAR", filmArray.get(position).getYear());
                    i.putExtra("FILM_ACTOR", filmArray.get(position).getProtagonist());
                    i.putExtra("FILM_RATE", filmArray.get(position).getCritics_rate());

                    startActivity(i);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if(expandedPosition!=position){
                    adapter.setExpandedPosition(position);
                    adapter.notifyDataSetChanged();
                    expandedPosition = position;
                }
                else {
                    adapter.setExpandedPosition(-1);
                    adapter.notifyDataSetChanged();
                    expandedPosition = -1;
                }

            }
        }));

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        refreshAdapter();
        recyclerView.setAdapter(adapter);


        ////// Navigation Drawer/////////

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mActivityTitle = getTitle().toString();

        mDrawerList = (ListView) findViewById(R.id.navList);
        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.headerlayout, mDrawerList, false);
        mDrawerList.addHeaderView(myHeader, null, false);
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

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        filmData.open();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        refreshAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }


    private void addDrawerItems() {
        String[] filmArray = {"My Films", "Add Film", "Help", "About"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filmArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2: //cas Add Film
                        filmData.close();
                        Intent i = new Intent(MainActivity.this, NewFilmActivity.class);
                        startActivity(i);
                        break;
                    case 3: //cas Help
                        Toast.makeText(MainActivity.this, "Help encara s'ha de implementar XD", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: //cas About
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

        //Activate the sort Button in the ActionBar
        if (id == R.id.sortAction)
        {
            final CharSequence[] items = { "Title", "Director", "Year", "Rating" };

            // Creating and Building the Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sort By");
            auxDialog = anInt;
            builder.setSingleChoiceItems(items,anInt,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            auxDialog = item;
                            //sortBy = var[item];
                            //levelDialog.dismiss();
                        }
                    });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    anInt = auxDialog;
                    sortBy = var[auxDialog];
                    refreshAdapter();
                    recyclerView.setAdapter(adapter);
                    levelDialog.dismiss();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    auxDialog = anInt;
                    levelDialog.dismiss();
                }
            });
            levelDialog = builder.create();
            levelDialog.show();
            return true;
        }
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
                    case "rate":
                        return Integer.valueOf(film2.getCritics_rate()).compareTo(film1.getCritics_rate());
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

    private void setupSpinner(){

        String[] items = new String[]{"Search by Title", "Search by Director", "Search by Year", "Search by Actor"};
        ArrayAdapter<String> adapterSpin = new SpinnerAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapterSpin);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // hide selection text
                searchBy = position;
                switch (position){
                    case 0:
                        searchText.setHint("Search by Title...");
                        break;
                    case 1:
                        searchText.setHint("Search by Director...");
                        break;
                    case 2:
                        searchText.setHint("Search by Year...");
                        break;
                    case 3:
                        searchText.setHint("Search by Actor...");
                        break;
                }
                refreshAdapter();
                recyclerView.setAdapter(adapter);
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void refreshAdapter(){
        filmArray = new ArrayList<>(filmData.getFilmsThat(searchText.getText().toString(),searchBy));
        sortArrayList();
        adapter = new FilmsAdapter(filmArray);
        adapter.setChangeExpandedPos(new FilmsAdapter.changeExpandedPositionInt() {
            @Override
            public void changeExpandedPosition(int newValue) {
                refreshAdapter();
                //adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                setExpandedPositionMain(newValue);
            }
        }
        );
    }

    public void setExpandedPositionMain(int x){
        expandedPosition = x;
    }
}