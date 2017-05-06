package com.example.karolinar.premieretracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObservedListActivity extends AppCompatActivity {


    private ObservedProductsAdapter observedProductsAdapter;
    private ListView listObservedProducts;
    DatabaseManager databaseManager = new DatabaseManager(this);
    ArrayList<ProductEntity>  listProducts = new ArrayList<>();
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observed_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listObservedProducts = (ListView) findViewById(R.id.listObservedProducts);
        List<ProductEntity> productEntityList = new ArrayList<>();
        productEntityList = databaseManager.GetProducts();
        listProducts.addAll(productEntityList);
        observedProductsAdapter = new ObservedProductsAdapter(this, R.layout.observed_list_view, listProducts);
        listObservedProducts.setAdapter(observedProductsAdapter);
        final Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.content_types_array_observed, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);


        listObservedProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductEntity productEntity = (ProductEntity) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(),DetailsActivity.class);
                intent.putExtra("PRODUCT_ID", Integer.toString(productEntity.Id));
             //   startActivityForResult(intent,(int)id);
               startActivity(intent);

            }
        });

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = spinnerCategory.getSelectedItem().toString();
                String databaseCagtegory = "Book";
                switch(selectedCategory){
                    case "Gry komputerowe" : databaseCagtegory = "Game";
                        break;
                    case "Książki" : databaseCagtegory = "Book";
                        break;
                    case "Filmy" : databaseCagtegory = "Movie";
                        break;

                    default: databaseCagtegory = "All";
                }
                listProducts.clear();
                try{
                    List<ProductEntity> productEntityList = new ArrayList<>();
                    if( !databaseCagtegory.equals("All")) {
                        productEntityList = databaseManager.GetProducts(databaseCagtegory);
                    } else {
                        productEntityList = databaseManager.GetProducts();
                    }
                    listProducts.addAll(productEntityList);
                }catch (Exception e){
                    System.out.println("Brak Produktow w bazie");
                }
                observedProductsAdapter.notifyDataSetChanged();
                //fillTable();
            }
        });
    }

    private void fillTable(){
        listProducts.clear();
        databaseManager.GetProducts();
        try{
            List<ProductEntity> productEntityList = new ArrayList<>();
            productEntityList = databaseManager.GetProducts();
            listProducts.addAll(productEntityList);
        }catch (Exception e){
            System.out.println("Brak Produktow w bazie");
        }
        observedProductsAdapter.notifyDataSetChanged();
    }

    public boolean exists(Product p){
        ProductEntity entity = new ProductEntity();
        entity.Description = "";
        entity.Name = p.getTitle();
        entity.Premiere = p.getPremiereDate();
        entity.ProductType = p.getClass().getSimpleName();
        entity.Creator = "";
        DatabaseManager db = new DatabaseManager(this);
        return db.existsProduct(entity);
    }
}
