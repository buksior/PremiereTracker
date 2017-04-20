package com.example.karolinar.premieretracker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Dominika on 20.04.2017.
 */

public class ObservedProductAdapter  extends ArrayAdapter<Product> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Product> data;

    public ObservedProductAdapter(Context context, int layoutResourceId, ArrayList<Product> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ObservedProductAdapter.ProductHolder holder = null;
        final Product product = data.get(position);
        int pos = position;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ObservedProductAdapter.ProductHolder();
            final String title = product.getTitle();
            holder.title = (TextView)row.findViewById(R.id.col1);
            holder.date = (TextView)row.findViewById(R.id.col2);


            final ImageButton btnDelete = (ImageButton)row.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    data.remove(data.get(position));
                    DatabaseManager dbManager = new DatabaseManager(context);

                    dbManager.RemoveProduct(data.indexOf(product));
                    notifyDataSetChanged();
                }
            });


            row.setTag(holder);
        }
        else{
            holder = (ObservedProductAdapter.ProductHolder) row.getTag();
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
