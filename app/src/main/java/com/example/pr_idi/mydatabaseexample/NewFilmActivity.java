package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by SigmundFreud on 21/12/16.
 */

public class NewFilmActivity extends AppCompatActivity {
    /**
     * setContentView(R.layout.add_film);
     */

    FilmData filmData;

    RatingBar ratingBar;
    TextView ratingText;
    Button submitButton;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Per cambiar el titol de la action bar
        getSupportActionBar().setTitle("New Film");
        /* Aixo seria per afegir el boto per tirar enrere o obrir el navigation drawer

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        */
        setContentView(R.layout.add_film_view);

        listenerForRatingBar();
        listenerForSubmitButton();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_new);

        mDrawerList = (ListView) findViewById(R.id.navListNew);
        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.headerlayout, mDrawerList, false);
        mDrawerList.addHeaderView(myHeader, null, false);
        addDrawerItems();

        setupDrawer();

        filmData = new FilmData(this);
        filmData.open();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void listenerForRatingBar() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBarQEdit);
        ratingText = (TextView) findViewById(R.id.ratingText_new);

        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        ratingText.setText(String.valueOf(Math.round(v * 2.0)));
                    }
                }
        );
    }

    public void listenerForSubmitButton() {
        submitButton = (Button) findViewById(R.id.submitButton_new);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean title = true;
                boolean director = true;
                boolean country = true;
                boolean year = true;
                boolean protagonist = true;

                EditText titleText = (EditText) findViewById(R.id.title_new);
                EditText directorText = (EditText) findViewById(R.id.director_new);
                EditText countryText = (EditText) findViewById(R.id.country_new);
                EditText yearText = (EditText) findViewById(R.id.year_new);
                EditText protagonistText = (EditText) findViewById(R.id.protagonist_new);

                if (titleText.length() == 0) {
                    title = false;
                    titleText.setError("Put a name");
                }
                if (directorText.length() == 0) {
                    director = false;
                    directorText.setError("Put a director");
                }
                if (countryText.length() == 0) {
                    country = false;
                    countryText.setError("Put a country");
                }
                if (yearText.length() == 0) {
                    year = false;
                    yearText.setError("Put a year");
                }
                if (protagonistText.length() == 0) {
                    protagonist = false;
                    protagonistText.setError("Put a protagonist");
                }

                if (title && director && country && year && protagonist) {
                    filmData.createFilm
                            (
                                    titleText.getText().toString().trim(),
                                    directorText.getText().toString().trim(),
                                    countryText.getText().toString().trim(),
                                    Integer.parseInt(yearText.getText().toString()),
                                    protagonistText.getText().toString().trim(),
                                    Integer.parseInt(ratingText.getText().toString())
                            );
                    filmData.close();
                    Toast.makeText(NewFilmActivity.this,"Film has been created",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }





    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("NewFilm Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    private void addDrawerItems() {
        String[] filmArray = {"My Films", "Add Film", "Help", "About"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmArray);
        mDrawerList.setAdapter(mAdapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        finish();
                        break;
                    case 2: //cas Add Film
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3: //cas Help
                        finish();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Intent i = new Intent(NewFilmActivity.this, HelpActivity.class);
                        startActivity(i);
                        break;
                    case 4: //cas About
                        finish();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        i = new Intent(NewFilmActivity.this, AboutActivity.class);
                        startActivity(i);
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
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }

            /** Es crida quan el drawer esta completament tancat */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("New Film");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Controlar els clicks de la action bar aqui.
        int id = item.getItemId();

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
}


