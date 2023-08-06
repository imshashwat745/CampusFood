package com.tiet.campusfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InsideRestraunt extends AppCompatActivity implements FoodItemsRVAdapter.OnAddClicked {
    private FirebaseAuth firebaseAuth;
    private RecyclerView foodItemRv;
    private ImageView cart_btn;
    List<FoodItemsRVModel> foodItemsRVModelList;
    FoodItemsRVAdapter foodItemsRVAdapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private ImageView bannerImg;
    private TextView bannerName,bannerRating;
    private String restrauntDocumentId,phno;
    private Set<String> alreadyInCart;
    boolean cartContainsAnotherRestrauntItems;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_restraunt);

        Log.d("Reached","0");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        cartContainsAnotherRestrauntItems=false;
//        Find user phno for document id of user
        phno=firebaseAuth.getCurrentUser().getPhoneNumber();
        Log.d("Reached","1");

        setBanner();
        alreadyInCart=new HashSet<>();
        restrauntDocumentId =getIntent().getStringExtra("docID");
        /*First add already added items in a set so that it is not added again here,but incremented/decremented in cart only*/
        restrauntDocumentId ="7905568512";
        Log.d("Reached","2");
        firebaseFirestore.collection("users").document(phno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("Reached","3");
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot.contains("cart")){
                        // Get a map of all fields and their values
                        Map<String, Object> fieldsMap = snapshot.getData();
                        Log.d("Reached","4");

                        // Traverse every field in the document
                        for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
                            String fieldName = entry.getKey();
                            Object fieldValue = entry.getValue();
                            if(fieldName.equals("firstName")||fieldName.equals("lastName"))continue;
                            alreadyInCart.add(fieldName);

                        }
                        if(!snapshot.getString("cart").equals(restrauntDocumentId)){
//                        Means cart contains another restraunt items,so update boolean variable so that cart can be
//                        made emoty before adding 1st itme from current restraunt
                            cartContainsAnotherRestrauntItems=true;
                        }
                    }

//                    Inintialize food item RV after adding to set is complete
                    initFoodItemRv();
                }
            }
        });

//        initFoodItemRv();

        cart_btn=findViewById(R.id.inside_restraunt_cart);
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(InsideRestraunt.this, Cart.class);
                startActivity(i);
                finish();// Finish so that,on updation from cart,data reloads here too
            }
        });

    }

    private void setBanner() {
//        Funtion to set Banner
        bannerImg=findViewById(R.id.inside_restraunt_photo);
        bannerName=findViewById(R.id.inside_restraunt_name);
        bannerRating=findViewById(R.id.inside_restraunt_rating);
        Intent intent=getIntent();
        Picasso.get().load(intent.getStringExtra("image")).into(bannerImg);
        bannerName.setText(intent.getStringExtra("name"));
        bannerRating.setText(intent.getStringExtra("rating"));
    }

    public void initFoodItemRv(){
        foodItemsRVModelList=new ArrayList<>();


        foodItemsRVAdapter=new FoodItemsRVAdapter(foodItemsRVModelList,this);
        populateFoodItemList();

        foodItemRv=findViewById(R.id.food_items_rv);
        foodItemRv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        foodItemRv.setAdapter(foodItemsRVAdapter);
        foodItemRv.setHasFixedSize(true);

    }
    private void populateFoodItemList(){
//        To fetch Food Items info

        firebaseFirestore.collection("restraunts/"+ restrauntDocumentId +"/food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
//                    To avoid duplicacy
                    foodItemsRVModelList.clear();
                    for(DocumentSnapshot documentSnapshot:task.getResult()){
                        FoodItemsRVModel model=new FoodItemsRVModel();
                        model.setName(documentSnapshot.getString("name"));
                        model.setPrice(documentSnapshot.getString("price"));
                        foodItemsRVModelList.add(model);
                    }
                    foodItemsRVAdapter.notifyDataSetChanged();
//                    Now set images
                    setImage();
                }else{
                    Log.d("Data fetch Inside Restraunt","Fail");
                }
            }
        });
    }

    private void setImage() {
//        Continue setting images
        for(int i=0;i<foodItemsRVModelList.size();++i){
            final int pos=i;
            String key=make_key(foodItemsRVModelList.get(i).getName());
            final String path="restraunts/res"+ restrauntDocumentId +"/food/"+key+".jpg";
            storageReference.child(path).getDownloadUrl().addOnSuccessListener(uri -> {
//                Set Image in every document
                foodItemsRVModelList.get(pos).setImage(uri.toString());
                Log.d("Image Fetch Inside Restraunt", foodItemsRVModelList.get(pos).getImage());
//                Picasso.get().load(uri.toString()).into(bannerImg);
                foodItemsRVAdapter.notifyDataSetChanged();

            }).addOnFailureListener(exception->{
                Log.d("Image Fetch Inside Restraunt","Fail "+path);
            });
        }
            foodItemsRVAdapter.notifyDataSetChanged();

    }

    private String make_key(String name) {
        String ans="_";
        name=name.toLowerCase();
        for(int i=0;i<name.length();++i){
            if(name.charAt(i)!=' ')ans+=name.charAt(i);
        }
        return ans;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void addToCart(int position) {
//        We are called here for adding to cart,and update the add Button
        String key=make_key(foodItemsRVModelList.get(position).getName());
        if(alreadyInCart.contains(key)&&!cartContainsAnotherRestrauntItems){
//            This item is already in cart so dont add and return
            Toast.makeText(this, "Already added,edit in Cart", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,Object> newFields=new HashMap<>();
//        Control reaching here means this item isn't in Cart till now
        if(cartContainsAnotherRestrauntItems){
//            Remove extra items from cart
            Map<String,Object> delete=new HashMap<>();
//            Traverse our set it contains fields of cart items of other restraunt
            for(String ele:alreadyInCart){
                delete.put(ele, FieldValue.delete());
            }
//            Now use update query to delete these fields
            firebaseFirestore.collection("users").document(phno).update(delete);
            alreadyInCart.clear();

        }

//        Now add this item to cart
        newFields.put("cart", restrauntDocumentId);
        newFields.put(key,1);
        firebaseFirestore.collection("users").document(phno).update(newFields).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                alreadyInCart.add(key);
                Toast.makeText(InsideRestraunt.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InsideRestraunt.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}