package com.example.prakriti.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.prakriti.myapplication.FoodDetailsActivity;
import com.example.prakriti.myapplication.Pojo.ProductObject;
import com.example.prakriti.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    public Context context;
    private List<ProductObject> productList;

    public ProductAdapter(Context context, List<ProductObject> productList) {
        this.context = context;
        this.productList = productList;

    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.food_adapter_list, parent, false);
         return new ProductHolder(view);

    }


    @Override
    public void onBindViewHolder(ProductHolder holder, final int position) {
        ProductObject one = productList.get(position);
        holder.productName.setText("Product Name : " + productList.get(position).getName());
        holder.productPrice.setText("Product Price : " + productList.get(position).getPrice());

        Picasso.with(context).load(productList.get(position).getImage()).into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ProductObject one = productList.get(position);
                Intent intent2 = new Intent(context, FoodDetailsActivity.class);
                intent2.putExtra("yellow", one);
                context.startActivity(intent2);
                Log.e("donkey", "monkey");
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
