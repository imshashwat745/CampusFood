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

public class DashboardHomeFragment extends Fragment {

    private RecyclerView foodTypeRv,restrauntRv;
    private List<FoodTypeRVModel> foodTypeRVModelList;
    private List<RestrauntRVModel> restrauntRVModelList;
    private FoodTypeRVAdapter foodTypeRVAdapter;
    private RestrauntRVAdapter restrauntRVAdapter;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.dashboard_home_fragment,container,false);

        initFoodTypeAdapter();
        initRestrauntAdapter();

        return rootView;
    }
    public void initFoodTypeAdapter(){
//        Find the recycler view
        foodTypeRv=rootView.findViewById(R.id.food_type_rv);

//        Add data to list to be displayed
        foodTypeRVModelList=new ArrayList<>();
        foodTypeRVModelList.add(new FoodTypeRVModel(R.drawable.logo,"Pizza"));
        foodTypeRVModelList.add(new FoodTypeRVModel(R.drawable.logo,"Burger"));
        foodTypeRVModelList.add(new FoodTypeRVModel(R.drawable.logo,"Dal"));
        foodTypeRVModelList.add(new FoodTypeRVModel(R.drawable.logo,"Momos"));
        foodTypeRVModelList.add(new FoodTypeRVModel(R.drawable.logo,"Healthy"));

//        Call Adapter class
        foodTypeRVAdapter=new FoodTypeRVAdapter(getActivity(),foodTypeRVModelList);
        foodTypeRv.setAdapter(foodTypeRVAdapter);

//        Set the layout
        foodTypeRv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        foodTypeRv.setHasFixedSize(true);
        foodTypeRv.setNestedScrollingEnabled(false);
    }

    public void initRestrauntAdapter(){
        restrauntRv=rootView.findViewById(R.id.restraunt_rv);

        restrauntRVModelList=new ArrayList<>();
        restrauntRVModelList.add(new RestrauntRVModel(R.drawable.sharma_ji_chai,"Sharma Ji Ki Chai","Lalbagh,Lucknow"
        ,"4.9","Min Rs.25"));
        restrauntRVModelList.add(new RestrauntRVModel(R.drawable.royal_cafe,"Royal Cafe","Hazratganj,Lucknow"
                ,"4.7","Min Rs.150"));
        restrauntRVModelList.add(new RestrauntRVModel(R.drawable.bajpayee,"Bajpayee Kachori","Hazratganj,Lucknow"
        ,"4.9","Min Rs.50"));
        restrauntRVModelList.add(new RestrauntRVModel(R.drawable.dominos,"Dominos Pizza","Gomti Nagar,Lucknow"
        ,"4.9","Min Rs.25"));

        restrauntRVAdapter=new RestrauntRVAdapter(getActivity(),restrauntRVModelList);
        restrauntRv.setAdapter(restrauntRVAdapter);

        restrauntRv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        restrauntRv.setHasFixedSize(true);
        restrauntRv.setNestedScrollingEnabled(false);
    }


}
