package com.tiet.campusfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DashboardUserOrdersFragment extends Fragment {
    List<UserOrderRVModel> userOrderRVModelList;
    RecyclerView userOrderRV;
    UserOrderRVAdapter userOrderRVAdapter;
    private View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.dashboard_user_orders_fragment,container,false);

        initUserOrdersRV();

        return rootView;
    }
    public void initUserOrdersRV(){
        userOrderRVModelList=new ArrayList<>();
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));
        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
                "1540"));

        userOrderRV=rootView.findViewById(R.id.dashboard_orders_rv);

        userOrderRVAdapter=new UserOrderRVAdapter(getActivity(),userOrderRVModelList);
        userOrderRV.setAdapter(userOrderRVAdapter);

        userOrderRV.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        userOrderRV.setHasFixedSize(true);
    }
}
