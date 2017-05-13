package com.example.karolinar.premieretracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.R.attr.format;

public class DetailsActivity extends AppCompatActivity {

    TextView txtViewNameValue, txtViewCategoryValue, txtViewPremiereValue, txtViewCreatorValue, txtViewDescriptionValue;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView txtViewNameValue = (TextView) findViewById(R.id.txtViewNameValue);
        TextView txtViewCategoryValue = (TextView) findViewById(R.id.txtViewCategoryValue);
        TextView txtViewCreatorValue = (TextView) findViewById(R.id.txtViewCreatorValue);
        TextView txtViewDescriptionValue = (TextView) findViewById(R.id.txtViewDescriptionValue);
        TextView txtViewPremiereValue = (TextView) findViewById(R.id.txtViewPremiereDateValue);

        String productId = getIntent().getStringExtra("PRODUCT_ID");
        dbManager = new DatabaseManager(this);
        ProductEntity productEntity = dbManager.getProductById(productId);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        txtViewNameValue.setText(productEntity.Name);

        switch (productEntity.ProductType)
        {
            case "Movie":
            {
                txtViewCategoryValue.setText("Film");
                break;
            }
            case "Game":
            {
                txtViewCategoryValue.setText("Gra komputerowa");
                break;
            }
            case "Book":
            {
                txtViewCategoryValue.setText("Książka");
                break;
            }
        }

        txtViewCreatorValue.setText(productEntity.Creator);
        txtViewPremiereValue.setText(format.format(productEntity.Premiere));
        txtViewDescriptionValue.setText(productEntity.Description);
    }
}
