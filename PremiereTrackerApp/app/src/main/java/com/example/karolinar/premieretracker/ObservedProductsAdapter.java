package com.example.karolinar.premieretracker;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dominika on 21.04.2017.
 */


public class ObservedProductsAdapter extends ArrayAdapter<ProductEntity> {
    private static final String TAG = "ProductsAdapter";

    private Context context;
    private int layoutResourceId;
    private ArrayList<ProductEntity> data;


    public ObservedProductsAdapter(Context context, int layoutResourceId, ArrayList<ProductEntity> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final ProductEntity product = data.get(position);
        Log.d(TAG, "At position " + position + ": " + product.toString());

        // init drawable res here because I'm too lazy to type android.R.etc every time
        @DrawableRes final int dialogInfoId = android.R.drawable.ic_dialog_info;
        @DrawableRes final int deleteId = android.R.drawable.ic_delete;

        // create view if it doesn't exist
        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        // get interface views
        TextView title = (TextView) row.findViewById(R.id.col1);
        final TextView date = (TextView) row.findViewById(R.id.col2);
        final ImageButton btnInfo = (ImageButton) row.findViewById(R.id.btnInfo);
        final ImageButton btnDelete = (ImageButton) row.findViewById(R.id.btnDelete);

        // set appropriate values from product object
        title.setText(product.Name);
        date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(product.Premiere));

        btnDelete.setTag(position);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DatabaseManager databaseManager = new DatabaseManager(context);
                int position=(Integer)arg0.getTag();
                ProductEntity productToRemove = data.get(position);
                try {
                    databaseManager.RemoveProduct(productToRemove.Id);
                    data.remove(position);
                    notifyDataSetChanged();
                    getContext().getContentResolver().notify();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return row;
    }

}
