package com.tiet.campusfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserOrderRVAdapter extends RecyclerView.Adapter<UserOrderRVAdapter.ViewHolder> {

    List<UserOrderRVModel> lst;
    Context context;

    public UserOrderRVAdapter(List<UserOrderRVModel> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @NonNull
    @Override
    public UserOrderRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_order_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderRVAdapter.ViewHolder holder, int position) {
        holder.total.setText(lst.get(position).getTotal());
        holder.description.setText(lst.get(position).getDescription());
        holder.name.setText(lst.get(position).getName());

        if(lst.get(position).isDelivered()){
            holder.delivery.setText("Delivered");
//            holder.delivery.setTextColor(ContextCompat.getColor(holder.delivery.getContext(),R.color.green));
        }else{
            holder.delivery.setText("Pending");
//            holder.delivery.setTextColor(ContextCompat.getColor(holder.delivery.getContext(),R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,description,total,delivery;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.dashboard_restraunt_name);
            description=itemView.findViewById(R.id.dashboard_orders_details);
            total=itemView.findViewById(R.id.dashboard_orders_total);
            delivery=itemView.findViewById(R.id.dashboard_orders_delivery);
        }
    }
}
