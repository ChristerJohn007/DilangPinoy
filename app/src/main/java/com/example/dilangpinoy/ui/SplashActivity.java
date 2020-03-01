package com.example.dilangpinoy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.media.MediaPlayer;

import com.example.dilangpinoy.R;

public class SplashActivity extends Activity {
    private static boolean splashLoaded = false;

    LinearLayout hut;
    TextView cjg;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!splashLoaded) {
            setContentView(R.layout.splash);
            int secondsDelayed = 5;
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 500);

            splashLoaded = true;
        }
        else {
            Intent goToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);

            finish();
        }



        cjg=(TextView)findViewById(R.id.prod);
        hut=(LinearLayout)findViewById(R.id.image1container);

        Animation logoanim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoomin);
        hut.startAnimation(logoanim);


        Animation prodanim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.bounce);
        cjg.startAnimation(prodanim);
    }


}
