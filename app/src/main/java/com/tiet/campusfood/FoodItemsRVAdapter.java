package com.tiet.campusfood;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodItemsRVAdapter extends RecyclerView.Adapter<FoodItemsRVAdapter.ViewHolder> {
    private List<FoodItemsRVModel> lst;
    private OnAddClicked onAddClicked;

    public FoodItemsRVAdapter(List<FoodItemsRVModel> lst, OnAddClicked onAddClicked) {
        this.lst = lst;
        this.onAddClicked = onAddClicked;
    }

    public interface OnAddClicked{
        void addToCart(int position);

    }

    @NonNull
    @Override
    public FoodItemsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_items_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foodName.setText(lst.get(position).getName());
        holder.foodPrice.setText("Rs."+lst.get(position).getPrice());
        Picasso.get().load(lst.get(position).getImage()).into(holder.foodImg);
//        Glide.with(holder.foodImg.getContext()).load(lst.get(position).getImage()).into(holder.foodImg);
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddClicked.addToCart(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImg;
        TextView foodName,foodPrice;
        Button addToCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg=itemView.findViewById(R.id.inside_restraunt_img);
            foodName=itemView.findViewById(R.id.cart_name);
            foodPrice=itemView.findViewById(R.id.cart_price);
            addToCart=itemView.findViewById(R.id.food_add_btn);
        }
    }
}
