package com.tiet.campusfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserOrderRVAdapter extends RecyclerView.Adapter<UserOrderRVAdapter.ViewHolder> {

    List<UserOrderRVModel> lst;

    public UserOrderRVAdapter(FragmentActivity activity, List<UserOrderRVModel> lst) {
        this.lst = lst;
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
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,description,total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.dashboard_restraunt_name);
            description=itemView.findViewById(R.id.dashboard_orders_details);
            total=itemView.findViewById(R.id.dashboard_orders_total);
        }
    }
}
