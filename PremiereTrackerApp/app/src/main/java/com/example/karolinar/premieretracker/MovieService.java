package com.example.karolinar.premieretracker;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marta on 20.04.2017.
 */

public class MovieService {

    private int numberOfMovies = 10;
    private Date today = new Date();

    public List<Movie> GetMoviesByTitle(String title) {
        List<Movie> movies = GetMoviesWhichContainTheTextInTitle(title);

        for (Iterator<Movie> iter = movies.iterator(); iter.hasNext(); ) {
            Movie movie = iter.next();
            if (!movie.getTitle().toLowerCase().equals(title.toLowerCase())) {
                iter.remove();
            }
        }

        return movies;
    }

    public List<Movie> GetMoviesWhichContainTheTextInTitle(String text) {
        List<Movie> movies = new LinkedList<Movie>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=ecf192cdd9fe2e8ff5a5699aeb9e12a2&query=" + URLEncoder.encode(text, "UTF-8"));
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
                JSONArray arrayMovies = jsonObj.getJSONArray("results");
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject jsonMovie = arrayMovies.getJSONObject(i);
                    JSONObject info = jsonMovie;

                    Movie movie = new Movie();
                    movie.setPremiereDate(convertDate(info.getString("release_date")));
                    movie.setTitle(info.getString("title"));

        if(movie.getPremiereDate().after(today) ){

            movies.add(movie);
        }

               }
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        if(movies.size() >= numberOfMovies){

            List<Movie> moviesLimited = new LinkedList<Movie>();

            for (int i=0; i<numberOfMovies; i++){
                moviesLimited.add(movies.get(i));
            }
            movies.clear();
            movies.addAll(moviesLimited);

        }

        return movies;
    }

    public List<Movie> GetMoviesByDirector(String director) {
        List<Movie> movies = GetMoviesWhichContainTheTextInDirector(director);

        for (Iterator<Movie> iter = movies.iterator(); iter.hasNext(); ) {
            Movie movie = iter.next();
            if (!movie.getAuthor().toLowerCase().equals(director.toLowerCase())) {
                iter.remove();
            }
        }

        return movies;
    }

    public List<Movie> GetMoviesWhichContainTheTextInDirector(String text) {

        List<Movie> movies = new LinkedList<Movie>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            String idDirector = getPersonId(text);

            URL url = new URL("https://api.themoviedb.org/3/person/" + URLEncoder.encode(idDirector, "UTF-8")+
                    "/combined_credits?api_key=ecf192cdd9fe2e8ff5a5699aeb9e12a2");

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

                JSONArray arrayMovies = jsonObj.getJSONArray("crew");
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject jsonMovie = arrayMovies.getJSONObject(i);
                    JSONObject info = jsonMovie;

                    Movie movie = new Movie();

                    String jobType=info.getString("job");

                if(jobType.equals("Director")){
                    movie.setPremiereDate(convertDate(info.getString("release_date")));
                    movie.setTitle(info.getString("title"));
                    movie.setDescription(info.getString("job"));
                    movie.setAuthor(getPersonName(idDirector));

                   if(movie.getPremiereDate().after(today) ) {
                       movies.add(movie);
                    }
                }

                }
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        if(movies.size() >= numberOfMovies){

            List<Movie> moviesLimited = new LinkedList<Movie>();

            for (int i=0; i<numberOfMovies; i++){
                moviesLimited.add(movies.get(i));
            }
            movies.clear();
            movies.addAll(moviesLimited);

        }

        return movies;
    }//



    public String getPersonId(String text){

        String idPerson="";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            URL url = new URL("https://api.themoviedb.org/3/search/person?api_key=ecf192cdd9fe2e8ff5a5699aeb9e12a2&query=" + URLEncoder.encode(text, "UTF-8"));
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
                JSONArray arrayMovies = jsonObj.getJSONArray("results");
                JSONObject jsonMovie = arrayMovies.getJSONObject(0);
                idPerson = jsonMovie.getString("id");

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        return idPerson;
    } ///

    public String getPersonName(String personId) {

        String getPersonName="";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            URL url = new URL("https://api.themoviedb.org/3/person/" + URLEncoder.encode(personId, "UTF-8")+
                    "?api_key=ecf192cdd9fe2e8ff5a5699aeb9e12a2" );

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
                getPersonName = jsonObj.getString("name");
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        return getPersonName;

    }//

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
