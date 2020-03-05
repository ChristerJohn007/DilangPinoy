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
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

public class MainActivity extends BaseActivity {

    private Button playBtn;
    private Button easterEgg1;
    ImageView logo;
    Button playbutton,leaderboardsBtn;

//BGanimation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView backgroundOne = (ImageView) findViewById(R.id.scrollingbg1);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.scrollingbg2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(80000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();





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
