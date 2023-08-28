package com.example.project2.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project2.R;

public class AdminCategoryActivity extends AppCompatActivity {

    TextView  proc, videocard, matPlat, operPam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        proc = findViewById(R.id.proc);
        videocard = findViewById(R.id.videocard);
        matPlat = findViewById(R.id.matPlat);
        operPam = findViewById(R.id.OrepPam);

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "proc");
                startActivity(intent);
            }
        });
        videocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "videocard");
                startActivity(intent);
            }
        });
        matPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "matPlat");
                startActivity(intent);
            }
        });
        operPam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "operPam");
                startActivity(intent);
            }
        });
    }
}