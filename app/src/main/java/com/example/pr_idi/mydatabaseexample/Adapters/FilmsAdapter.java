package com.example.pr_idi.mydatabaseexample.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.R;

import java.util.ArrayList;

/**
 * Created by SigmundFreud on 20/12/16.
 */

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.filmViewHolder>
{
    private ArrayList<Film> filmArray;

    public FilmsAdapter(ArrayList<Film> filmArray) {
        this.filmArray = filmArray;
    }

    @Override
    public filmViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_row,parent,false);
        filmViewHolder film = new filmViewHolder(v);
        return film;
    }

    @Override
    public void onBindViewHolder(filmViewHolder holder, int position)
    {
        holder.name.setText(filmArray.get(position).getTitle());
        String aux = filmArray.get(position).getDirector() + " - " + filmArray.get(position).getYear();
        holder.directorYear.setText(aux);
    }

    /** Number of rows */
    @Override
    public int getItemCount() {
        return filmArray.size();
    }

    /** Items of the row */
    public class filmViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, directorYear;

        public filmViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.filmName);
            directorYear = (TextView)itemView.findViewById(R.id.yearDirector);
        }
    }

}
