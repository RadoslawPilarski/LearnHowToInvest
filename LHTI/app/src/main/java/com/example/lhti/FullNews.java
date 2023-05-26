package com.example.lhti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FullNews extends AppCompatActivity {

    TextView title,contents;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        title=findViewById(R.id.title);
        contents=findViewById(R.id.contents);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String nameTxt = intent.getStringExtra("title");
        String priceTxt = intent.getStringExtra("contents");
        title.setText(nameTxt);
       contents.setText(priceTxt);
        contents.setMovementMethod(new ScrollingMovementMethod());
    }
}