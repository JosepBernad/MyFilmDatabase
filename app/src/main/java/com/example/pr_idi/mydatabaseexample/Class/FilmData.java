package com.example.pr_idi.mydatabaseexample.Class;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.MySQLiteHelper;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private Context contextI;

    // Here we only select Title and Director, must select the appropriate columns
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_DIRECTOR,
            MySQLiteHelper.COLUMN_COUNTRY, MySQLiteHelper.COLUMN_YEAR_RELEASE,
            MySQLiteHelper.COLUMN_PROTAGONIST, MySQLiteHelper.COLUMN_CRITICS_RATE};

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);
        contextI = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Film createFilm(String title, String director, String country, int year, String protagonist, int rate)
    {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + title + " " + director);
        // Add data: Note that this method only provides title and director

        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, director);

        // Josep data
        values.put(MySQLiteHelper.COLUMN_COUNTRY, country);
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, year);
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, protagonist);
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, rate);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> getAllFilms() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null,MySQLiteHelper.COLUMN_TITLE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }


        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Film> getFilmsThat(String searchTerm, int searchBy)
    {
        List<Film> comments = new ArrayList<>();
        Cursor cursor;
        if(searchTerm != null && searchTerm.length()>0) {
            switch (searchBy) {
                case 0: //Search By Title
                    cursor = database.query(true, MySQLiteHelper.TABLE_FILMS, allColumns,
                            MySQLiteHelper.COLUMN_TITLE + " LIKE ?", new String[]
                                    {"%"+ searchTerm+ "%" }, null, null, null, null);
                    break;
                case 1://Search By Director
                    cursor = database.query(true, MySQLiteHelper.TABLE_FILMS, allColumns,
                            MySQLiteHelper.COLUMN_DIRECTOR + " LIKE ?", new String[]
                                    {"%"+ searchTerm+ "%" }, null, null, null, null);
                    break;
                case 2://Search By Year
                    cursor = database.query(true, MySQLiteHelper.TABLE_FILMS, allColumns,
                            MySQLiteHelper.COLUMN_YEAR_RELEASE + " LIKE ?", new String[]
                                    {"%"+ searchTerm+ "%" }, null, null, null, null);
                    break;
                case 3://Search By Actor
                    cursor = database.query(true, MySQLiteHelper.TABLE_FILMS, allColumns,
                            MySQLiteHelper.COLUMN_PROTAGONIST + " LIKE ?", new String[]
                                    {"%"+ searchTerm+ "%" }, null, null, null, null);
                    break;
                default:
                    cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                            allColumns, null, null, null, null,MySQLiteHelper.COLUMN_TITLE);
            }
        }
        else {
            cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                    allColumns, null, null, null, null,MySQLiteHelper.COLUMN_TITLE);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }


        // make sure to close the cursor
        cursor.close();
        return comments;

    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setDirector(cursor.getString(2));
        film.setCountry(cursor.getString(3));
        film.setYear(cursor.getInt(4));
        film.setProtagonist(cursor.getString(5));
        film.setCritics_rate(cursor.getInt(6));
        return film;
    }
    public Film getFilm(long id){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID+"='"+ id + "'", null, null, null,null);
        cursor.moveToFirst();
        return cursorToFilm(cursor);
    }

    public void modify(Film film){
        long id=film.getId();
        ContentValues cv = new ContentValues();
        cv.put(MySQLiteHelper.COLUMN_TITLE,film.getTitle());
        cv.put(MySQLiteHelper.COLUMN_DIRECTOR,film.getDirector());
        cv.put(MySQLiteHelper.COLUMN_COUNTRY,film.getCountry());
        cv.put(MySQLiteHelper.COLUMN_YEAR_RELEASE,film.getYear());
        cv.put(MySQLiteHelper.COLUMN_PROTAGONIST,film.getProtagonist());
        cv.put(MySQLiteHelper.COLUMN_CRITICS_RATE,film.getCritics_rate());
        database.update(MySQLiteHelper.TABLE_FILMS,cv,"_id='"+String.valueOf(id)+"'",null);
    }

}