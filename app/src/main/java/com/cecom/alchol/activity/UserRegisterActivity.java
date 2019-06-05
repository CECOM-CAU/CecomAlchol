package com.cecom.alchol.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cecom.alchol.R;
import com.cecom.alchol.SelectDialog;
import com.cecom.alchol.SelectDialogListener;
import com.cecom.alchol.model.DrinkList;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity
{
    String[] drinkElements = DrinkList.data;
    boolean[] checkedItems = new boolean[drinkElements.length];
    boolean[] lastCheckedItems;
    int[] ratio;
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
                    SelectDialog dlg = new SelectDialog(context);
                    dlg.setListener(new SelectDialogListener() {
                        @Override
                        public void onPositiveClicked(boolean[] selectedElements_r, int[] ratio_r) {
                            ratio = ratio_r;
                            checkedItems = selectedElements_r;
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

                        @Override
                        public void onNegativeClicked() {
                            return;
                        }
                    });
                    dlg.showDialog();
                    break;

                case R.id.submitBT:
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String[] tempArr = selectedElements.getText().toString().split("\n");
                    Map<String, Object> user = new HashMap<>();
                    String tempSource = "";
                    for(int i = 0; i < tempArr.length; i++){
                        tempSource += (tempArr[i]+",");
                    }
                    String tempRatio = "";
                    for(int j = 0; j < ratio.length; j++){
                        if(ratio[j] != 0){
                            tempRatio += (String.valueOf(ratio[j])+",");
                        }

                    }
                    user.put("Source", tempSource);
                    user.put("Ratio", tempRatio);

                    db.collection("Drink").document(nameET.getText().toString()).set(user);
                    Toast.makeText(context, "성공적으로 업로드됐습니다.", Toast.LENGTH_SHORT).show();
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