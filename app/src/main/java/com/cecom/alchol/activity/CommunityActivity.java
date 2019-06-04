package com.cecom.alchol.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cecom.alchol.CardRecyclerViewAdapter;
import com.cecom.alchol.R;
import com.cecom.alchol.model.CardViewItemDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        final RecyclerView recyclerView= (RecyclerView)findViewById(R.id.community_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardRecyclerViewAdapter(initialCardViewItem()));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Drink")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            CardViewItemDTO[] test = new CardViewItemDTO[task.getResult().size()];
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String data = document.getData().toString().replace(" ","");
                                String[] tempString = data.substring(1,data.length()-1).split(",");
                                String tempData = "";
                                for(int j = 0; j < tempString.length; j++){
                                    tempData += (tempString[j]+"\n");
                                }
                                test[i++] = new CardViewItemDTO(document.getId().toString(), tempData);
                                Log.d("ABC", document.getId());
                            }
                            CardRecyclerViewAdapter temp = (CardRecyclerViewAdapter) recyclerView.getAdapter();
                            temp.setData(test);
                            temp.notifyDataSetChanged();
                        }
                    }
                });


        Button btnAdd = findViewById(R.id.community_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityActivity.this, UserRegisterActivity.class));
            }
        });

    }

    CardViewItemDTO[] initialCardViewItem(){
        CardViewItemDTO[] returnCardViewItemDTO = new CardViewItemDTO[1];

        returnCardViewItemDTO[0] = new CardViewItemDTO("temp", "temp");



        return returnCardViewItemDTO;

    }


}

