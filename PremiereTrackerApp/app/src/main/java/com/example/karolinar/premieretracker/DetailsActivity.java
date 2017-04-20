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

        dbManager=new DatabaseManager(this);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        ProductEntity p1= new ProductEntity();
        p1.ProductType="Film";
        p1.Name="TestFilm";
        p1.Creator="autor";
        p1.Description="Opis";
        p1.Premiere= new Date();

        dbManager.AddProduct(p1);

        List<ProductEntity> products = new LinkedList<ProductEntity>();
        products=dbManager.GetProducts();

        ProductEntity pe = new ProductEntity();

        if(products.size()>0){

            // TODO przekazywanie id produktu z ObservedListActivity
            pe = products.get(1);
            txtViewNameValue.setText(pe.Name);
            txtViewCategoryValue.setText(pe.ProductType);
            txtViewCreatorValue.setText(pe.Creator);
            txtViewPremiereValue.setText(format.format(pe.Premiere));
            txtViewDescriptionValue.setText(pe.Description);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
