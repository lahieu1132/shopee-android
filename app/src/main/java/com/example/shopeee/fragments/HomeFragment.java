package com.example.shopeee.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shopeee.R;
import com.example.shopeee.activities.ShowAllActivity;
import com.example.shopeee.adapter.CategoryAdapter;
import com.example.shopeee.adapter.NewProductAdapter;
import com.example.shopeee.adapter.PopularProductAdapter;
import com.example.shopeee.models.CategoryModel;
import com.example.shopeee.models.NewProductModel;
import com.example.shopeee.models.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView catShowAll, popularShowAll, newProductShowAll;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    // category
    RecyclerView recyclerView;
    List<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;
    // new product
    RecyclerView newProductRecyclerView;
    List<NewProductModel> newProductModels;
    NewProductAdapter newProductAdapter;
    // popular product
    RecyclerView popularRecyclerView;
    List<PopularProductModel> popularProductModels;
    PopularProductAdapter popularProductAdapter;
    LinearLayout linearLayout;
    // banner
    ArrayList<SlideModel> slideModels;
    ImageSlider imageSlider;

    public HomeFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        showProgressDialog();

        showSlideBanner();

        showCategory();

        showNewProducts();

        showPopularProducts();

        showAllProduct();

        return view;
    }

    public void init(View view) {
        imageSlider = view.findViewById(R.id.image_slider);
        slideModels = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rec_category);
        popularRecyclerView = view.findViewById(R.id.popular_rec);
        newProductRecyclerView = view.findViewById(R.id.new_product_rec);

        linearLayout = view.findViewById(R.id.home_layout);
        catShowAll = view.findViewById(R.id.category_see_all);
        newProductShowAll = view.findViewById(R.id.newProducts_see_all);
        popularShowAll = view.findViewById(R.id.popular_see_all);

        linearLayout.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();
    }

    public void showAllProduct() {
        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);

            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Chào mừng đến với Shopeee");
        progressDialog.setMessage("Làm ơn chờ một chút");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void showSlideBanner() {
        slideModels.add(new SlideModel(R.drawable.banner1, "Discount on shoes items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Discount on perfume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70% off", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
    }

    public void showCategory() {
        //Category
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModels);
        recyclerView.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModels.add(categoryModel);

                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();

                            }
                            categoryAdapter.setData(categoryModels);
                        }
                    }
                });
    }

    public void showNewProducts() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        newProductRecyclerView.setLayoutManager(linearLayoutManager);
        newProductModels = new ArrayList<>();
        newProductAdapter = new NewProductAdapter(getContext(), newProductModels);
        newProductRecyclerView.setAdapter(newProductAdapter);
        //showNewProducts();

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductModel newProductModel = document.toObject(NewProductModel.class);
                                newProductModels.add(newProductModel);
                                Log.e("product", newProductModel.getName());
                            }
                            newProductAdapter.setData(newProductModels);
                        } else {

                        }
                    }
                });
    }

    public void showPopularProducts() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        popularRecyclerView.setLayoutManager(gridLayoutManager);
        popularProductModels = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(getContext(), popularProductModels);
        popularRecyclerView.setAdapter(popularProductAdapter);

        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                                popularProductModels.add(popularProductModel);
                                popularProductAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}