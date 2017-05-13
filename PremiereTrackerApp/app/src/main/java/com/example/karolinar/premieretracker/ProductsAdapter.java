package com.example.karolinar.premieretracker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Karolina R on 14.04.2017.
 */

public class ProductsAdapter extends ArrayAdapter<Product> {
    private static final String TAG = "ProductsAdapter";

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
        final Product product = data.get(position);
        Log.d(TAG, "At position " + position + ": " + product.toString());

        @DrawableRes final int starLikedResId = android.R.drawable.star_big_on;
        @DrawableRes final int starNotLikedResId = android.R.drawable.star_big_off;

        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView title = (TextView) row.findViewById(R.id.col1);
        final TextView date = (TextView) row.findViewById(R.id.col2);
        final ImageView star = (ImageView) row.findViewById(R.id.liked);

        title.setText(product.getTitle());
        if(product.getPremiereDate() != null) {
            date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(product.getPremiereDate()));
        }
        star.setImageResource(product.isFavorite() ? starLikedResId : starNotLikedResId);

        star.setTag(position);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int innerPosition = (int) view.getTag();
                ImageView innerImageView = (ImageView) view;

                if(data.get(innerPosition).isFavorite()) {
                    innerImageView.setImageResource(starNotLikedResId);
                    data.get(innerPosition).setFavorite(false);
                }
                else {
                    innerImageView.setImageResource(starLikedResId);
                    Product p = data.get(innerPosition);
                    p.setFavorite(true);
                    DatabaseManager manager = new DatabaseManager(context);
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.Name = p.getTitle();
                    productEntity.ProductType = p.getProductType();
                    productEntity.Premiere = p.getPremiereDate();
                    if(p.getAuthor() == null)
                    {
                        productEntity.Creator = "";
                    }
                    else
                    {
                        productEntity.Creator = p.getAuthor();
                    }
                    if(p.getDescription() == null)
                    {
                        productEntity.Description = "";
                    }
                    else
                    {
                        productEntity.Description = p.getDescription();
                    }

                    if(!manager.existsProduct(productEntity)){
                        manager.AddProduct(productEntity);
                    }

                    List<ProductEntity> list = manager.GetProducts();
                }

            }
        });

        return row;
    }

}
