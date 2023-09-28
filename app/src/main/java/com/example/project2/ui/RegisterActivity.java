package com.example.project2.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText userName, password, number;
    private ProgressDialog loadingBar;
    TextView toLogin;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toLogin = findViewById(R.id.toLogin);
        register = (Button) findViewById(R.id.register);
        userName = (EditText) findViewById(R.id.userNameImput);
        password = (EditText) findViewById(R.id.registerPassword);
        number = (EditText) findViewById(R.id.registerPhoneImput);
        loadingBar = new ProgressDialog(this);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLoginIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String username = userName.getText().toString();
        String password2 = password.getText().toString();
        String number2 = number.getText().toString();


        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password2)){
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(number2)){
            Toast.makeText(this, "Введите номер", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Создаие аккаунта");
            loadingBar.setMessage("Пожалуйста подождите");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhone(username, number2, password2);
        }
    }
    private void ValidatePhone(String username, String number2, String password2) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number2).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("number", number2);
                    userDataMap.put("name", username);
                    userDataMap.put("password", password2);

                    RootRef.child("Users").child(number2).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();

                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);//Login
                                startActivities(new Intent[]{loginIntent});
                            }else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Номер " + number2 + " уже зарегестрирован", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivities(new Intent[]{loginIntent});
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}