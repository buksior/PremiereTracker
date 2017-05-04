package com.example.karolinar.premieretracker;

import android.os.StrictMode;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
 * Created by Dominika on 21.04.2017.
 */

public class GameService {
    public List<Game> GetGamesByTitle(String title) {
        List<Game> games = GetGamesWhichContainTheTextInTitle(title);

        for (Iterator<Game> iter = games.iterator(); iter.hasNext(); ) {
            Game game = iter.next();
            if (!game.getTitle().toLowerCase().equals(title.toLowerCase())) {
                iter.remove();
            }
        }

        return games;
    }

    public List<Game> GetGamesWhichContainTheTextInTitle(String text) {
        List<Game> games = new LinkedList<Game>();
        String address = "https://igdbcom-internet-game-database-v1.p.mashape.com/games/?fields=first_release_date%2Cname%2Cdevelopers%2Csummary&limit=50&offset=0&order=release_dates.date%3Adesc&search=";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            URL url = new URL(address + URLEncoder.encode(text, "UTF-8"));
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
                games = mapper.readValue(stringBuilder.toString(), new TypeReference<List<Game>>(){});
                Iterator<Game> iterator = games.iterator();
                while(iterator.hasNext()){
                    Game game = iterator.next();
                    if(game.getPremiereDate().before(new Date())){
                        iterator.remove();
                    }
                    else{
                        if(game.getDevelopers().size() > 0){
                            Integer companyId = (Integer)game.getDevelopers().get(0);
                            game.setAuthor(getCompanyNameById(companyId));
                        }
                        game.setProductType("Game");
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

        return games;
    }

    public List<Game> getGamesWhichContainsTheTextInAuthor(String text){
        String address = "https://igdbcom-internet-game-database-v1.p.mashape.com/companies/?fields=developed&limit=10&offset=0&search=";
        List<Game> games = new LinkedList<Game>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            URL url = new URL(address + URLEncoder.encode(text, "UTF-8"));
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

                JSONArray arrayCompanies = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < arrayCompanies.length(); i++) {
                    JSONObject gamesArray = arrayCompanies.getJSONObject(i);
                    if(gamesArray.has("developed")) {
                        JSONArray arrayGames = gamesArray.getJSONArray("developed");
                        for (int j = 0; j < arrayGames.length(); j++) {
                            Game game = getGameById((int) arrayGames.get(j));
                            if (game != null && game.getPremiereDate().after(new Date())) {
                                game.setAuthor(gamesArray.getString("name"));
                                game.setProductType("Game");
                                games.add(game);
                            }
                        }
                    }
                    //game.setPremiereDate(convertDate(info.getString("release_date")));
                    //game.setTitle(info.getString("title"));
                }
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return games;
    }

    private Game getGameById(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL("https://igdbcom-internet-game-database-v1.p.mashape.com/games/" + id + "?fields=first_release_date%2Cname%2Cdevelopers%2Csummary&limit=50&offset=0&order=release_dates.date%3Adesc");
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
                return games.get(0);
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        return null;
    }

    private String getCompanyNameById(int companyId){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL("https://igdbcom-internet-game-database-v1.p.mashape.com/companies/" + companyId + "?fields=name&limit=10&offset=0");
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
                JSONArray arrayBooks = new JSONArray(stringBuilder.toString());
                JSONObject object = arrayBooks.getJSONObject(0);
                return object.getString("name");
                //ObjectMapper mapper = new ObjectMapper();
                //List<Game> games = mapper.readValue(stringBuilder.toString(), new TypeReference<List<Game>>(){});
                //return games.get(0);
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        return "";
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
