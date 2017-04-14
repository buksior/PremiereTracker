package com.example.karolinar.premieretracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paulina on 08.04.2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context) {
        super(context, "premiereTrackerDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ProductTypes(" +
                "Name nchar(10) primary key" +
                ")");

        db.execSQL("CREATE TABLE Products(" +
                "Id integer primary key autoincrement" +
                ",Name nchar(30) NOT NULL" +
                ",ProductType nchar(10) NOT NULL" +
                ",Premiere datetime NOT NULL" +
                ",Description nchar(255) NOT NULL" +
                ",Creator nchar(30) NOT NULL" +
                ",FOREIGN KEY (ProductType) REFERENCES ProductTypes(Name)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void AddProducsTypes() {
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
        db.close();
    }

    public void AddProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues p = new ContentValues();
        p.put("Name", product.Name);
        p.put("Creator", product.Creator);
        p.put("Description", product.Description);
        p.put("Premiere", product.Premiere.getTime());
        p.put("ProductType", product.ProductType);

        db.insertOrThrow("Products", null, p);
        db.close();
    }

    public List<Product> GetProducts(){
        String[] columns = {"Id", "Name", "Creator", "Description", "Premiere", "ProductType"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Products", columns, null, null, null, null, null);

        List<Product> products = new LinkedList<Product>();

        while (cursor.moveToNext()){
            Product product = new Product();
            product.Id = cursor.getInt(0);
            product.Name = cursor.getString(1);
            product.Creator = cursor.getString(2);
            product.Description = cursor.getString(3);
            product.Premiere = new Date(cursor.getLong(4));
            product.ProductType = cursor.getString(5);
            products.add(product);
        }

        cursor.close();
        db.close();

        return products;
    }

    public void RemoveProduct(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] arguments = {"" + id};
        db.delete("Products", "id=?", arguments);
        db.close();
    }
}