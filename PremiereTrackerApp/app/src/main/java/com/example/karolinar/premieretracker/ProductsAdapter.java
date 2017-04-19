package com.example.karolinar.premieretracker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Karolina R on 14.04.2017.
 */

public class ProductsAdapter extends ArrayAdapter<Product> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Product> data;

    public ProductsAdapter(Context context, int layoutResourceId, ArrayList<Product> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ProductHolder holder = null;
        Product product = data.get(position);

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ProductHolder();
            final String title = product.getTitle();
            holder.title = (TextView)row.findViewById(R.id.col1);
            holder.date = (TextView)row.findViewById(R.id.col2);

            //final ImageButton im = (ImageButton)row.findViewById(R.id.liked);
            //im.setFocusable(false);
            //im.setFocusableInTouchMode(false);
            //final boolean selected = false;
            //im.setOnClickListener(new View.OnClickListener() {
             //   @Override
            //    public void onClick(View v) {
            //        im.setImageResource(android.R.drawable.star_big_on);
            //       //TODO database
            //    }
            //});

            row.setTag(holder);
        }
        else{
            holder = (ProductHolder) row.getTag();
        }



        holder.title.setText(product.getTitle());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(product.getPremiereDate());
        holder.date.setText(date);

        return row;
    }

    private static class ProductHolder{
        TextView title;
        TextView date;
    }
}
