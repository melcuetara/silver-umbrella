package com.example.rentaland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.rentaland.ui.signup.SignUpActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartLogoActivity extends AppCompatActivity {
    ImageView img1, img2;
    Animation top, bottom;
    ProgressBar pb;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_logo);
        img1 =(ImageView)findViewById(R.id.img_piclogo);
        img2 =(ImageView)findViewById(R.id.img_titlelogo);

        top = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_piclogo);
        bottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_titlelogo);

        img1.setAnimation(top);
        img2.setAnimation(bottom);

        prog();
    }

    public void prog(){
        pb = (ProgressBar)findViewById(R.id.pb_main);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);

                if(counter == 100)
                    t.cancel();
                if(counter == 100) {
                    Intent uitesting = new Intent(StartLogoActivity.this, SignUpActivity.class);
                    startActivity(uitesting);
                }
            }
        };
        t.schedule(tt,0,30);
    }
}