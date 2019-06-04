package com.cecom.alchol;

import android.content.Intent;
import android.os.Bundle;

import com.cecom.alchol.activity.CommunityActivity;
import com.cecom.alchol.activity.FavoritesActivity;
import com.cecom.alchol.activity.UserInputActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSearch = findViewById(R.id.main_btn_search);
        Button btnFavorite = findViewById(R.id.main_btn_favorite);
        Button btnCommunity = findViewById(R.id.main_btn_community);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserInputActivity.class));
            }
        });
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
            }
        });
        btnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommunityActivity.class));
            }
        });
    }
}
