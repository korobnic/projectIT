package com.example.project2.ui.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.Model.Users;
import com.example.project2.Prevalent.Prevalent;
import com.example.project2.R;
import com.example.project2.ui.LoginActivity;
import com.example.project2.ui.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button reg;
    private Button enter;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        reg = findViewById(R.id.registration);
        enter = findViewById(R.id.enter);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivities(new Intent[]{loginIntent});
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivities(new Intent[]{regIntent});
            }
        });

        String UserNumberKey = Paper.book().read(Prevalent.UserNumberKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserNumberKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserNumberKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                ValidatUser(UserNumberKey, UserPasswordKey);

                loadingBar.setTitle("Выполняется вход");
                loadingBar.setMessage("Пожалуйста подождите");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
        

    }

    private void ValidatUser ( final String number2, final String password2){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(number2).exists()) {
                    Users usersData = dataSnapshot.child("Users").child(number2).getValue(Users.class);

                    if (usersData.getNumber().equals(number2)) {
                        if (usersData.getPassword().equals(number2)) {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);//Home
                            startActivity(homeIntent);
                        } else {
                            loadingBar.dismiss();
                        }
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Аккаунт с номером " + number2 + " не существует", Toast.LENGTH_SHORT).show();

                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);//HOme
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}