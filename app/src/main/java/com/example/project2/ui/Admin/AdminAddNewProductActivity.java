package com.example.project2.ui.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String categoryName, name, price, rating, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl, shop;
    private EditText productName, productPrice, productRating, productShop;
    private Button addNewProduct;
    private static final int galleryPick = 1;
    private Uri imageUri;
    private StorageReference productImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        init();
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        name = productName.getText().toString();
        price = productPrice.getText().toString();
        rating = productRating.getText().toString();
        shop = productShop.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Добавьте название", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "Добавьте стоимость", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(rating)){
            Toast.makeText(this, "Добавьте рэйтинг", Toast.LENGTH_SHORT).show();
        }else {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation() {

        loadingBar.setTitle("Загрузка данных");
        loadingBar.setMessage("Пожалуйста подождите");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;
        loadingBar.dismiss();
        SaveProductInfoToDatabase();
    }
    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("data", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("name", name);
        productMap.put("price", price);
        productMap.put("shop", shop);
        productMap.put("category", categoryName);
        productMap.put("rating", rating);
        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNewProductActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                    startActivities(new Intent[]{loginIntent});
                }
                else {
                    String massege = task.getException().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Ошибка: " + massege, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });

    }
    private void init(){
        categoryName = getIntent().getExtras().get("category").toString();
        productShop = findViewById(R.id.productShop);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);
        addNewProduct = findViewById(R.id.addNewProduct);
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);
    }
}
//        productImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OpenGallery();
//            }
//        });
//        if(imageUri == null){
//            Toast.makeText(this, "Добавьте изображение", Toast.LENGTH_SHORT).show();
//        }
//        StorageReference filePath = productImageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");
//        final UploadTask uploadTask = filePath.putFile(imageUri);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                String messege = e.toString();
//                Toast.makeText(AdminAddNewProductActivity.this, "Ошибка: " + messege, Toast.LENGTH_SHORT).show();
//                loadingBar.dismiss();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//               Toast.makeText(AdminAddNewProductActivity.this, "Изображение успешно загружено", Toast.LENGTH_SHORT).show();
//
//
//                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                        if(!task.isSuccessful()){
//                            throw task.getException();
//                        }
//                        downloadImageUrl = filePath.getDownloadUrl().toString();
//                        return filePath.getDownloadUrl();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(AdminAddNewProductActivity.this, "Изображение сохранено", Toast.LENGTH_SHORT).show();
//
//                            SaveProductInfoToDatabase();
//                        }
//                    }
//                });
//            }
//        });
//    private void OpenGallery(){
//        Intent galleryIntent = new Intent();
//        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent, galleryPick);
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == galleryPick && resultCode == RESULT_OK && data != null){
//            imageUri = data.getData();
//            productImage.setImageURI(imageUri);
//        }
//    }