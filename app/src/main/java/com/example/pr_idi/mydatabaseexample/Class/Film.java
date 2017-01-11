package com.example.pr_idi.mydatabaseexample.Class;

/**
 * Film
 * Created by pr_idi on 10/11/16.
 */


public class Film {

    // Basic film data manipulation class
    // Contains basic information on the film

    private long id;
    private String title;
    private String director;
    private String country;
    private int year;
    private String protagonist;
    private int critics_rate;



    /** ID */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /** Title */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    /** Director */
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    /** Country */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    /** Year */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    /** Protagonist */
    public String getProtagonist() {
        return protagonist;
    }

    public void setProtagonist(String protagonist) {
        this.protagonist = protagonist;
    }


    /** Critics Rate */
    public int getCritics_rate() {
        return critics_rate;
    }

    public void setCritics_rate(int critics_rate) {
        this.critics_rate = critics_rate;
    }



    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return String.format("%s - %s", title, director);
    }
}