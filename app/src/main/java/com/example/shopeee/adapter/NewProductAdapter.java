package com.example.shopeee.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopeee.R;
import com.example.shopeee.activities.DetailedActivity;
import com.example.shopeee.models.NewProductModel;

import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {
    Context context;
    List<NewProductModel> newProductModels;

    public NewProductAdapter(Context context, List<NewProductModel> newProductModels) {
        this.context = context;
        this.newProductModels = newProductModels;
    }

    public void setData(List<NewProductModel> list) {
        this.newProductModels = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newproduct_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(newProductModels.get(position).getImg_url()).into(holder.newProductImg);
        holder.newProductPrice.setText(String.valueOf(newProductModels.get(position).getPrice()));
        holder.newProductName.setText(newProductModels.get(position).getName());

        int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", newProductModels.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newProductModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newProductImg;
        TextView newProductPrice, newProductName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newProductImg = itemView.findViewById(R.id.newproduct_img);
            newProductName = itemView.findViewById(R.id.newproduct_name);
            newProductPrice = itemView.findViewById(R.id.newproduct_price);

        }
    }
}
