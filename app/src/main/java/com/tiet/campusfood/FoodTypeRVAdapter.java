package com.tiet.campusfood;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodTypeRVAdapter extends RecyclerView.Adapter<FoodTypeRVAdapter.ViewHolder> {
    private Context context;
    private List<FoodTypeRVModel> lst;

    public FoodTypeRVAdapter(Context context, List<FoodTypeRVModel> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public FoodTypeRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_type_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTypeRVAdapter.ViewHolder holder, int position) {
        holder.image.setImageResource(lst.get(position).getImage());
        holder.text.setText(lst.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.food_type_img);
            text=itemView.findViewById(R.id.food_type_txt);
        }

    }
}
