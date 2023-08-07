package com.tiet.campusfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Payment extends AppCompatActivity {

    private Spinner hostelSpinner;
    private FirebaseFirestore firebaseFirestore;
    private Button placeOrderBtn;
    private String phno,restrauntId;
    private FirebaseAuth firebaseAuth;
    private  Map<String,Object> data;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        phno=firebaseAuth.getCurrentUser().getPhoneNumber();
        placeOrderBtn =findViewById(R.id.place_order_btn);
        initHostelDropdown();
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });






    }

    private void placeOrder() {
        data=new HashMap<>();

//        First get Cart Details
        firebaseFirestore.collection("users").document(phno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    restrauntId=snapshot.getString("cart");
//                    Now Add this data to db for ordering
                    Map<String,Object> curr=snapshot.getData();
                    for(Map.Entry<String,Object> entry:curr.entrySet()){
                        data.put(entry.getKey(),entry.getValue());
                    }
                    data.put("phno",phno);
                    data.put("delivered","false");
                    String hostel=hostelSpinner.getSelectedItem().toString();
                    data.put("hostel",hostel);
                    data.put("total",getIntent().getStringExtra("Cost"));
                    addToDb();
                }else{
                    Toast.makeText(Payment.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToDb() {
//        Add to User DB
//        String customDocumentId = UUID.randomUUID().toString();
        firebaseFirestore.collection("users/" + phno + "/orders").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Payment.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        //        Add to restraunt DB
        firebaseFirestore.collection("restraunts/" + restrauntId + "/orders").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Payment.this, "Order Placed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Payment.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initHostelDropdown() {
        hostelSpinner=findViewById(R.id.hostel_dropdown);
        String items[]=getResources().getStringArray(R.array.hostel_dropdown);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostelSpinner.setAdapter(adapter);
    }
}