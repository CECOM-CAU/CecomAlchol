package com.cecom.alchol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class UserInputActivity extends AppCompatActivity {

    String sourceInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        Button btnResult = findViewById(R.id.input_btn_search);
        CheckBox chkSource1 = findViewById(R.id.input_check_source1);
        CheckBox chkSource2 = findViewById(R.id.input_check_source2);
        CheckBox chkSource3 = findViewById(R.id.input_check_source3);

        chkSource1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(sourceInput.equals("")){
                        sourceInput += "Source1";
                    }else{
                        sourceInput += ",Source1";
                    }
                }else{
                    sourceInput = sourceInput.replace("Source1,", "").replace("Source1", "");
                }
            }
        });
        chkSource2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(sourceInput.equals("")){
                        sourceInput += "Source2";
                    }else{
                        sourceInput += ",Source2";
                    }
                }else{
                    sourceInput = sourceInput.replace("Source2,", "").replace("Source2", "");
                }
            }
        });
        chkSource3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(sourceInput.equals("")){
                        sourceInput += "Source3";
                    }else{
                        sourceInput += ",Source3";
                    }
                }else{
                    sourceInput = sourceInput.replace("Source3,", "").replace("Source3", "");
                }
            }
        });

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
}
