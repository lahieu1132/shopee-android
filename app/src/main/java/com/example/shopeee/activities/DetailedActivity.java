package com.example.shopeee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.shopeee.R;
import com.example.shopeee.models.NewProductModel;
import com.example.shopeee.models.PopularProductModel;
import com.example.shopeee.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg, addItems, removeItems;
    TextView description, price, quantity;
    Button addToCart, buyNow;
    TextView name;
    Toolbar detailedToolbar;

    private FirebaseFirestore firebaseFirestore = null;
    NewProductModel newProductModel;
    ShowAllModel showAllModel;
    PopularProductModel popularProductModel;
    private FirebaseAuth firebaseAuth;

    int totalQuantity = 1;
    int totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        init();
        // get 'detailed' from ShowAllActivity
        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductModel) {
            newProductModel = (NewProductModel) obj;
        } else if(obj instanceof PopularProductModel) {
            popularProductModel = (PopularProductModel) obj;
        } else if(obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        if (newProductModel != null) {
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detailedImg);
            name.setText(newProductModel.getName());
            description.setText(newProductModel.getDescription());
            price.setText(newProductModel.getPrice() + "");
            totalPrice = newProductModel.getPrice() * totalQuantity;
        }

        if (popularProductModel != null) {
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailedImg);
            name.setText(popularProductModel.getName());
            description.setText(popularProductModel.getDescription());
            price.setText(popularProductModel.getPrice() + "");
            totalPrice = newProductModel.getPrice() * totalQuantity;
        }

        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            description.setText(showAllModel.getDescription());
            price.setText(showAllModel.getPrice() + "");
            totalPrice = newProductModel.getPrice() * totalQuantity;
        }

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(totalQuantity + "");

                    if(newProductModel != null) {
                        totalPrice = totalQuantity * newProductModel.getPrice();
                    }

                    if(popularProductModel != null) {
                        totalPrice = totalQuantity * popularProductModel.getPrice();
                    }

                    if(showAllModel != null) {
                        totalPrice = totalQuantity * showAllModel.getPrice();
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(totalQuantity + "");
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailedActivity.this, AddAdressActivity.class));
            }
        });
    }

    public void init() {
        detailedToolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(detailedToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailedToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        buyNow = findViewById(R.id.buy_now);
        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        addItems = findViewById(R.id.add_item);
        buyNow = findViewById(R.id.buy_now);
        removeItems = findViewById(R.id.remove_item);
        addToCart = findViewById(R.id.add_to_cart);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.price_item);
        name = findViewById(R.id.detailed_name);
    }

    private void addToCart() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendarForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calendarForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarForDate.getTime());


        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firebaseFirestore.collection("AddToCart")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("User")
                .add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Add to a Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}