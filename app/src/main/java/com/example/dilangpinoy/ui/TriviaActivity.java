package com.example.dilangpinoy.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.adapters.StagesListAdapter;
import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.objects.Trivia;
import com.example.dilangpinoy.tools.JSONTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TriviaActivity extends  BaseActivity {
    final String TRIVIA_IMAGE_PATH = "file:///android_asset/trivia_images/";
    TextView triviaText;
    ImageView triviaImage;
    ProgressBar progressBar;
    Button backBtn;

    int progress;
    Handler loadStage;

    Stage selectedStage;
    int stageNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        triviaText=findViewById(R.id.triviaText);
        progressBar=findViewById(R.id.loading_progress);
        triviaImage=findViewById(R.id.trivia_image);
        backBtn=findViewById(R.id.back_btn);
        progressBar.setMax(5000);


        Trivia randomTrivia=getRandomTrivia();
        triviaText.setText(randomTrivia.getText());
        Picasso.get().load(TRIVIA_IMAGE_PATH + randomTrivia.getImagePath()).into(triviaImage);



        Bundle extras=getIntent().getExtras();
         selectedStage=(Stage) extras.getSerializable("selectedStage");
         stageNumber=extras.getInt("stageNumber");



            loadStage=new Handler();
            loadStage.postDelayed(new Runnable() {
                public void run() {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    startActivity(Stage.getStageIntent(selectedStage, TriviaActivity.this, stageNumber));
                    onChangeActivity();
                    loadStage.removeCallbacksAndMessages(null);
                    finish();
                }
            }, 5000);



        updateProgress();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadStage.removeCallbacksAndMessages(null);
                finish();
            }
        });


        final ImageView backgroundOne = (ImageView) findViewById(R.id.scrollingbg1);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.scrollingbg2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100000L);
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


    }



    public Trivia getRandomTrivia(){
        List<Trivia> triviaList=new ArrayList<Trivia>();
        JSONTools.readFileWithTrivia(TriviaActivity.this,triviaList);
        Random rand = new Random();


        return triviaList.get(rand.nextInt(triviaList.size()));


    }

    public void updateProgress(){
        loadStage.postDelayed(new Runnable() {
            public void run() {
                progress+=200;
                progressBar.setProgress(progress);
                updateProgress();
            }
        }, 200);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadStage.removeCallbacksAndMessages(null);
    }


}