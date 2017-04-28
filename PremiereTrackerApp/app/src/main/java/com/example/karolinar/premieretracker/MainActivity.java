package com.example.karolinar.premieretracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager dbManager = new DatabaseManager(this);

        //ApproachingPremiereNotificationManager m = new ApproachingPremiereNotificationManager(this, (NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        //m.showNotyficationTodayPremiere();
        //m.showNotyficationDaysBerforePremiere(7);
        //BookService bookService = new BookService();
        //List<Book> list = bookService.GetBooksByTitle("harry potter i kamień filozoficzny");

       //MovieService movieService = new MovieService();
       // List<Movie> moviesList = movieService.GetMoviesWhichContainTheTextInTitle("Saw");

        if(dbManager.GetProductTypes().isEmpty()) {
            dbManager.AddProducsTypes();
        }

        Intent alarmApproachingPremiereIntent = new Intent(this, ApproachingPremiereAlarmReceiver.class);
        PendingIntent pendingApproachingPremiereIntent = PendingIntent.getBroadcast(this, 0, alarmApproachingPremiereIntent, 0);

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);

        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingApproachingPremiereIntent);
       //
//        ProductEntity p = new ProductEntity();
//        p.Creator = "Ala";
//        p.Description = "fmmvfmv";
//        p.Name = "Cos";
//        p.Premiere = new Date();
//        p.ProductType = "Book";
//        dbManager.AddProduct(p);
//        dbManager.RemoveProduct(1);
//        dbManager.GetProducts();

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

                if (isOnline()){
                     Intent startIntent = new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(startIntent);
                }else{

                    Toast.makeText(MainActivity.this.getApplicationContext(), "Aby korzystać korzystać ze wszystkich możliwość aplikacji musisz się połączyć z Internetem!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
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
