package com.example.karolinar.premieretracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Dominika on 21.04.2017.
 */

public class ObservedProductsAdapter extends CursorAdapter{
    private Context context;

    public ObservedProductsAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
        this.context = context;
    }

    @Override
    public View newView(Context context,Cursor cursor,ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.observed_list_view,parent,false);
    }

    @Override
    public void bindView( View view, Context context,Cursor cursor){
        TextView col1 = (TextView) view.findViewById(R.id.col1);
        TextView col2 = (TextView) view.findViewById(R.id.col2);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
        String premiere = cursor.getString(cursor.getColumnIndexOrThrow("Premiere"));
        col1.setText(name);
        col2.setText(premiere);
    }



}
