package com.example.pr_idi.mydatabaseexample.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pr_idi.mydatabaseexample.Class.Film;
import com.example.pr_idi.mydatabaseexample.Class.FilmData;
import com.example.pr_idi.mydatabaseexample.MainActivity;
import com.example.pr_idi.mydatabaseexample.R;

import java.util.ArrayList;

/**
 * Created by SigmundFreud on 20/12/16.
 */

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.filmViewHolder>
{
    private ArrayList<Film> filmArray;
    private int expandedPosition = -1;
    private Context context;

    public FilmsAdapter(ArrayList<Film> filmArray) {
        this.filmArray = filmArray;
    }

    @Override
    public filmViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
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
        String aux2 = String.valueOf(filmArray.get(position).getCritics_rate());
        holder.rate.setText(aux2);
        holder.ratingBar.setRating(Float.parseFloat(holder.rate.getText().toString())/2.0f);
        holder.actualRateText.setText(String.valueOf(holder.rate.getText().toString()));

        if (position == expandedPosition) {
            holder.llExpandArea.setVisibility(View.VISIBLE);
        } else {
            holder.llExpandArea.setVisibility(View.GONE);
        }
    }

    /** Number of rows */
    @Override
    public int getItemCount() {
        return filmArray.size();
    }

    /** Items of the row */
    public class filmViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView name, directorYear,actualRateText;
        Button rate, doneButton, deleteButton;
        RatingBar ratingBar;
        RelativeLayout llExpandArea,origHolder;

        public filmViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.filmName);
            directorYear = (TextView)itemView.findViewById(R.id.yearDirector);
            rate = (Button)itemView.findViewById(R.id.ratingButton);
            origHolder = (RelativeLayout)itemView.findViewById(R.id.origHolder);
            llExpandArea = (RelativeLayout)itemView.findViewById(R.id.llExpandArea);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBarQEdit);
            doneButton = (Button)itemView.findViewById(R.id.doneButton);
            deleteButton = (Button)itemView.findViewById(R.id.deleteButton);
            actualRateText = (TextView)itemView.findViewById(R.id.actualRateText);
            //ratingBar.setRating(Float.parseFloat(rate.getText().toString())/2.0f);
            //actualRateText.setText(String.valueOf(rate.getText().toString()));
            listenersForExpanded();
        }

        public void listenersForExpanded() {
            //ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBarQEdit);
            //actualRateText = (TextView) itemView.findViewById(R.id.actualRateText);
            origHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    expandedPosition = -1;
                    updateExpandedPos(-1);
                    llExpandArea.setVisibility(View.GONE);
                }
            });
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    FilmData filmData = new FilmData(context);
                    filmData.open();
                    Film aux = filmArray.get(expandedPosition);
                    aux.setCritics_rate(Integer.parseInt(actualRateText.getText().toString()));
                    filmData.modify(aux);
                    filmData.close();
                    expandedPosition = -1;
                    notifyItemChanged(expandedPosition);
                    updateExpandedPos(-1);
                    Toast.makeText(context, "Film Rate modified", Toast.LENGTH_SHORT).show();
                    llExpandArea.setVisibility(View.GONE);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final AlertDialog deleteConfirm = builder.create();
                    builder.setTitle("Do you want to delete this?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FilmData filmData = new FilmData(context);
                            filmData.open();
                            filmData.deleteFilm(filmArray.get(expandedPosition));
                            filmArray.remove(expandedPosition);
                            filmData.close();
                            notifyItemChanged(expandedPosition);
                            expandedPosition = -1;
                            updateExpandedPos(-1);
                            llExpandArea.setVisibility(View.GONE);
                            Toast.makeText(context, "The film has been deleted", Toast.LENGTH_SHORT).show();
                            deleteConfirm.dismiss();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteConfirm.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
            ratingBar.setOnRatingBarChangeListener(
                    new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            actualRateText.setText(String.valueOf(Math.round(v * 2.0)));
                        }
                    }
            );
        }
    }

    public void setExpandedPosition(int expandI){
        expandedPosition = expandI;

        // Check for an expanded view, collapse if you find one
        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            notifyItemChanged(prev);
        }
        // Set the current position to "expanded"
        expandedPosition = expandI;
        notifyItemChanged(expandedPosition);
    }
    public void updateExpandedPos(int x){
        if (mChangePos != null) {
            mChangePos.changeExpandedPosition(x);
        }
    }

    public interface changeExpandedPositionInt {
        void changeExpandedPosition(int newValue);
    }
    private changeExpandedPositionInt mChangePos;

    public void setChangeExpandedPos(changeExpandedPositionInt l) {
        mChangePos = l;
    }
}
