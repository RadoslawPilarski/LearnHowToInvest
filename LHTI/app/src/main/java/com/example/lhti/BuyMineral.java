package com.example.lhti;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyMineral extends AppCompatActivity {

    TextView name,price,unit;

    EditText ilosc;

    double money;

    String userKey;

    FirebaseFirestore db;

    // DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lhti-625e5-default-rtdb.firebaseio.com/");

    DatabaseReference dr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lhti-625e5-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_mineral);

        db=FirebaseFirestore.getInstance();

        name=findViewById(R.id.name);
        price=findViewById(R.id.priceName);
        unit=findViewById(R.id.rodzaj);
        ilosc=findViewById(R.id.iloscValue);

        LineChart chart = (LineChart) findViewById(R.id.chart2);

        Wykres w=new Wykres(1,4);
        Wykres w2=new Wykres(2,1);
        Wykres w3=new Wykres(3,7);
        Wykres w4=new Wykres(4,9);
        Wykres w5=new Wykres(5,6);
        Wykres[] dataObjects={w,w2,w3,w4,w5};

        List<Entry> entries = new ArrayList<Entry>();
        for (Wykres data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getX(), data.getY()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label"); //

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh


        refresh();

    }
    private void refresh (){
        Intent intent = getIntent();
        String nameTxt = intent.getStringExtra("name");
        String priceTxt = intent.getStringExtra("price");
        String unitTxt = intent.getStringExtra("unit");
        name.setText(nameTxt);
        price.setText(priceTxt);
        unit.setText(unitTxt);


        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String unm=sp1.getString("Unm", null);
        getUserMoney(unm);

        db.collection("prizes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                if(nameTxt.equals(dc.getDocument().toObject(Item.class).getName())){
                                    price.setText(Double.toString(dc.getDocument().toObject(Item.class).getPrice()));
                                }
                            }
                        }
                    }
                });
    }

    public void buyValue(View view) {

        double value;
        String text = ilosc.getText().toString();
        if (!text.isEmpty())
            try {
                value = Double.parseDouble(text);

        SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);

        String unm = sp1.getString("Unm", null);
        String nameV = name.getText().toString();
        double priceV = Double.valueOf(price.getText().toString())*value;

        HashMap<String, String> symbols = new HashMap<String, String>();
        symbols.put("ALUMINIUM", "ALU");
        symbols.put("ZŁOTO", "XAU");
        symbols.put("MIEDŹ", "XCU");
        symbols.put("KOBALT", "LCO");
        symbols.put("NIKIEL", "NI");
        symbols.put("SREBRO", "XAG");
        symbols.put("PALLAD", "XPD");


        String symbol = symbols.get(nameV);

        Map<String, Object> city = new HashMap<>();
        city.put("nazwa", nameV);
        city.put("login", unm);
        city.put("ilosc", value);
        city.put("wklat", priceV);
        city.put("zysk", 0);
        city.put("symbol", symbol);

        LocalDateTime when = LocalDateTime.now();

        city.put("data", String.valueOf(when));

        getUserMoney(unm);
        if (money > priceV) {
            Toast.makeText(BuyMineral.this, "Udało się kupić :)", Toast.LENGTH_SHORT).show();
            db.collection("akcje")
                    .add(city)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            setUserMoney(unm, priceV);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        } else {
            Toast.makeText(BuyMineral.this, "Brak środków", Toast.LENGTH_SHORT).show();
        }
    }
    catch (Exception e1) {
        // this means it is not double

        Toast.makeText(this, "Podaj prawidłową liczbę", Toast.LENGTH_SHORT).show();
    }



    }
    private void getUserMoney(String unm) {
        dr.child("users").child(unm).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {{
                Post user=snapshot.getValue(Post.class);
               money=user.getMoney();
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setUserMoney(String unm,double priceV){
        dr.child("users").child(unm).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {{
                            Post user=snapshot.getValue(Post.class);
                            double finale=user.getMoney()-priceV;
                            dr.child("users").child(unm).child("money").setValue(finale);
                        }
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getUniqueKey(String unm) {
        dr.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String key = snapshot.child(unm).child("key").getValue(String.class);
                    userKey = unm + String.valueOf(key);
                    dr.child("users").child(unm).child("key").setValue("Chuj");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });
    }
}