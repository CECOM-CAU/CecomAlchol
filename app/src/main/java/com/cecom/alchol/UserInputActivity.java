package com.cecom.alchol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        Button btnResult = findViewById(R.id.input_btn_search);
        final EditText edtInput = findViewById(R.id.input_edittext);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strInput = edtInput.getText().toString();
                if(!strInput.equals("")){
                    Intent intent = new Intent(UserInputActivity.this, RecipeResultActivity.class);
                    intent.putExtra("Input", strInput);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주세요", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
