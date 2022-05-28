package com.example.shopeee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopeee.R;
import com.example.shopeee.activities.ShowAllActivity;
import com.example.shopeee.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> categoryModelList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    public void setData(List<CategoryModel> list) {
        this.categoryModelList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categoryModelList.get(position).getImg_url()).into(holder.catImg);
        holder.name.setText(categoryModelList.get(position).getName());
        int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowAllActivity.class);
                intent.putExtra("type", categoryModelList.get(pos).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImg = itemView.findViewById(R.id.cat_img);
            name = itemView.findViewById(R.id.cat_name);

        }
    }
}
