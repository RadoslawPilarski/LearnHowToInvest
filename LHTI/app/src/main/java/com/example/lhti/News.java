package com.example.lhti;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lhti.databinding.ActivityNewsBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class News extends AppCompatActivity {

    Button profileButton;

    RecyclerView news;
    ArrayList<Message> newsArrayList;
    NewsAdapter newsAdapter;
    FirebaseFirestore db;

    Button myStocks,courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        myStocks=findViewById(R.id.myStocks);
        courses=findViewById(R.id.courses);

        news=findViewById(R.id.myNews);
        news.setHasFixedSize(true);
        news.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        newsArrayList = new ArrayList<Message>();
        newsArrayList.clear();
        newsAdapter = new NewsAdapter(News.this,newsArrayList);

        profileButton=findViewById(R.id.profileButton);

        news.setAdapter(newsAdapter);

        EventChangeListener();

        myStocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(News.this,MyStocks.class);
                startActivity(intent);
            }
        });
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(News.this,MainActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(News.this,v);
                popupMenu.getMenuInflater().inflate(R.menu.profilemenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.accountreset:
                                Intent accountreset=new Intent(News.this,resetPopUp.class);
                                startActivity(accountreset);
                                return true;
                            case R.id.passchange:
                                //reset();
                                return true;
                            case R.id.namechange:
                                //reset();
                                return true;
                            case R.id.statiscis:
                                //reset();
                                return true;
                            case R.id.logout:
                                logout();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void logout(){
        // do naprawy
        Intent logout=new Intent(News.this,Login.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logout);
        finish();
    }
    private void EventChangeListener() {
        db.collection("news")
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        Log.d("StockArrayList before", String.valueOf(newsArrayList.size()));
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED ) {
                                newsArrayList.add(dc.getDocument().toObject(Message.class));
                            }
                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                newsArrayList.set(dc.getOldIndex(), dc.getDocument().toObject(Message.class));
                                newsAdapter.notifyItemChanged(dc.getOldIndex());

                            }
                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                newsArrayList.remove(dc.getOldIndex());
                                newsAdapter.notifyItemRemoved(dc.getOldIndex());
                            }
                        }
                        newsAdapter.notifyDataSetChanged();
                    }
                });
    }
}