package com.example.project2.ViewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.Interface.ItemClickListener;
import com.example.project2.Model.Products;
import com.example.project2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Products> list;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductPrice, txtProductRating;
        public ImageView productImage;
        public ItemClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            txtProductName = itemView.findViewById(R.id.productName);
            txtProductPrice = itemView.findViewById(R.id.productPrice);
            txtProductRating = itemView.findViewById(R.id.productRating);
        }
        public void setItemClickListener(ItemClickListener listener){
            this.listener = listener;
        }
        public void onClick(View view){
            listener.onClick(view, getAdapterPosition(), false);
        }

    }



    public MyAdapter(Context context, ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_items_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products product = list.get(position);
        String imageURL = product.getImage();
        holder.txtProductName.setText(product.getName());
        holder.txtProductPrice.setText(product.getPrice() + " рублей");
        holder.txtProductRating.setText(product.getRating() + " ★");
        Picasso.get().load(imageURL).fit().centerInside().into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
