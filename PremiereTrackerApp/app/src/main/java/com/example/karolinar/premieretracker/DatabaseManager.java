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

    public void AddProduct(ProductEntity product) {
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

    public List<ProductEntity> GetProducts(){
        String[] columns = {"Id", "Name", "Creator", "Description", "Premiere", "ProductType"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Products", columns, null, null, null, null, null);

        List<ProductEntity> products = new LinkedList<ProductEntity>();

        while (cursor.moveToNext()){
            ProductEntity product = new ProductEntity();
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

    public List<ProductEntity> GetProducts(String producType){
        String[] columns = {"Id", "Name", "Creator", "Description", "Premiere", "ProductType"};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("Products", columns, null, null, null, null, null);
        List<ProductEntity> products = new LinkedList<ProductEntity>();

        while (cursor.moveToNext()){
            if(cursor.getString(5).toLowerCase().equals(producType.toLowerCase())) {
                ProductEntity product = new ProductEntity();
                product.Id = cursor.getInt(0);
                product.Name = cursor.getString(1);
                product.Creator = cursor.getString(2);
                product.Description = cursor.getString(3);
                product.Premiere = new Date(cursor.getLong(4));
                product.ProductType = cursor.getString(5);
                products.add(product);
            }
        }

        cursor.close();
        db.close();

        return products;
    }

    public List<String> GetProductTypes(){
        String[] columns = { "Name" };
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("ProductTypes", columns, null, null, null, null, null);

        List<String> types = new LinkedList<String>();

        while (cursor.moveToNext()){
            String type = cursor.getString(0);
            types.add(type);
        }

        cursor.close();
        db.close();

        return types;
    }

    public boolean existsProduct(ProductEntity p){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"Id", "Name", "Creator", "Description", "Premiere", "ProductType"};
        Cursor cursor = db.query("Products", columns, null, null, null, null, null);
        List<ProductEntity> products = new LinkedList<ProductEntity>();

        while (cursor.moveToNext()){
            if(cursor.getString(1).toLowerCase().equals(p.Name.toLowerCase())
                    && cursor.getString(2).toLowerCase().equals(p.Creator.toLowerCase())
                    && cursor.getString(3).toLowerCase().equals(p.Description.toLowerCase())
                    && cursor.getString(5).toLowerCase().equals(p.ProductType.toLowerCase())) {
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }

    public ProductEntity getProductById(String id){

        String[] columns = {"Id", "Name", "Creator", "Description", "Premiere", "ProductType"};
        SQLiteDatabase db = getReadableDatabase();
     //   Cursor cursor = db.rawQuery("Select * from Products WHERE id == "+id, columns);
        db.query("Products", columns, null, null, null, null, null);

       // Cursor  cursor = db.rawQuery("Select * from Products WHERE id = "+id,null);
        Cursor cursor = db.query("Products",columns,"id = "+id, null,null,null ,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ProductEntity productEntity = new ProductEntity();
        Integer a = cursor.getColumnCount();
        productEntity.Id = cursor.getInt(0);
        productEntity.Name = cursor.getString(1);
        productEntity.Creator = cursor.getString(2);
        productEntity.Description = cursor.getString(3);
        productEntity.Premiere = new Date(cursor.getLong(4));
        productEntity.ProductType = cursor.getString(5);


        cursor.close();
        db.close();

        return productEntity;
    }

    public void RemoveProduct(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] arguments = {"" + id};
        db.delete("Products", "id=?", arguments);
        db.close();
    }
}
