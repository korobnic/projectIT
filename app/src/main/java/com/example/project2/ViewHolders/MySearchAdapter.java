package com.example.project2.ViewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.Model.Products;
import com.example.project2.R;

import java.util.ArrayList;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.MySearchAdapterViewHolder> {

    public Context c;
    public ArrayList<Products> arrayList;

    public MySearchAdapter(Context c, ArrayList<Products> arrayList){
        this.c = c;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public MySearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);

        return new MySearchAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySearchAdapterViewHolder holder, int position) {
        Products product = arrayList.get(position);
        holder.txtProductShop.setText(product.getShop());
        holder.txtProductName.setText(product.getName());
        holder.txtProductPrice.setText(product.getPrice() + " рублей");
        holder.txtProductRating.setText(product.getRating() + " ★");
    }

    public class MySearchAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView txtProductName, txtProductPrice, txtProductRating, txtProductShop;

        public MySearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductShop = itemView.findViewById(R.id.productShop);
            txtProductName = itemView.findViewById(R.id.productName);
            txtProductPrice = itemView.findViewById(R.id.productPrice);
            txtProductRating = itemView.findViewById(R.id.productRating);
        }
    }
}
