package com.example.project2.ui.Users.Shops;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.R;
import com.example.project2.ui.Users.HomeActivity;

public class ChooseShopActivity extends AppCompatActivity {
    TextView dns, citilink, yandexmarket, Mvideo;
    Button backToHomeBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shop);
        dns = findViewById(R.id.dns);
        citilink = findViewById(R.id.citilink);
        yandexmarket = findViewById(R.id.yandexMarket);
        Mvideo = findViewById(R.id.MVideo);
        backToHomeBtn = findViewById(R.id.backToHome);

        dns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dnsIntent = new Intent(ChooseShopActivity.this, DnsActivity.class);
                startActivity(dnsIntent);
            }
        });
        citilink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent citilinkIntent = new Intent(ChooseShopActivity.this, CitilinkActivity.class);
                startActivity(citilinkIntent);
            }
        });
        yandexmarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yandexmarketIntent = new Intent(ChooseShopActivity.this, YandexMarketActivity.class);
                startActivity(yandexmarketIntent);
            }
        });
        Mvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mVideoIntent = new Intent(ChooseShopActivity.this, MvideoActivity.class);
                startActivity(mVideoIntent);
            }
        });
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ChooseShopActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}

