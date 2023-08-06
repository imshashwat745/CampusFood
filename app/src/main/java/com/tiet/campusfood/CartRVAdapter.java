package com.tiet.campusfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.ViewHolder> {
    private List<CartRVModel> lst;

    public CartRVAdapter(List<CartRVModel> lst, OnItemQuantityChange onItemQuantityChange) {
        this.lst = lst;
        this.onItemQuantityChange = onItemQuantityChange;
    }

    OnItemQuantityChange onItemQuantityChange;

    @NonNull
    @Override
    public CartRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart__rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRVAdapter.ViewHolder holder, int position) {
        holder.total.setText("Rs."+lst.get(position).getTotal());
        holder.quantity.setText(lst.get(position).getQuantity());
        holder.price.setText("RS."+lst.get(position).getPrice());
        holder.img.setImageResource(lst.get(position).getImage());

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemQuantityChange.addItem(position);
            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemQuantityChange.subItem(position);
            }
        });
    }
    //    Interface to call back parent activity on change in quantity of an item
    public interface OnItemQuantityChange{
    void addItem(int position);
    void subItem(int position);
}
    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img,addBtn,subBtn;
        private TextView price,quantity,total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.inside_restraunt_img);
            addBtn=itemView.findViewById(R.id.cart_add);
            subBtn=itemView.findViewById(R.id.cart_subtract);
            price=itemView.findViewById(R.id.cart_price);
            quantity=itemView.findViewById(R.id.cart_quantity);
            total=itemView.findViewById(R.id.cart_item_total);
        }
    }
}
