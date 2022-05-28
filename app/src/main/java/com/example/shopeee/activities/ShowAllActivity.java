package com.example.shopeee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopeee.R;
import com.example.shopeee.adapter.ShowAllAdapter;
import com.example.shopeee.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ShowAllModel> list;
    ShowAllAdapter showAllAdapter;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all2);
        // anh xa
        init();
        // get product type of category in MainActivity
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (TextUtils.isEmpty(type)){
            firestore.collection("ShowAll").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                                    list.add(showAllModel);

                                }
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("Woman")) {
            firestore.collection("ShowAll").whereEqualTo("type", "woman").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                            list.add(showAllModel);

                        }
                        showAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type != null && type.equalsIgnoreCase("watch")) {
            firestore.collection("ShowAll").whereEqualTo("type", "watch").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                                    list.add(showAllModel);

                                }
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("camera")) {
            firestore.collection("ShowAll").whereEqualTo("type", "camera").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                                    list.add(showAllModel);

                                }
                                showAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }

    public void init() {
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        list = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, list);
        recyclerView.setAdapter(showAllAdapter);

        toolbar = findViewById(R.id.tbarShowAll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}