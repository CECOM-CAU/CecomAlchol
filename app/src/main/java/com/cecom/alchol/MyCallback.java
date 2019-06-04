package com.cecom.alchol;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

class MyThread extends Thread {
    MyOnCompleteListener<QuerySnapshot> completeListener = new MyOnCompleteListener<QuerySnapshot>();
    FirebaseFirestore db;
    public MyThread(FirebaseFirestore db) {
        this.db = db;
    }
    @Override
    public void run() {
        super.run();
        db.collection("Drink")
                .get()
                .addOnCompleteListener(completeListener);


    }

}
