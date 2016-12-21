package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setContentView(R.layout.add_film_view);

        listenerForRatingBar();
        listenerForSubmitButton();

        filmData = new FilmData(this);
        filmData.open();
    }



    public void listenerForRatingBar()
    {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingText = (TextView) findViewById(R.id.ratingText);

        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener()
                {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                    {
                        ratingText.setText(String.valueOf(Math.round(v*2.0)));
                    }
                }
        );
    }

    public void listenerForSubmitButton()
    {
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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

                if (titleText.length() == 0)
                {
                    title = false;
                    titleText.setError("Put a name");
                }
                if (directorText.length() == 0)
                {
                    director = false;
                    directorText.setError("Put a director");
                }
                if (countryText.length() == 0)
                {
                    country = false;
                    countryText.setError("Put a country");
                }
                if (yearText.length() == 0)
                {
                    year = false;
                    yearText.setError("Put a year");
                }
                if (protagonstText.length() == 0)
                {
                    protagonist = false;
                    protagonstText.setError("Put a protagonist");
                }

                if (title && director && country && year)
                {
                    Film film;
                    film = filmData.createFilm(titleText.getText().toString(),
                            directorText.getText().toString(),
                            countryText.getText().toString(),
                            Integer.parseInt(yearText.getText().toString()),
                            protagonstText.getText().toString(),
                            Integer.parseInt(ratingText.getText().toString()));

                    filmData.close();
                    finish();
                }


            }
        });

    }



}


