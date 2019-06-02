package com.cecom.alchol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

public class RecipeResultActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    String sql;
    final static String dbName = "favoriteTable.db";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_result);

        RecyclerView resultRecycler = findViewById(R.id.result_recycler);

        //Test if String Value is transfered properly or not.
        Toast.makeText(this, getIntent().getStringExtra("Input"), Toast.LENGTH_LONG).show();


    }


//    ADD DATA TO FAVORITE DATABASE
//    dbHelper = new FavoritesActivity.DBHelper(this, dbName, null, dbVersion);
//    db = dbHelper.getWritableDatabase();
//    sql = String.format("INSERT INTO favoriteTable VALUES('" + "Menu1" + "');");
//    db.execSQL(sql);

    static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE favoriteTable (name TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS favoriteTable");
            onCreate(db);
        }

    }
}
