package com.example.karolinar.premieretracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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


        txtViewDescriptionValue.setMovementMethod(new ScrollingMovementMethod());
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
