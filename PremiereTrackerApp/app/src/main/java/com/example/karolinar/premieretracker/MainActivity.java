package com.example.karolinar.premieretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //BookService bookService = new BookService();
        //bookService.GetBooksWhichContainTheTextInTitle("harry potter");
        //List<Book> list = bookService.GetBooksByTitle("harry potter i kamień filozoficzny");
        DatabaseManager dbManager = new DatabaseManager(this);
        if(dbManager.GetProductTypes().isEmpty()) {
            dbManager.AddProducsTypes();
        }
       //
       // Product p = new Product();
       // p.Creator = "Ala";
       // p.Description = "fmmvfmv";
       // p.Name = "Cos";
       // p.Premiere = new Date();
       // p.ProductType = "Book";
       // dbManager.AddProduct(p);
       // dbManager.RemoveProduct(1);
       // dbManager.GetProducts();

        setContentView(R.layout.activity_first);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        //Intent myIntent = new Intent(this, ObservedListActivity.class);
        //startActivity(myIntent);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(startIntent);
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
