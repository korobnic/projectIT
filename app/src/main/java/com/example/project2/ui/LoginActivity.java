package com.example.project2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.R;
import com.example.project2.Model.Users;
import com.example.project2.Prevalent.Prevalent;
import com.example.project2.ui.Admin.AdminCategoryActivity;
import com.example.project2.ui.Users.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText userName, password, number;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe;
    private TextView AdminLink, NotAdminLink, forgetPassword;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AdminLink = findViewById(R.id.adminLink);
        NotAdminLink = findViewById(R.id.notAdminLink);
        login = (Button) findViewById(R.id.reg2);
        password = (EditText) findViewById(R.id.password);
        number = (EditText) findViewById(R.id.loginPhoneImput);
        loadingBar = new ProgressDialog(this);
        checkBoxRememberMe = findViewById(R.id.remember);
        forgetPassword = findViewById(R.id.forgetPassword);
        Paper.init(this);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPasswordIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(forgetPasswordIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)   {
                loginUser();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                login.setText("Вход для администрации");
                parentDbName = "Admins";
            }

        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotAdminLink.setVisibility(View.INVISIBLE);
                AdminLink.setVisibility(View.VISIBLE);
                login.setText("Вход");
                parentDbName = "Users";
            }
        });

    }

    private void loginUser() {
        String password2 = password.getText().toString();
        String number2 = number.getText().toString();

        if(TextUtils.isEmpty(password2)){
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(number2)){
            Toast.makeText(this, "Введите номер", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Выполняется вход");
            loadingBar.setMessage("Пожалуйста подождите");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(number2, password2);
        }
    }
    private void ValidateUser(String number2, String password2) {

        if(checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserNumberKey, number2);
            Paper.book().write(Prevalent.UserPasswordKey, password2);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(number2).exists()){
                    Users usersData = dataSnapshot.child(parentDbName).child(number2).getValue(Users.class);

                    if(usersData.getNumber().equals(number2)){
                        if(usersData.getPassword().equals(password2)){
                            if(parentDbName.equals("Users")){
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);//home
                                startActivity(homeIntent);
                            }
                            else if(parentDbName.equals("Admins")){
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(homeIntent);
                            }
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Аккаунт с номером " + number2 + " не существует", Toast.LENGTH_SHORT).show();

                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
