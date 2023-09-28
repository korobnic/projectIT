package com.example.project2.ui.Users;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project2.Model.Products;
import com.example.project2.R;
import com.example.project2.ViewHolders.MyAdapter;
import com.example.project2.ViewHolders.MySearchAdapter;
import com.example.project2.ui.LoginActivity;
import com.example.project2.ui.Users.Category.ChooseCategoryActivity;
import com.example.project2.ui.Users.Shops.ChooseShopActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManagerRecyclerView;
//    private RecyclerViewCustomAdapter;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<Products> list;
    ImageButton ProductCart;
    EditText search;
    ArrayList<Products> arrayList;
    private RequestQueue requestQueue;

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search = findViewById(R.id.search);
        arrayList = new ArrayList<>();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    searchSMT(s.toString());
                }else{
                    searchSMT("");
                }
            }
        });

//        ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA_IMAGES}, PackageManager.PERMISSION_GRANTED);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view)-> {

                Snackbar.make(view, "Здесь будет переход в корзину", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.recycle_menu);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
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
    private void searchSMT(String s) {
        Query queryName = databaseReference.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
        Query queryShop = databaseReference.orderByChild("shop").startAt(s).endAt(s + "\uf8ff");
        queryName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    arrayList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()){
                        final Products product = dss.getValue(Products.class);
                        arrayList.add(product);
                    }
                    MySearchAdapter mySearchAdapter = new MySearchAdapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(mySearchAdapter);
                    mySearchAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        queryShop.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    arrayList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()){
                        final Products product = dss.getValue(Products.class);
                        arrayList.add(product);
                    }
                    MySearchAdapter mySearchAdapter = new MySearchAdapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(mySearchAdapter);
                    mySearchAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //    public void buttonRecyclerViewUpdate(View view){
//
//        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
//        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
//        File fileImage = new File()
//    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.shop){
            Intent shopsIntent = new Intent(HomeActivity.this, ChooseShopActivity.class);
            startActivity(shopsIntent);

        } else if(id == R.id.nav_categories){
            Intent categoryIntent = new Intent(HomeActivity.this, ChooseCategoryActivity.class);
            startActivity(categoryIntent);

        } else if(id == R.id.nav_setings){


        } else if(id == R.id.nav_logout){
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}