package com.example.lhti;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyStocks extends AppCompatActivity {

    RecyclerView stocks;
    ArrayList<Stock> stockArrayList;
    StockAdapter stockAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    Button courses,news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stocks);

        courses=findViewById(R.id.courses);
        news=findViewById(R.id.news);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

        stocks=findViewById(R.id.stocks);
        stocks.setHasFixedSize(true);
        stocks.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        stockArrayList = new ArrayList<Stock>();
        stockArrayList.clear();
        stockAdapter = new StockAdapter(MyStocks.this,stockArrayList);

        stocks.setAdapter(stockAdapter);


        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String unm=sp1.getString("Unm", null);

        EventChangeListener(unm);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyStocks.this,News.class);
                startActivity(intent);
            }
        });
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyStocks.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void EventChangeListener(String unm) {

        db.collection("akcje")
                .whereEqualTo("login",unm)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error !=null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        Log.d("StockArrayList before", String.valueOf(stockArrayList.size()));
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED ) {
                                stockArrayList.add(dc.getDocument().toObject(Stock.class));
                            }
                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                    stockArrayList.set(dc.getOldIndex(), dc.getDocument().toObject(Stock.class));
                                    stockAdapter.notifyItemChanged(dc.getOldIndex());

                            }
                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                    stockArrayList.remove(dc.getOldIndex());
                                    stockAdapter.notifyItemRemoved(dc.getOldIndex());
                            }
                        }


                        stockAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                    }
                });
    }
}