package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;


/**
 * Created by SigmundFreud on 21/12/16.
 */

public class NewFilmActivity extends Activity {
    /**
     * setContentView(R.layout.add_film);
     */

    FilmData filmData;

    RatingBar ratingBar;
    TextView ratingText;
    Button submitButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setContentView(R.layout.add_film_view);

        listenerForRatingBar();
        listenerForSubmitButton();

        filmData = new FilmData(this);
        filmData.open();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void listenerForRatingBar() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingText = (TextView) findViewById(R.id.ratingText);

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
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean title = true;
                boolean director = true;
                boolean country = true;
                boolean year = true;
                boolean protagonist = true;

                EditText titleText = (EditText) findViewById(R.id.title_edit);
                EditText directorText = (EditText) findViewById(R.id.director_edit);
                EditText countryText = (EditText) findViewById(R.id.country_edit);
                EditText yearText = (EditText) findViewById(R.id.year_edit);
                EditText protagonstText = (EditText) findViewById(R.id.protagonist_edit);

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
                if (protagonstText.length() == 0) {
                    protagonist = false;
                    protagonstText.setError("Put a protagonist");
                }

                if (title && director && country && year && protagonist) {
                    Film film;
                    film = filmData.createFilm
                            (
                                    capitalizeFirstLetter(titleText.getText().toString().trim()),
                                    capitalizeFirstLetter(directorText.getText().toString().trim()),
                                    capitalizeFirstLetter(countryText.getText().toString().trim()),
                                    Integer.parseInt(yearText.getText().toString()),
                                    capitalizeFirstLetter(protagonstText.getText().toString().trim()),
                                    Integer.parseInt(ratingText.getText().toString())
                            );
                    filmData.close();
                    finish();
                }

            }
        });
    }



    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
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
}


