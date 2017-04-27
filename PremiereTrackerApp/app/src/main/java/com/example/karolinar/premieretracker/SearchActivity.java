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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private EditText editText;
    private RadioGroup radioGroup;
    private RadioButton radioGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ProductsAdapter(this, R.layout.search_list_view, list);
        listView.setAdapter(listAdapter);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.content_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        radioGroup =(RadioGroup) findViewById(R.id.radioGroup);
        editText = (EditText) findViewById(R.id.editText);

        ImageButton searchButton = (ImageButton) findViewById(R.id.imageButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioGroupButton = (RadioButton) findViewById(selectedId);
                String radioCategory = (String) radioGroupButton.getText();
                String selectedCategory = spinner.getSelectedItem().toString();
                SearchService searchService = new SearchService();
                List<Product> productEntityList = new ArrayList<>();
                list.clear();
                if(radioCategory.equals("Tytuł")){
                    switch (selectedCategory) {
                        case "Gry komputerowe":
                            productEntityList = searchService.findGameByTitle(editText.getText().toString());
                            list.addAll(productEntityList);
                            break;
                        case "Książki":
                            productEntityList = searchService.findBookByTitle(editText.getText().toString());
                            list.addAll(productEntityList);
                            break;
                        case "Filmy":
                            productEntityList = searchService.findMovieByTitle(editText.getText().toString());
                            list.addAll(productEntityList);
                            break;
                    }
                }else if(radioCategory.equals("Autor")){
                    switch (selectedCategory) {
                        case "Gry komputerowe":
                            productEntityList = searchService.findGameByStudio(editText.getText().toString());
                            list.addAll(productEntityList);
                            break;
                        case "Książki":
                            productEntityList = searchService.findBookByAuthor(editText.getText().toString());
                            list.addAll(productEntityList);
                            break;
                        case "Filmy":
                            productEntityList = searchService.findMovieByDirector(editText.getText().toString());
                            list.addAll(productEntityList);
                            break;
                    }
                }

                listAdapter.notifyDataSetChanged();

                //fillTable();
            }
        });
    }

    private void fillTable(){
        list.clear();
        GameService service = new GameService();
        List<Game> games = service.getGamesWhichContainsTheTextInAuthor("konami");
        for(Game g : games){
            if(exists(g)){
                g.setFavorite(true);
            }
        }
        list.addAll(games);

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
