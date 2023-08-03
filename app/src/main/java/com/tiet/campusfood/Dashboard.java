package com.tiet.campusfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Button logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navigationView=findViewById(R.id.dashboard_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(this );
        navigationView.setSelectedItemId(R.id.Home);
        loadDashboardFragment(new DashboardHomeFragment());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment=null;
//        switch(item.getItemId()){
//            case R.id.Home:
//                fragment=new
//        }
        return loadDashboardFragment(new DashboardHomeFragment());
    }
    private boolean loadDashboardFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frame,fragment).commit();
            return true;
        }
        return false;
    }
}