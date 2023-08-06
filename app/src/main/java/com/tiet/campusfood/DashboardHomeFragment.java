package com.tiet.campusfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DashboardHomeFragment extends Fragment implements RestrauntRVAdapter.OnCardClickListener {

    private RecyclerView foodTypeRv,restrauntRv;
    private List<FoodTypeRVModel> foodTypeRVModelList;
    private List<RestrauntRVModel> restrauntRVModelList;
    private FoodTypeRVAdapter foodTypeRVAdapter;
    private RestrauntRVAdapter restrauntRVAdapter;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private View rootView;
    private ImageView cart_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.dashboard_home_fragment,container,false);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        initFoodTypeAdapter();
        initRestrauntAdapter();

        cart_btn=rootView.findViewById(R.id.dashboard_cart);
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Cart.class);
                startActivity(i);
            }
        });

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


        restrauntRVAdapter=new RestrauntRVAdapter(getActivity(),restrauntRVModelList,this);
        restrauntRv.setAdapter(restrauntRVAdapter);

        restrauntRv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        restrauntRv.setHasFixedSize(true);
        restrauntRv.setNestedScrollingEnabled(false);
        populaterestrauntRVModelList();
    }

    private void populaterestrauntRVModelList() {


//        Find Restraunt info
        firebaseFirestore.collection("restraunts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("Data Fetch","Fetched");
//                    To avoid duplicacy
                    restrauntRVModelList.clear();
//                    Loop through every document
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        Log.d("Data Fetch","Done");
                        String docId=documentSnapshot.getId();
                        String name=documentSnapshot.getString("name");
                        String location=documentSnapshot.getString("location");
                        Log.d("ID",docId);
//                        Hardcoding next 2 fields for now
                        String rating="4.7";
                        String minCost="50";
                        RestrauntRVModel model=new RestrauntRVModel();
//                        Set everything in the list
                        model.setDocumentID(docId);
                        model.setCost(minCost);
                        model.setName(name);
                        model.setRating(rating);
                        model.setLocation(location);
//                        Push restraunt details to list for rec view
                        restrauntRVModelList.add(model);

                    }
                    Log.d("Data Fetch","Done1");
                    setImage();
                    restrauntRVAdapter.notifyDataSetChanged();
                }else{
//                    If task not successful
                    Log.d("Data Fetch","Error");
//                    Toast.makeText(getActivity(), "Error fetching details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void setImage(){
//        after list is filled with info fill image on success to avoid critical section
        Log.d("Sz",String.valueOf(restrauntRVModelList.size()));
//        Find Images by docID
        for(int i=0;i<restrauntRVModelList.size();++i) {
            final int pos=i;
            String docId=restrauntRVModelList.get(i).getDocumentID();
            String pathToFindImageInFirebaseStorage = "restraunts/res" + docId + "/banner.jpg";
            Log.d("SKS", pathToFindImageInFirebaseStorage);
            //                        Find image
            storageReference.child(pathToFindImageInFirebaseStorage).getDownloadUrl()
                    .addOnSuccessListener(uri -> {
                        //                                    Image download successful,set the uri to list

                        Log.d("Image url", uri.toString());
                        restrauntRVModelList.get(pos).setImage(uri.toString());
                        restrauntRVAdapter.notifyDataSetChanged();

                    })
                    .addOnFailureListener(exception -> {
                        Log.d("Image url","Fail");
                        //                                    Log.d("Image url","Fail");
                        //                                    Failed downloading image
                        //                                    Toast.makeText(getActivity(),"Error Downloading image", Toast.LENGTH_SHORT).show();
                        exception.printStackTrace();

                    });
        }
        restrauntRVAdapter.notifyDataSetChanged();

    }


    @Override
    public void restrauntCardClicked(int position) {
//        This function gets callback from recycler view through an interface whenever a card is clicked
        Intent i=new Intent(getActivity(), InsideRestraunt.class);
        i.putExtra("docId",restrauntRVModelList.get(position).getDocumentID());
        i.putExtra("image",restrauntRVModelList.get(position).getImage());
        i.putExtra("name",restrauntRVModelList.get(position).getName());
        i.putExtra("rating",restrauntRVModelList.get(position).getRating());
        startActivity(i);
    }
}