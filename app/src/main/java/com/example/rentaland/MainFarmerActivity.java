package com.example.rentaland;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.rentaland.ui.signin.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.example.rentaland.databinding.ActivityMainFarmerBinding;

import com.google.firebase.auth.FirebaseAuth;

public class MainFarmerActivity extends AppCompatActivity {

    private ActivityMainFarmerBinding binding;
    private FirebaseAuth mAuth;
    private Bundle mBundle;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainFarmerBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();

        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMainFarmer.toolbarFarmer);
        binding.appBarMainFarmer.fabFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mAuth.signOut();
                finish();
                startLogin();
            }
        });

        DrawerLayout drawer = binding.drawerLayoutFarmer;
        NavigationView navigationView = binding.navViewFarmer;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_notification, R.id.nav_booklist, R.id.nav_message)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_farmer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_farmer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void startLogin() {
        Intent intentLogin = new Intent(MainFarmerActivity.this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }
}