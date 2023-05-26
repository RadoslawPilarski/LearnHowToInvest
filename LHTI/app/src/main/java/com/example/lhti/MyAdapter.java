package com.example.lhti;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> itemArrayList;

    public MyAdapter(Context context, ArrayList<Item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.value,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Item item = itemArrayList.get(position);

        holder.name.setText(item.name);
        holder.price.setText(Double.toString(item.price));
        holder.unit.setText(item.unit);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,price,unit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            unit=itemView.findViewById(R.id.unit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent = new Intent(context,BuyMineral.class);
            intent.putExtra("name",itemArrayList.get(position).getName());
            intent.putExtra("price",Double.toString(itemArrayList.get(position).getPrice()));
            intent.putExtra("unit",itemArrayList.get(position).getUnit());
            context.startActivity(intent);
        }
    }
}
