package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;

/**
 * Created by SigmundFreud on 10/01/2017.
 */

public class EditFilmActivity extends AppCompatActivity
{
    FilmData filmData;

    EditText title, director, protagonist, year, country;
    TextView rate;
    RatingBar ratingBar;
    Button submit, cancel;

    String sTitle, sDirector, sProtagonist, sCountry;
    int iYear, iRate;
    long mId;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);


    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_film_view);

        getSupportActionBar().setTitle("Edit film");

        //Old values
        mId = getIntent().getLongExtra("FILM_ID",0);
        sTitle = getIntent().getStringExtra("FILM_TITLE");
        sDirector = getIntent().getStringExtra("FILM_DIRECTOR");
        sProtagonist = getIntent().getStringExtra("FILM_ACTOR");
        sCountry = getIntent().getStringExtra("FILM_COUNTRY");
        iYear = getIntent().getIntExtra("FILM_YEAR", 2000);
        iRate = getIntent().getIntExtra("FILM_RATE", 5);

        title = (EditText) findViewById(R.id.title_edit);
        director = (EditText) findViewById(R.id.director_edit);
        protagonist = (EditText) findViewById(R.id.protagonist_edit);
        year = (EditText) findViewById(R.id.year_edit);
        country = (EditText) findViewById(R.id.country_edit);

        rate = (TextView) findViewById(R.id.ratingText_edit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_edit);

        title.setText(sTitle);
        director.setText(sDirector);
        protagonist.setText(sProtagonist);
        country.setText(sCountry);
        year.setText(String.valueOf(iYear));

        rate.setText(String.valueOf(iRate));
        float fRate = (float) (iRate);
        ratingBar.setRating(fRate / 2);

        filmData = new FilmData(this);
        filmData.open();
    }
}
