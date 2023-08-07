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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity implements CartRVAdapter.OnItemQuantityChange {

    private List<CartRVModel> cartRVModelList;
    private RecyclerView cartRV;
    private CartRVAdapter cartRVAdapter;
    private TextView cartTotal,cartGrandTotal,cartTax,cartDeliveryCharge;
    private Button orderBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String phno;
    private double CART_TOTAL=0,CART_TAX=0.18,DELIVERY_CHARGE=20;
    private String docId="8546075088";
    private Map<String,String> alreadyInCart;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);

//        setContentView(R.layout.empty_cart);
        firebaseAuth=FirebaseAuth.getInstance();
        phno=firebaseAuth.getCurrentUser().getPhoneNumber();
        firebaseFirestore=FirebaseFirestore.getInstance();
        alreadyInCart=new HashMap<>();
//        First find if cart is empty or not,if empty then display that cart is empty
        firebaseFirestore.collection("users").document(phno).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    if(documentSnapshot.contains("cart")){
//                                        This means items are there in cart, so show cart
                                        docId=documentSnapshot.getString("cart");

                                        // Get a map of all fields and their values
                                        Map<String, Object> fieldsMap = documentSnapshot.getData();
                                        Log.d("Reached","4");

                                        // Traverse every field in the document
                                        for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
                                            String fieldName = entry.getKey();
                                            String fieldValue=(String)(entry.getValue());
                                            if(fieldName.equals("firstName")||fieldName.equals("lastName")||fieldName.equals("cart"))continue;


                                            alreadyInCart.put(fieldName,fieldValue);

                                        }
                                        initCart();
                                    }else{
//                                        Set Empty cart method
                                        setContentView(R.layout.empty_cart);
                                    }
                                }
                            }
                        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void initCart(){

        setContentView(R.layout.activity_cart);
        cartTax=findViewById(R.id.cart_tax);
        cartTotal=findViewById(R.id.cart_total);
        cartGrandTotal=findViewById(R.id.cart_grand_total);
        cartDeliveryCharge=findViewById(R.id.cart_delivery_vharges);
        orderBtn=findViewById(R.id.cart_order_button);

        initCartRV();

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Cart.this, Payment.class);
                double GRAND_TOTAL=CART_TOTAL+(CART_TOTAL*CART_TAX)+DELIVERY_CHARGE;
                i.putExtra("Cost",String.valueOf(GRAND_TOTAL));
                startActivity(i);
            }
        });
    }

    private void initCartRV() {
        cartRV=findViewById(R.id.cart_items_rv);
        cartRVModelList=new ArrayList<>();
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","2","175"));
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","3","175"));
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","1","175"));
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","5","175"));
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","7","175"));
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","7","175"));
//        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","7","175"));

        cartRVAdapter=new CartRVAdapter(cartRVModelList,this);
        cartRV.setAdapter(cartRVAdapter);
        cartRV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        cartRV.setNestedScrollingEnabled(false);
        populateCartRV();

    }

    private void populateCartRV() {
//
        firebaseFirestore.collection("restraunts/"+docId+"/food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot snapshot=task.getResult();
                    for(DocumentSnapshot documentSnapshot:snapshot){
//                        This means add only those documents which are inside the cart
                        if(!alreadyInCart.containsKey(documentSnapshot.getId()))continue;
//                        Add data to list

                        CartRVModel model=new CartRVModel();
                        model.setPrice(documentSnapshot.getString("price"));
                        model.setName(documentSnapshot.getString("name"));
                        model.setImageID(documentSnapshot.getId());
                        model.setQuantity(alreadyInCart.get(documentSnapshot.getId()));
                        model.setTotal();
                        cartRVModelList.add(model);


                    }
//                    Set total details card
                    calculateTotal();
                    setCartDetails();
                    setImageInCart();

                    cartRVAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void setImageInCart() {
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        for(int i=0;i<cartRVModelList.size();++i){
            final int pos=i;
            String imageId=cartRVModelList.get(i).getImageID();
            String path="restraunts/res"+docId+"/food/"+imageId+".jpg";
            Log.d("img path",path);
            storageReference.child(path).getDownloadUrl().addOnSuccessListener(uri->{
                cartRVModelList.get(pos).setImage(uri.toString());

                Log.d("Cart Image Fetch","Success "+uri.toString());
                cartRVAdapter.notifyDataSetChanged();
            }).addOnFailureListener(exception->{
                Toast.makeText(this, "Error fething image", Toast.LENGTH_SHORT).show();
                Log.d("Cart Image Fetch","Fail "+ exception.getMessage()+" ... "+path);
            });
        }
        cartRVAdapter.notifyDataSetChanged();
    }

    private void calculateTotal() {

        for(int i=0;i<cartRVModelList.size();++i){
            CART_TOTAL+=Double.valueOf(cartRVModelList.get(i).getTotal());
        }
    }

    @Override
    public void addItem(int position) {
//        Called from interface in CartRVAdapter that add button is clicked so update quantity

//        First update it in database
        String id=cartRVModelList.get(position).getImageID();// Image id is field of the item in db of the user
        String updatedValue=String.valueOf(Integer.valueOf(cartRVModelList.get(position).getQuantity())+1);
        Map<String,Object> updates=new HashMap<>();
        updates.put(id,updatedValue);
        firebaseFirestore.collection("users").document(phno).update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                Update already in cart also
                updateAlreadyInCart(id,updatedValue);
//                Update successful
                cartRVModelList.get(position).setQuantity(updatedValue);
//        Also update cart total
                CART_TOTAL+=Double.valueOf(cartRVModelList.get(position).getPrice());
                setCartDetails();
                cartRVAdapter.notifyItemChanged(position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cart.this, "Server Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void subItem(int position) {
        //        Called from interface in CartRVAdapter that subtract button is clicked so update quantity


//        Update in db first
        String id=cartRVModelList.get(position).getImageID();// Image id is field of the item in db of the user
        String updatedValue=String.valueOf(Integer.valueOf(cartRVModelList.get(position).getQuantity())-1);
        Map<String,Object> updates=new HashMap<>();

        if(cartRVModelList.get(position).getQuantity().equals("1")) {
            updates.put(id, FieldValue.delete());
            if(alreadyInCart.size()==1){
//                This means this is the last item in cart so make the cart empty,i.e. remove cart field from the document
                updates.put("cart",FieldValue.delete());
            }
        }
        else updates.put(id,updatedValue);
        firebaseFirestore.collection("users").document(phno).update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                Success updating db
                updateAlreadyInCart(id,updatedValue);


                cartRVModelList.get(position).setQuantity(updatedValue);
//        Also update cart total
                CART_TOTAL-=Double.valueOf(cartRVModelList.get(position).getPrice());
                setCartDetails();
                if(cartRVModelList.get(position).getQuantity().equals("0")){
                    cartRVModelList.remove(position);
                    cartRVAdapter.notifyItemRemoved(position);
                }else{
                    cartRVAdapter.notifyItemChanged(position);
                }
                if(alreadyInCart.size()==0){
//                    Move to empty cart layout
                    setContentView(R.layout.empty_cart);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cart.this, "Server Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void updateAlreadyInCart(String key,String value){
        alreadyInCart.remove(key);
        if(value.equals("0"))return;
        else{
            alreadyInCart.put(key,value);
        }
    }

    private void setCartDetails() {
        double GRAND_TOTAL=CART_TOTAL+(CART_TOTAL*CART_TAX)+DELIVERY_CHARGE;
        cartTax.setText("Rs."+String.valueOf(CART_TAX*CART_TOTAL));
        cartTotal.setText("Rs."+String.valueOf(CART_TOTAL));
        cartDeliveryCharge.setText("Rs."+String.valueOf(DELIVERY_CHARGE));
        cartGrandTotal.setText("Rs."+String.valueOf(GRAND_TOTAL));


    }
}