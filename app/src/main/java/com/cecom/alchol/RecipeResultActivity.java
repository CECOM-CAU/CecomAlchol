package com.cecom.alchol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipeResultActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    private ArrayList<ResultData> mData = new ArrayList<>();
    String sql;
    final static String dbName = "favoriteTable.db";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_result);

        Toast.makeText(this, getIntent().getStringExtra("Input"), Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = findViewById(R.id.result_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ResultRecyclerAdapter adapter = new ResultRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Drink")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ResultData data = new ResultData();
                                data.setMenu(document.getId());
                                data.setSource(document.getData().toString());
                                adapter.addItem(data);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error getting documents : " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    ADD DATA TO FAVORITE DATABASE
//    dbHelper = new FavoritesActivity.DBHelper(this, dbName, null, dbVersion);
//    db = dbHelper.getWritableDatabase();
//    sql = "INSERT INTO favoriteTable VALUES('" + "Menu1" + "');";
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
