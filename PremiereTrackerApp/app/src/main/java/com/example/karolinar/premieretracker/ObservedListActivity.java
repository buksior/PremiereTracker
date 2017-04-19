package com.example.karolinar.premieretracker;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObservedListActivity extends AppCompatActivity {

    private ArrayList<Product> listProducts = new ArrayList<>();
    private ProductsAdapter observedProductsAdapter;
    private ListView listObservedProducts;
    private DatabaseManager databaseManager = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observed_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listObservedProducts = (ListView) findViewById(R.id.listObservedProducts);
        observedProductsAdapter = new ProductsAdapter(this, R.layout.observed_list_view, listProducts);
        listObservedProducts.setAdapter(observedProductsAdapter);
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.content_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTable();
            }
        });
    }

    private void mock(){
        listProducts.add(new Product("Steven Gerrard. Życie pozostawione na Anfield", new Date()));
        listProducts.add(new Product("Rozpacz", new Date()));
        listProducts.add(new Product("Ciemno, prawie noc", new Date()));
        listProducts.add(new Product("Franny i Zooey", new Date()));
        listProducts.add(new Product("Bieg po życie", new Date()));
        listProducts.add(new Product("Doktor Faustus", new Date()));
        listProducts.add(new Product("Swiat według Garpa", new Date()));
    }

    private void fillTable(){
        listProducts.clear();

        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        String producType = ConvertCategoryTextToProductType(spinnerCategory.getSelectedItem().toString());
        List<ProductEntity> products;

        if(producType == null) {
            products = databaseManager.GetProducts();
        }
        else {
            products = databaseManager.GetProducts(producType);
        }

        for(ProductEntity productEntity : products){
            listProducts.add(new Product(productEntity.Name, productEntity.Premiere));
        }

        observedProductsAdapter.notifyDataSetChanged();
    }

    private String ConvertCategoryTextToProductType(String category)
    {
        switch (category){
            case "Gry komputerowe":{
                return "Game";
            }
            case "Książki":{
                return "Book";
            }
            case "Filmy":{
                return "Movie";
            }
        }
        return null;
    }
}
