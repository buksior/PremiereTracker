package com.example.karolinar.premieretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    TextView txtViewNameValue, txtViewCategoryValue, txtViewPremiereValue, txtViewCreatorValue, txtViewDescriptionValue;
    DatabaseManager dbManager;
    ProductEntity productEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtViewNameValue = (TextView) findViewById(R.id.txtViewNameValue);
        txtViewCategoryValue = (TextView) findViewById(R.id.txtViewCategoryValue);
        txtViewCreatorValue = (TextView) findViewById(R.id.txtViewCreatorValue);
        txtViewDescriptionValue = (TextView) findViewById(R.id.txtViewDescriptionValue);
        txtViewPremiereValue = (TextView) findViewById(R.id.txtViewPremiereDateValue);


        txtViewDescriptionValue.setMovementMethod(new ScrollingMovementMethod());
        String productId = getIntent().getStringExtra("PRODUCT_ID");
        dbManager = new DatabaseManager(this);
        productEntity = dbManager.getProductById(productId);
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
        txtViewCreatorValue.setClickable(true);
        txtViewCreatorValue.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                                                       intent.putExtra("CREATOR", txtViewCreatorValue.getText());
                                                       intent.putExtra("CATEGORY", productEntity.ProductType.toString());
                                                       startActivity(intent);
                                                   }
                                               }
        );
        txtViewPremiereValue.setText(format.format(productEntity.Premiere));
        txtViewDescriptionValue.setText(productEntity.Description);
    }
}
