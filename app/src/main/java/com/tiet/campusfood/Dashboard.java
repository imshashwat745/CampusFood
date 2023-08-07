package com.tiet.campusfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Button logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navigationView=findViewById(R.id.dashboard_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(this );
        navigationView.setSelectedItemId(R.id.navigation_home);
        loadDashboardFragment(new DashboardHomeFragment());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Fragment fragment=new DashboardHomeFragment();;
        if(id==R.id.navigation_orders){
            fragment=new DashboardUserOrdersFragment();
        }

        return loadDashboardFragment(fragment);
    }
    private boolean loadDashboardFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frame,fragment).commit();
            return true;
        }
        return false;
    }


}