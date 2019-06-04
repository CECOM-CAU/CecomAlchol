package com.cecom.alchol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity
{
    String[] drinkElements = DrinkList.data;
    boolean[] checkedItems = new boolean[drinkElements.length];
    boolean[] lastCheckedItems;
    Button selectElementsButton;
    Button submitButton;
    TextView selectedElements;
    EditText nameET;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        selectElementsButton = findViewById(R.id.selectElementsBT);
        selectedElements = findViewById(R.id.selectedElementsTV);
        submitButton = findViewById(R.id.submitBT);
        nameET = findViewById(R.id.drinkNameET);
        selectElementsButton.setOnClickListener(listener);
        submitButton.setOnClickListener(listener);
        resetArray(checkedItems);
        context = this;
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
                            String tempString = "";
                            lastCheckedItems = Arrays.copyOf(checkedItems,checkedItems.length);
                            resetArray(checkedItems);
                            for(int i =0; i< lastCheckedItems.length; i++){
                                if(lastCheckedItems[i] == true){
                                    tempString += (String.valueOf(drinkElements[i])+"\n");
                                }
                            }
                            selectedElements.setText(tempString);
                            Log.d("test", String.valueOf(lastCheckedItems[0])+String.valueOf(lastCheckedItems[1]));
                        }
                    });
                    builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.submitBT:
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String[] tempArr = selectedElements.getText().toString().split("\n");
                    Map<String, Object> user = new HashMap<>();
                    String temp = "";
                    for(int i = 0; i < tempArr.length; i++){
                        temp += (tempArr[i]+",");
                    }
                    user.put("Source", temp);
                    db.collection("Drink").document(nameET.getText().toString()).set(user);
                    break;
            }

        }
    };

    void resetArray(boolean[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = false;
        }
    }

}




