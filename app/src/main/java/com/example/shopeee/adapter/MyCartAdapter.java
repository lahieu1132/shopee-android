package com.example.shopeee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopeee.R;
import com.example.shopeee.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> list;
    // biến dùng để tính tổng tiền trong list để
    int totalAmount = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    public void setData(List<MyCartModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = position;
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());
        holder.totalPrice.setText(list.get(position).getTotalPrice() + "");
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getProductPrice() +"");
        holder.btnNoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User").document(list.get(pos).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    list.remove(pos);
                                    Toast.makeText(context, "Sản phẩm đã được loại bỏ", Toast.LENGTH_SHORT).show();
                                    setData(list);
                                } else {
                                    Toast.makeText(context, "Lỗi" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        totalAmount = totalAmount + list.get(position).getTotalPrice();

        Intent intent = new Intent("My Cart Amount");
        intent.putExtra("totalAmount", totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, totalPrice, totalQuantity;
        public Button btnNoBuy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                name = itemView.findViewById(R.id.product_name);
                price = itemView.findViewById(R.id.product_price);
                time = itemView.findViewById(R.id.current_time);
                date = itemView.findViewById(R.id.current_date);
                totalPrice = itemView.findViewById(R.id.total_price);
                totalQuantity = itemView.findViewById(R.id.total_quantity);
                btnNoBuy = itemView.findViewById(R.id.btnNoBuy);
        }
    }
}
