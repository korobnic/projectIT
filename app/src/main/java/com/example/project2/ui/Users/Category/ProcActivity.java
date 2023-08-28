package com.example.project2.ui.Users.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2.Model.Products;
import com.example.project2.R;
import com.example.project2.ViewHolders.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.project2.Model.Products;
import java.util.ArrayList;

import okhttp3.Request;

public class ProcActivity extends AppCompatActivity {
    Button backToCategoryBtn;
    public RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<Products> list;
    MyAdapter myAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc);
        backToCategoryBtn = findViewById(R.id.backToCategoryFromProc);
        backToCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToCategoryIntent = new Intent(ProcActivity.this, ChooseCategoryActivity.class);
                startActivity(backToCategoryIntent);
            }
        });

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Products");

        recyclerView = findViewById(R.id.recycle_menu_proc);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products product = dataSnapshot.getValue(Products.class);
                    list.add(product);
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
