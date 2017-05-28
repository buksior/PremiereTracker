package com.example.karolinar.premieretracker;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
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
        final DatabaseManager manager = new DatabaseManager(this);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ProductsAdapter(this, R.layout.search_list_view, list);
        listView.setAdapter(listAdapter);

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

                list.clear();

                String name = editText.getText().toString();
                BackgroundTask task = new BackgroundTask(SearchActivity.this, radioCategory, selectedCategory, name );
                task.execute();
            }
            // }).start();
            // }


        });
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

    private class BackgroundTask extends AsyncTask<Void, Void,  List<Product>> {
        private ProgressDialog dialog;
        private String radioCategory;
        private String selectedCategory;
        private String name;

        public BackgroundTask(SearchActivity activity, String radioCategory, String selectedCategory, String name) {
            this.radioCategory = radioCategory;
            this.selectedCategory = selectedCategory;
            this.name = name;
            dialog = new ProgressDialog(activity,android.R.style.Theme_Dialog);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Wyszukiwanie...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Product> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            list.addAll(result);
            listAdapter.notifyDataSetChanged();
            if(list.size()==0){
                Toast.makeText(SearchActivity.this.getApplicationContext(), "Brak wyników", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected List<Product> doInBackground(Void... params) {

            ArrayList<Product> list = new ArrayList<>();
            SearchService searchService = new SearchService();
            List<Product> productEntityList = new ArrayList<>();
            list.clear();

            if(radioCategory.equals("Tytuł")){
                switch (selectedCategory) {
                    case "Gry komputerowe":
                        productEntityList = searchService.findGameByTitle(name);
                        break;
                    case "Książki":
                        productEntityList = searchService.findBookByTitle(name);
                        break;
                    case "Filmy":
                        productEntityList = searchService.findMovieByTitle(name);
                        break;
                }
            }else if(radioCategory.equals("Autor")){
                switch (selectedCategory) {
                    case "Gry komputerowe":
                        productEntityList = searchService.findGameByStudio(name);
                        break;
                    case "Książki":
                        productEntityList = searchService.findBookByAuthor(name);
                        break;
                    case "Filmy":
                        productEntityList = searchService.findMovieByDirector(name);
                        break;
                }
            }
            return productEntityList;
        }

    }

}
