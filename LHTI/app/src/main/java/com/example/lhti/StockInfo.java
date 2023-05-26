package com.example.lhti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StockInfo extends AppCompatActivity {

    FirebaseFirestore db;

    TextView nameStock,valueStock;
    Button buttonEnd;

    String id;
    Stock st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        nameStock = findViewById(R.id.nameStock);
        valueStock = findViewById(R.id.valueStock);
        buttonEnd = findViewById(R.id.buttonEnd);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);


        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        db.collection("akcje")
                .whereEqualTo("ilosc", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            st = document.toObject(Stock.class);
                        }
                    }
                });

        nameStock.setText(st.getNazwa());
        valueStock.setText(st.getSymbol());
    }
}