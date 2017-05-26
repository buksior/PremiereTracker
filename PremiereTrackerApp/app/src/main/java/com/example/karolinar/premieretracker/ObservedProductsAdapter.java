package com.example.karolinar.premieretracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        @DrawableRes final int deleteId = android.R.drawable.ic_delete;

        // create view if it doesn't exist
        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        // get interface views
        TextView title = (TextView) row.findViewById(R.id.col1);
        final TextView date = (TextView) row.findViewById(R.id.col2);
        final ImageButton btnDelete = (ImageButton) row.findViewById(R.id.btnDelete);

        // set appropriate values from product object
        title.setText(product.Name);
        date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(product.Premiere));

        btnDelete.setTag(position);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View arg0) {

                context=getContext();

                AlertDialog.Builder dialog = new AlertDialog.Builder(context, android.R.style.Theme_Dialog);
                dialog.setCancelable(false);
                dialog.setTitle("Usuwanie z listy ulubionych");
                dialog.setMessage("Czy na pewno chcesz usunąć produkt z listy obserwowanych?" );
                 AlertDialog.Builder ok = dialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseManager databaseManager = new DatabaseManager(context);
                        int position = (Integer) arg0.getTag();
                        ProductEntity productToRemove = data.get(position);
                        try {
                            databaseManager.RemoveProduct(productToRemove.Id);
                            data.remove(position);
                            notifyDataSetChanged();
                            getContext().getContentResolver().notify();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, "Produkt usunięto z listy obserwowanych", Toast.LENGTH_SHORT).show();
                    }
                })
                        .setNegativeButton("Anuluj ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });


        return row;
    }
}
