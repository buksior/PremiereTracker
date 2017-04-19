package com.example.karolinar.premieretracker;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static android.R.id.list;

/**
 * Created by Paulina on 16.04.2017.
 */

public class BookService {

    public List<Book> GetBooksByTitle(String title) {
        List<Book> books = GetBooksWhichContainTheTextInTitle(title);

        for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
            Book book = iter.next();
            if (!book.title.toLowerCase().equals(title.toLowerCase())) {
                iter.remove();
            }
        }

        return books;
    }

    public List<Book> GetBooksWhichContainTheTextInTitle(String text) {
        List<Book> books = new LinkedList<Book>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=intitle:" + URLEncoder.encode(text, "UTF-8"));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                JSONObject jsonObj = new JSONObject(stringBuilder.toString());
                JSONArray arrayBooks = jsonObj.getJSONArray("items");
                for (int i = 0; i < arrayBooks.length(); i++) {
                    JSONObject jsonBook = arrayBooks.getJSONObject(i);
                    JSONObject info = jsonBook.getJSONObject("volumeInfo");

                    Book book = new Book();
                    book.premiereDate = convertDate(info.getString("publishedDate"));
                    book.title = info.getString("title");

                    books.add(book);
                }
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return books;
    }

    private Date convertDate(String stringDate){
        Date date = null;
        try {
            switch (stringDate.length()){
                case 4:{
                    DateFormat format = new SimpleDateFormat("yyyy");
                    date = format.parse(stringDate);
                    break;
                }
                case 7:{
                    DateFormat format = new SimpleDateFormat("yyyy-mm");
                    date = format.parse(stringDate);
                    break;
                }
                case 10:{
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    date = format.parse(stringDate);
                    break;
                }
            }
        }
        catch (ParseException e){

        }
        return date;
    }
}