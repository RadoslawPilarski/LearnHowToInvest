package com.example.lhti;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Message> newsArrayList;

    public NewsAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.newsArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.news,parent,false);

        return new MyViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {

        Message message = newsArrayList.get(position);

        holder.title.setText(message.title);
        Date date = message.time;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM");
        String strDate = dateFormat.format(date);
        holder.date.setText(strDate);

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent = new Intent(context,FullNews.class);
            intent.putExtra("title",newsArrayList.get(position).getTitle());
            intent.putExtra("contents",newsArrayList.get(position).getContents());
            context.startActivity(intent);
        }
    }
}

