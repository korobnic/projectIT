//package com.example.project2.ViewHolders;
//
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.project2.R;
//
//public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {
//
//    private Bitmap[] bitmapsLocal;
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        private final ImageView imageProduct;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imageProduct = itemView.findViewById(R.id.productImage);
//        }
//
//        public ImageView getImageProduct(){
//            return imageProduct;
//        }
//    }
//    @NonNull
//    @Override
//    public RecyclerViewCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewCustomAdapter.ViewHolder holder, int position) {
//        holder.getImageProduct().setImageBitmap(bitmapsLocal[position]);
//    }
//
//    @Override
//    public int getItemCount() {
//        return bitmapsLocal.length;
//    }
//}
