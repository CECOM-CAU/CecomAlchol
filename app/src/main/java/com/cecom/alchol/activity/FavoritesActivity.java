package com.cecom.alchol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import com.cecom.alchol.FavoriteRecyclerAdapter;
import com.cecom.alchol.R;
import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity
{
    ArrayList<String> list = new ArrayList<>();

    BroadcastReceiver mReceiver;
    DBHelper dbHelper;
    FavoriteRecyclerAdapter adapter;
    RecyclerView favoriteRecycler;
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

        favoriteRecycler = findViewById(R.id.favorite_recycler);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(this)) ;

        adapter = new FavoriteRecyclerAdapter(list) ;

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
            }
        } else {
            list.add("조회결과가 없습니다.");
        }
        cursor.close();
        favoriteRecycler.setAdapter(adapter);

        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction("UPDATE RECYCLER");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("UPDATE RECYCLER")){
                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    list.clear();
                    Cursor cursor = db.rawQuery(sql, null);
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            list.add(cursor.getString(0));
                        }
                    } else {
                        list.add("조회결과가 없습니다.");
                    }
                    cursor.close();
                    favoriteRecycler.setAdapter(adapter);
                }
            }
        };
        registerReceiver(mReceiver, theFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
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
