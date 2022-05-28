package com.example.shopeee.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopeee.R;
import com.example.shopeee.activities.MainActivity;
import com.example.shopeee.adapter.ShowAllAdapter;
import com.example.shopeee.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {


    public SearchFragment() {

    }
    private EditText edtSearch;
    private RecyclerView recyclerView;
    private ShowAllAdapter adapter;
    private List<ShowAllModel> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("ShowAll").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                                list.add(showAllModel);
                            }
                        }
                    }
                });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        recyclerView = view.findViewById(R.id.listSearch);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new ShowAllAdapter(getContext(),new ArrayList<>());
        recyclerView.setAdapter(adapter);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProduct(edtSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
    private void searchProduct(String key){
        List<ShowAllModel> listResult;
        listResult = new ArrayList<>();
        for (ShowAllModel model: list){
            if (model.getName().toLowerCase().contains(key.toLowerCase())){
                listResult.add(model);
            }
        }
        adapter.setData(listResult);

    }
}