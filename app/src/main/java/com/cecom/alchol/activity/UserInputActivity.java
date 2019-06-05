package com.cecom.alchol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cecom.alchol.R;
import com.cecom.alchol.model.DrinkList;

public class UserInputActivity extends AppCompatActivity {

    String sourceInput = "";
    CheckBox[] checkBoxes;
    Button btnResult;
    LinearLayout temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_input);
        btnResult = findViewById(R.id.input_btn_search);
        temp = findViewById(R.id.checkBoxLayout);
        checkBoxes = new CheckBox[DrinkList.data.length];
        for(int i = 0; i < checkBoxes.length; i++){
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(DrinkList.data[i]);
            checkBoxes[i].setOnCheckedChangeListener(listener);
            temp.addView(checkBoxes[i]);
        }

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sourceInput.equals("")){
                    Intent intent = new Intent(UserInputActivity.this, RecipeResultActivity.class);
                    intent.putExtra("Input", sourceInput);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주세요", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                if(sourceInput.equals("")){
                    sourceInput += (buttonView.getText().toString().replace(" ",""));
                }
                else{
                    sourceInput += ("," + buttonView.getText().toString().replace(" ",""));
                }
            }
            else{
                String temp = buttonView.getText().toString().replace(" ","");
                sourceInput = sourceInput.replace(","+temp, "").replace(temp, "");
            }
        }
    };
}
