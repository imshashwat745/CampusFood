package com.tiet.campusfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestrauntRVAdapter extends RecyclerView.Adapter<RestrauntRVAdapter.ViewHolder> {
    private Context context;
    private List<RestrauntRVModel> lst;

    public RestrauntRVAdapter(Context context, List<RestrauntRVModel> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public RestrauntRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restraunt_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestrauntRVAdapter.ViewHolder holder, int position) {
        holder.image.setImageResource(lst.get(position).getImage());
        holder.cost.setText(lst.get(position).getCost());
        holder.location.setText(lst.get(position).getLocation());
        holder.name.setText(lst.get(position).getName());
        holder.rating.setText(lst.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,location,cost,rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.restraunt_img);
            name=itemView.findViewById(R.id.restraunt_name);
            location=itemView.findViewById(R.id.restraunt_location);
            cost=itemView.findViewById(R.id.restraunt_cost);
            rating=itemView.findViewById(R.id.restraunt_rating);
        }
    }
}
