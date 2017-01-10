package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;

/**
 * Created by SigmundFreud on 07/01/2017.
 */

public class FilmDetailsActivity extends Activity
{
    TextView title, director, protagonist, year, country;
    String sTitle, sDirector, sProtagonist, sCountry;
    int iYear, iRate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    //public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        //super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.film_details);
        //getSupportActionBar().setTitle("Film Details");
        sTitle = getIntent().getStringExtra("FILM_TITLE");
        sDirector = getIntent().getStringExtra("FILM_DIRECTOR");
        sProtagonist = getIntent().getStringExtra("FILM_ACTOR");
        sCountry = getIntent().getStringExtra("FILM_COUNTRY");
        iYear = getIntent().getIntExtra("FILM_YEAR",2000);
        iRate = getIntent().getIntExtra("FILM_RATE", 5);

        title = (TextView)findViewById(R.id.titleTextView);
        director = (TextView)findViewById(R.id.directorTextView);
        year = (TextView)findViewById(R.id.yearTextView);

        title.setText(sTitle);
        director.setText(sDirector);
        year.setText(String.valueOf(iYear));

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
