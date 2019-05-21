package com.cecom.alchol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class RecipeResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_result);

        RecyclerView resultRecycler = findViewById(R.id.result_recycler);

        //Test if String Value is transfered properly or not.
        Toast.makeText(this, getIntent().getStringExtra("Input"), Toast.LENGTH_LONG).show();
    }
}
