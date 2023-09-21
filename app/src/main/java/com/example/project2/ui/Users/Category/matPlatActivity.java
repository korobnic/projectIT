package com.example.project2.ui.Users.Category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.Model.Products;
import com.example.project2.R;
import com.example.project2.ViewHolders.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class matPlatActivity extends AppCompatActivity {

    Button backToCategoryBtn, sortByRating, sortByPrice;
    public RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<Products> list;
    MyAdapter myAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc);
        backToCategoryBtn = findViewById(R.id.backToCategoryFromProc);


        backToCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToCategoryIntent = new Intent(matPlatActivity.this, ChooseCategoryActivity.class);
                startActivity(backToCategoryIntent);
            }
        });


        recyclerView = findViewById(R.id.recycle_menu_proc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("category")
                .equalTo("matPlat");
        query.addListenerForSingleValueEvent(valueEventListener);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
            if (snapshot.exists()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Products product = dataSnapshot.getValue(Products.class);
                    list.add(product);
                }
                myAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}