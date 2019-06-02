package com.cecom.alchol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<>();

    DBHelper dbHelper;
    SQLiteDatabase db;

    String sql;
    final static String dbName = "favoriteTable.db";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        dbHelper = new DBHelper(this, dbName, null, dbVersion);
        db = dbHelper.getReadableDatabase();
        sql = "SELECT * FROM favoriteTable;";

        RecyclerView favoriteRecycler = findViewById(R.id.favorite_recycler);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(this)) ;

        // Button for Adding Test Values to Database
        Button testButton = findViewById(R.id.favorite_btn_test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new FavoritesActivity.DBHelper(FavoritesActivity.this, dbName, null, dbVersion);
                db = dbHelper.getWritableDatabase();
                sql = "INSERT INTO favoriteTable VALUES('" + "Menu1" + "');";
                db.execSQL(sql);
                sql = "INSERT INTO favoriteTable VALUES('" + "Menu2" + "');";
                db.execSQL(sql);
                sql = "INSERT INTO favoriteTable VALUES('" + "Menu3" + "');";
                db.execSQL(sql);
                sql = "INSERT INTO favoriteTable VALUES('" + "Menu4" + "');";
                db.execSQL(sql);
                Toast.makeText(getApplicationContext(), "Run Activity Again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        FavoriteRecyclerAdapter adapter = new FavoriteRecyclerAdapter(list) ;
        favoriteRecycler.setAdapter(adapter) ;

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
            }
        } else {
            list.add("조회결과가 없습니다.");
        }
        cursor.close();
    }

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
