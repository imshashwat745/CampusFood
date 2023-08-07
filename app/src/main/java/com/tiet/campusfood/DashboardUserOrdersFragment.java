package com.tiet.campusfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardUserOrdersFragment extends Fragment{
    List<UserOrderRVModel> userOrderRVModelList;
    RecyclerView userOrderRV;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    UserOrderRVAdapter userOrderRVAdapter;
    private View rootView;
    private String phno;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.dashboard_user_orders_fragment,container,false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        phno=firebaseAuth.getCurrentUser().getPhoneNumber();
        initUserOrdersRV();

        return rootView;
    }
    public void initUserOrdersRV(){
        userOrderRVModelList=new ArrayList<>();
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));
//        userOrderRVModelList.add(new UserOrderRVModel("Royal Cafe","Basket Chat x 7 = 1050\nLassi x 7=490",
//                "1540"));

        userOrderRV=rootView.findViewById(R.id.dashboard_orders_rv);

        userOrderRVAdapter=new UserOrderRVAdapter(userOrderRVModelList,getActivity());
        userOrderRV.setAdapter(userOrderRVAdapter);

        userOrderRV.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        userOrderRV.setHasFixedSize(true);
        populateRV();
    }

    private void populateRV() {
        firebaseFirestore.collection("users/"+phno+"/orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot queryDocumentSnapshots=task.getResult();
                    for(DocumentSnapshot snapshot:queryDocumentSnapshots){
                        UserOrderRVModel model=new UserOrderRVModel();
                        model.setOrderId(snapshot.getId());
                        model.setRestrauntId(snapshot.getString("cart"));
                        model.setDelivered(snapshot.getString("delivered").equals("true")?true:false);
                        model.setTotal(snapshot.getString("total"));
                        String description="";
                        for(Map.Entry<String,Object> e:snapshot.getData().entrySet()){
                            if(e.getValue().equals("cart")||e.getValue().equals("total")||e.getValue().equals("delivered"))continue;
                            description+=e.getKey()+": "+e.getValue()+"\n";
                        }
                        model.setDescription(description);
                        userOrderRVModelList.add(model);
                    }
                    userOrderRVAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}
