package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;
import com.google.android.gms.appindexing.AppIndex;

/**
 * Created by SigmundFreud on 07/01/2017.
 */

public class FilmDetailsActivity extends Activity
{
    TextView title, director, protagonist, year, country, rate;
    String sTitle, sDirector, sProtagonist, sCountry;
    int iYear, iRate;
    long mId;
    FilmData filmData;

    FloatingActionButton editButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        //super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.film_details);

        //getSupportActionBar().setTitle("Film Details");
        mId = getIntent().getLongExtra("FILM_ID",0);
        filmData = new FilmData(FilmDetailsActivity.this);
        filmData.open();
        Film aux = filmData.getFilm(mId);
        //filmData.close();
        sTitle = aux.getTitle();
        sDirector = aux.getDirector();
        sProtagonist = aux.getProtagonist();
        sCountry = aux.getCountry();
        iYear = aux.getYear();
        iRate = aux.getCritics_rate();

        title = (TextView)findViewById(R.id.titleTextView);
        director = (TextView)findViewById(R.id.directorTextView);
        year = (TextView)findViewById(R.id.yearTextView);
        protagonist = (TextView) findViewById(R.id.protagonistTextView);
        country = (TextView) findViewById(R.id.placeTextView);
        rate = (TextView) findViewById(R.id.rateTextView);

        title.setText(sTitle);
        director.setText(sDirector);
        year.setText(String.valueOf(iYear));
        country.setText(sCountry);
        protagonist.setText(sProtagonist);
        rate.setText(String.valueOf(iRate));


        listenerForEditButton();

    }
    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {
        super.onStop();
        filmData.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mId = getIntent().getLongExtra("FILM_ID",0);
        FilmData filmData = new FilmData(this);
        filmData.open();
        Film aux = filmData.getFilm(mId);
        filmData.close();

        sTitle = aux.getTitle();
        sDirector = aux.getDirector();
        sProtagonist = aux.getProtagonist();
        sCountry = aux.getCountry();
        iYear = aux.getYear();
        iRate = aux.getCritics_rate();

        title.setText(sTitle);
        director.setText(sDirector);
        year.setText(String.valueOf(iYear));
        country.setText(sCountry);
        protagonist.setText(sProtagonist);
        rate.setText(String.valueOf(iRate));
    }

    public void listenerForEditButton()
    {
        editButton = (FloatingActionButton) findViewById(R.id.edit_button);

        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(FilmDetailsActivity.this,"ueeep",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(FilmDetailsActivity.this, EditFilmActivity.class);

                i.putExtra("FILM_ID", mId);

                startActivity(i);

            }
        });
    }

}
