package com.cecom.alchol;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;

class MyOnCompleteListener<T> implements OnCompleteListener<T> {
    CardViewItemDTO[] returnCardViewItemDTO = new CardViewItemDTO[1];


    boolean isComplete = false;
    @Override
    public void onComplete(@NonNull Task<T> task) {
        Log.d("abc", "complete2");
        QuerySnapshot result = (QuerySnapshot)task.getResult();
        isComplete = true;
        if (task.isSuccessful()) {
            Log.d("abc", "complete3");
            returnCardViewItemDTO = new CardViewItemDTO[result.size()];
            int i = 0;
            for (QueryDocumentSnapshot document : result) {
                returnCardViewItemDTO[i] = new CardViewItemDTO(document.getId().toString(), document.getData().toString());
                Log.d("TAG", document.getId() + " => " + document.getData());
            }

        }
        else {
            Log.d("TAG", "Error getting documents: ", task.getException());
        }

    }
}
