package com.example.rentaland.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentaland.R;
import com.example.rentaland.ui.signin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class adminDashboard extends AppCompatActivity {
    Button registerGovBtn,viewReportBtn,ManageUserBtn,viewFeedbackBtn;
    ImageButton logoutbtn;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        logoutbtn = (ImageButton) findViewById(R.id.logoutGov);
        registerGovBtn = (Button) findViewById(R.id.btn_registerLocalGov);
        viewReportBtn = (Button) findViewById(R.id.btn_view_reportAdmin);
        ManageUserBtn = (Button) findViewById(R.id.btn_manage_farmerAdmin);
        viewFeedbackBtn = (Button) findViewById(R.id.btn_view_feedbackAdmin);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        viewReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReport();
            }
        });

       registerGovBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegGov();
            }
        });
       viewFeedbackBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openFeedback();
           }
       });
       ManageUserBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openManageReport();
           }
       });
    }
    public void openFeedback()
    {
        Intent intent = new Intent(this, viewFeedback.class);
        startActivity(intent);
    }
    public void openManageReport()
    {
        Intent intent = new Intent(this,manageUser.class);
        startActivity(intent);
    }
    public void openReport()
    {
        Intent intent = new Intent(this,viewReportAdmin.class);
        startActivity(intent);
    }
    public void openRegGov()
    {
        Intent intent = new Intent(this, registerGovernment.class);
        startActivity(intent);
    }
    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

