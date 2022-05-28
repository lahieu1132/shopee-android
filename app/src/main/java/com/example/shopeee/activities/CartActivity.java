package com.example.shopeee.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopeee.R;
import com.example.shopeee.adapter.MyCartAdapter;
import com.example.shopeee.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView allTotalAmount;
    RecyclerView recyclerView;
    List<MyCartModel> list;
    MyCartAdapter myCartAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    int clickCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();

        // lấy dữ liệu các sản phẩm đã bấm đươc lưu vào AddToCart trong firebase  hiển thị ra
        firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                            String documentId = documentSnapshot.getId();
                            MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);

                            myCartModel.setDocumentId(documentId);
                            list.add(myCartModel);
                        }
                        myCartAdapter.setData(list);
                    }
                });

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int totalBill = intent.getIntExtra("totalAmount", 0);
                allTotalAmount.setText(totalBill + "VNĐ");
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("My Cart Amount"));

    }

    public void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        allTotalAmount = findViewById(R.id.totalPrice);

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(this, list);
        recyclerView.setAdapter(myCartAdapter);

    }

    @Override
    public void onBackPressed() {
        clickCount++;
        if (clickCount > 1) {
            startActivity(new Intent(CartActivity.this, MainActivity.class));
        }
    }

}