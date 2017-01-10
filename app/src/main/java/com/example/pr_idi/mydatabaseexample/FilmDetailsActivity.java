package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;

/**
 * Created by SigmundFreud on 07/01/2017.
 */

public class FilmDetailsActivity extends Activity
{
    TextView title, director, protagonist, year, country;
    String sTitle, sDirector, sProtagonist, sCountry;
    int iYear, iRate;

    FloatingActionButton editButton;

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

        listenerForEditButton();

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

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

                i.putExtra("FILM_TITLE", sTitle);
                i.putExtra("FILM_DIRECTOR", sDirector);
                i.putExtra("FILM_COUNTRY", sCountry);
                i.putExtra("FILM_YEAR", iYear);
                i.putExtra("FILM_ACTOR", sProtagonist);
                i.putExtra("FILM_RATE", iRate);

                startActivity(i);

            }
        });
    }

}
