package com.cecom.alchol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipeResultActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_result);

        Toast.makeText(this, getIntent().getStringExtra("Input"), Toast.LENGTH_SHORT).show();

        final String[] selectedData = getIntent().getStringExtra("Input").split(",");
        boolean checked[] = {true,true,true};
        ArrayList<String> tempList = new ArrayList<>();
        final String[] unSelectedData;

        for(String temp : selectedData){
            if(temp.equals("Source1")){
                checked[0] = false;
            }
            else if(temp.equals("Source2")){
                checked[1] = false;
            }
            else if(temp.equals("Source3")){
                checked[2] = false;
            }
        }

        for(int i = 0; i < checked.length; i++){
            if(checked[i] == true){
                tempList.add("Source"+String.valueOf(i+1));
            }
        }

        unSelectedData = tempList.toArray(new String[tempList.size()]);
        if(unSelectedData.length > 0){
            Log.d("AVC", unSelectedData[0]);
        }

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
                                boolean count = false;
                                ResultData data = new ResultData();
                                data.setMenu(document.getId());
                                data.setSource(document.getData().get("Source").toString());


                                String[] tempArr = data.getSource().split(",");
                                for(String temp:tempArr){
                                    for(int i = 0; i < selectedData.length; i++){
                                        if(selectedData[i].equals(temp)){
                                            adapter.addItem(data);
                                            count = true;
                                        }
                                        if(count == true){
                                            break;
                                        }
                                    }
                                    if(count == true){
                                        break;
                                    }
                                }

                            }

                            ArrayList<ResultData> tempList = adapter.getList();

                            for(int i = 0; i < tempList.size(); i++){
                                for(int j = 0; j < unSelectedData.length; j++){
                                    if(tempList.get(i).getSource().contains(unSelectedData[j])){
                                        Log.d("ABC"+String.valueOf(i), unSelectedData[j]);
                                        tempList.remove(i);
                                        i--;
                                        break;
                                    }
                                }
                            }
                            adapter.setList(tempList);
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getApplicationContext(), "Error getting documents : " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
