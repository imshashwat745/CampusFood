package com.tiet.campusfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        phno=firebaseAuth.getCurrentUser().getPhoneNumber();
        firebaseFirestore=FirebaseFirestore.getInstance();
//        First find if cart is empty or not,if empty then display that cart is empty
        firebaseFirestore.collection("users").document(phno).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    if(documentSnapshot.contains("cart")){
//                                        This means items are there in cart, so show cart
                                    }
                                }
                            }
                        })


        setContentView(R.layout.activity_cart);

        cartTax=findViewById(R.id.cart_tax);
        cartTotal=findViewById(R.id.cart_total);
        cartGrandTotal=findViewById(R.id.cart_grand_total);
        cartDeliveryCharge=findViewById(R.id.cart_delivery_vharges);

        initCartRV();

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Cart.this, Payment.class);
                startActivity(i);
            }
        });
    }

    private void initCartRV() {
        cartRV=findViewById(R.id.cart_items_rv);
        cartRVModelList=new ArrayList<>();
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","2","175"));
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","3","175"));
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","1","175"));
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","5","175"));
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","7","175"));
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","7","175"));
        cartRVModelList.add(new CartRVModel(R.drawable.royal_cafe,"Basket Chaat","7","175"));

        cartRVAdapter=new CartRVAdapter(cartRVModelList,this);
        cartRV.setAdapter(cartRVAdapter);
        cartRV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        cartRV.setNestedScrollingEnabled(false);
        calculateTotal();
        setCartDetails();
    }

    private void calculateTotal() {
        for(int i=0;i<cartRVModelList.size();++i){
            CART_TOTAL+=Double.valueOf(cartRVModelList.get(i).getTotal());
        }
    }

    @Override
    public void addItem(int position) {
//        Called from interface in CartRVAdapter that add button is clicked so update quantity
        cartRVModelList.get(position).setQuantity(String.valueOf(Integer.valueOf(cartRVModelList.get(position).getQuantity())+1));
//        Also update cart total
        CART_TOTAL+=Double.valueOf(cartRVModelList.get(position).getPrice());
        setCartDetails();
        cartRVAdapter.notifyItemChanged(position);
    }

    private void setCartDetails() {
        double GRAND_TOTAL=CART_TOTAL+(CART_TOTAL*CART_TAX)+DELIVERY_CHARGE;
        cartTax.setText("Rs."+String.valueOf(CART_TAX));
        cartTotal.setText("Rs."+String.valueOf(CART_TOTAL));
        cartDeliveryCharge.setText("Rs."+String.valueOf(DELIVERY_CHARGE));
        cartGrandTotal.setText("Rs."+String.valueOf(GRAND_TOTAL));


    }

    @Override
    public void subItem(int position) {
        //        Called from interface in CartRVAdapter that subtract button is clicked so update quantity
        cartRVModelList.get(position).setQuantity(String.valueOf(Integer.valueOf(cartRVModelList.get(position).getQuantity())-1));
//        Also update cart total
        CART_TOTAL-=Double.valueOf(cartRVModelList.get(position).getPrice());
        setCartDetails();
        if(cartRVModelList.get(position).getQuantity().equals("0")){
            cartRVModelList.remove(position);
            cartRVAdapter.notifyItemRemoved(position);
        }else{
            cartRVAdapter.notifyItemChanged(position);
        }

    }
}