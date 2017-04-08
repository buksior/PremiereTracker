package com.example.karolinar.premieretracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paulina on 08.04.2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context) {
        super(context, "test3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ProductTypes(" +
                "Name nchar(10) primary key" +
                ")");

        AddProducsTypes();

        db.execSQL("CREATE TABLE Products(" +
                "Id integer primary key autoincrement" +
                ",Name nchar(30)" +
                ",ProductType nchar(10)" +
                ",Premiere datetime NOT NULL" +
                ",Description nchar(255) NOT NULL" +
                ",Creator nchar(30) NOT NULL" +
                ",FOREIGN KEY (ProductType) REFERENCES ProductTypes(Name)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void AddProducsTypes() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues book = new ContentValues();
        book.put("Name", "Book");
        ContentValues game = new ContentValues();
        game.put("Name", "Game");
        ContentValues movie = new ContentValues();
        movie.put("Name", "Movie");

        db.insertOrThrow("ProductTypes", null, book);
        db.insertOrThrow("ProductTypes", null, game);
        db.insertOrThrow("ProductTypes", null, movie);
    }
}
