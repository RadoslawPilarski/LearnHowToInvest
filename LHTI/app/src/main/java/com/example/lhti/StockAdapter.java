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

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {

    Context context;
    ArrayList<Stock> stockArrayList;

    public StockAdapter(Context context, ArrayList<Stock> stockArrayList) {
        this.context = context;
        this.stockArrayList = stockArrayList;
    }

    @NonNull
    @Override
    public StockAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.stock,parent,false);

        return new MyViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull StockAdapter.MyViewHolder holder, int position) {

        Stock stock = stockArrayList.get(position);

        holder.nazwa.setText(stock.nazwa);
        holder.zysk.setText(Double.toString(stock.zysk));
        holder.ilosc.setText(Double.toString(stock.ilosc));

    }

    @Override
    public int getItemCount() {
        return stockArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nazwa,ilosc,zysk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            zysk=itemView.findViewById(R.id.zysk);
            nazwa=itemView.findViewById(R.id.nazwa);
            ilosc=itemView.findViewById(R.id.ilosc);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            //int position=getAdapterPosition();
            //Intent intent = new Intent(context,StockInfo.class);
            //intent.putExtra("data",stockArrayList.get(position).getData());
            // intent.putExtra("price",Double.toString(stockArrayList.get(position).getPrice()));
            //context.startActivity(intent);
            Toast.makeText(context, "Narazie nic nie robię :C", Toast.LENGTH_SHORT).show();
        }
    }
}

