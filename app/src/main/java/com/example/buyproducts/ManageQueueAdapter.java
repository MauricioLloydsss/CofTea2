package com.example.buyproducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageQueueAdapter extends RecyclerView.Adapter<ManageQueueAdapter.ProductViewHolder> {
    private List<QueueEntry> productList;
    private Context context;

    public ManageQueueAdapter(Context context, List<QueueEntry> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qeueuadapter, parent, false);
        return new ProductViewHolder(view, productList);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        QueueEntry product = productList.get(position);
        holder.productNameTextView.setText(product.getCustomerName());
        holder.productIdTextView.setText(product.getId());
        holder.productPriceEditText.setText(product.getCustomerPhone());
        holder.productQuantityTextView.setText("" + product.getTotalPayment());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceEditText;
        TextView productQuantityTextView;

        TextView productIdTextView;
        ImageView productImageView;
        Button editBTN, deleteBTN;
        List<QueueEntry> productList;

        ProductViewHolder(View itemView, List<QueueEntry> productList) {
            super(itemView);
            this.productList = productList;

            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceEditText = itemView.findViewById(R.id.productPriceEditText);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productIdTextView = itemView.findViewById(R.id.productIdTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
            editBTN = itemView.findViewById(R.id.BTNEdit);
            deleteBTN = itemView.findViewById(R.id.BTNDelete);
        }
    }
}
