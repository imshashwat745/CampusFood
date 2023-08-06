package com.tiet.campusfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestrauntRVAdapter extends RecyclerView.Adapter<RestrauntRVAdapter.ViewHolder> {
    private Context context;
    private List<RestrauntRVModel> lst;
    private OnCardClickListener onCardClickListener;

    public RestrauntRVAdapter(Context context, List<RestrauntRVModel> lst, OnCardClickListener onCardClickListener) {
        this.context = context;
        this.lst = lst;
        this.onCardClickListener = onCardClickListener;
    }

    @NonNull
    @Override
    public RestrauntRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restraunt_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestrauntRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/tietcampusfood.appspot.com/o/restraunts%2Fres7985152772%2Fbanner.jpg?alt=media&token=7098a8cf-1a7c-48c2-8380-ed1de263d72a").into(holder.image);
        Picasso.get().load(lst.get(position).getImage()).into(holder.image);
        holder.cost.setText(lst.get(position).getCost());
        holder.location.setText(lst.get(position).getLocation());
        holder.name.setText(lst.get(position).getName());
        holder.rating.setText(lst.get(position).getRating());

        holder.restrauntCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClickListener.restrauntCardClicked(position);
            }
        });
    }

    public interface OnCardClickListener {
//        This Interface is used to call to parent fragment that a card is clicked
        public void restrauntCardClicked(int position);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,location,cost,rating;
        CardView restrauntCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.restraunt_img);
            name=itemView.findViewById(R.id.restraunt_name);
            location=itemView.findViewById(R.id.restraunt_location);
            cost=itemView.findViewById(R.id.restraunt_cost);
            rating=itemView.findViewById(R.id.restraunt_rating);
            restrauntCard=itemView.findViewById(R.id.restraunt_card);
        }
    }
}
