package com.example.rentaland.ui.government;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentaland.R;
import com.example.rentaland.ui.signin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class governmentDashboard extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button viewFarmerBtn,viewProfileBtn;
    ImageButton logoutBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_dashboard);
        viewFarmerBtn = (Button) findViewById(R.id.btn_view_farmer_gov);
        viewProfileBtn =(Button)findViewById(R.id.btn_viewProfileGov);
        logoutBtn = (ImageButton) findViewById(R.id.logoutGov);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        viewProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });
        viewFarmerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewFarmer();
            }
        });
    }
    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openViewFarmer()
    {
        Intent intent = new Intent(this,viewFarmer.class);
        startActivity(intent);
    }
    public void openProfile()
    {
        Intent intent = new Intent(this,viewProfileGov.class);
        startActivity(intent);
    }
}
