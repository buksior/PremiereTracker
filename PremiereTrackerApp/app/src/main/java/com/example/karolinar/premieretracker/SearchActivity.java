package com.example.karolinar.premieretracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Product> list = new ArrayList<>();
    private ProductsAdapter listAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ProductsAdapter(this, R.layout.search_list_view, list);
        listView.setAdapter(listAdapter);
        Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.content_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ImageButton searchButton = (ImageButton) findViewById(R.id.imageButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTable();
            }
        });
    }

    private void fillTable(){
        list.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
            try {
                URL url = new URL("https://igdbcom-internet-game-database-v1.p.mashape.com/games/?fields=first_release_date%2Cname&limit=50&offset=0&order=release_dates.date%3Adesc&search=zelda");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-Mashape-Key", "CT53butpo9mshilIIVZs6Bf1LS4Fp154lhwjsnJ4kNfjeWNNIP");
                urlConnection.setRequestProperty("Accept", "application/json");
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    ObjectMapper mapper = new ObjectMapper();
                    List<Game> games = mapper.readValue(stringBuilder.toString(), new TypeReference<List<Game>>(){});
                    for(Game g : games){
                        if(exists(g)){
                            g.setFavorite(true);
                        }
                    }
                    list.addAll(games);
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }

        //mock();
        listAdapter.notifyDataSetChanged();
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
