package com.example.dilangpinoy.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dilangpinoy.R;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends BaseActivity {

    private Button playBtn;
    private Button easterEgg1;
    ImageView logo;
    Button playbutton,leaderboardsBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn=findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StagesActivity.class);
                startActivity(intent);
                onChangeActivity();

            }
        });

        leaderboardsBtn=findViewById(R.id.leaderboards_btn);
        leaderboardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderboardsActivity.class);
                startActivity(intent);
                onChangeActivity();
            }
        });


        logo=(ImageView)findViewById(R.id.mainlogo);
        playbutton=(Button)findViewById(R.id.play_btn);

        Animation logoanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin);
        logo.startAnimation(logoanim);

        Animation buttonanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        logo.startAnimation(buttonanim);

    }

    public void viewEaster1 (View view) {
        easterEgg1=findViewById(R.id.easterEgg1);
        setContentView(R.layout.easter_egg1);

    }



}
