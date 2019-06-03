package com.cecom.alchol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity
{
    String[] drinkElements = {"horse", "cow", "camel", "sheep", "goat"};
    boolean[] checkedItems = new boolean[drinkElements.length];
    Button selectElementsButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        selectElementsButton = findViewById(R.id.selectElementsBT);
        selectElementsButton.setOnClickListener(listener);
        for (int i = 0; i < checkedItems.length; i++){
            checkedItems[i] = false;
        }

        context = this;
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
        db.collection("Drink").document("drink4").set(user);


    }

    Button.OnClickListener listener = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.selectElementsBT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Choose an animal");
                    builder.setMultiChoiceItems(drinkElements, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            // user checked or unchecked a box
                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user clicked OK
                        }
                    });
                    builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
            }

        }
    };

}

