package com.example.project2.ui.Users.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.ui.Users.HomeActivity;

public class ChooseCategoryActivity extends AppCompatActivity {
    TextView proc, videocard, matPlat, operPam;
    Button backToHomeBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        proc = findViewById(R.id.proc);
        videocard = findViewById(R.id.videocard);
        matPlat = findViewById(R.id.matPlat);
        operPam = findViewById(R.id.OperPam);
        backToHomeBtn = findViewById(R.id.backToHome);

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent procIntent = new Intent(ChooseCategoryActivity.this, ProcActivity.class);
                startActivity(procIntent);
            }
        });
        videocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoCardIntent = new Intent(ChooseCategoryActivity.this, videoCardActivity.class);
                startActivity(videoCardIntent);
            }
        });
        matPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matPlatIntent = new Intent(ChooseCategoryActivity.this, matPlatActivity.class);
                startActivity(matPlatIntent);
            }
        });
        operPam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent operPamIntent = new Intent(ChooseCategoryActivity.this, operPamActivity.class);
                startActivity(operPamIntent);
            }
        });
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ChooseCategoryActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}