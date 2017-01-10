package com.example.pr_idi.mydatabaseexample.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by roger on 7/01/17.
 */

public class SpinnerAdapter<T> extends ArrayAdapter<String> {
    public SpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
    }
    
    //modifiquem el view del Spinner per ocultar el text i tenir un spinner nomes amb la imatge que volem
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText("");
        return view;
    }
}