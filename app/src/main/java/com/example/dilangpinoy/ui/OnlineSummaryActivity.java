package com.example.dilangpinoy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.tools.JSONTools;
import com.example.dilangpinoy.tools.Prefs;

import java.util.ArrayList;
import java.util.List;

public class OnlineSummaryActivity extends  BaseActivity {

    final int[] MULTIPLIERS={100,500,1000,10000,50000,99999};


    private Button nextBtn;
    private TextView correctAnswersText,wrongAnswersText;
    private TextView stageMultiplier1,stageMultiplier2;
    private TextView stageTotal;
    private TextView grandTotal;

    private TextView stageName;




    private int correctAnswers;
    private int wrongAnswers;
    private int stageMultiplier;
    private long stageScore;
    private long totalScore;


    int currentStage;
    private Stage nextStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_results);

        nextBtn=findViewById(R.id.next_btn);
        correctAnswersText=findViewById(R.id.online_correct_answers);
        wrongAnswersText=findViewById(R.id.online_wrong_answers);
        stageMultiplier1=findViewById(R.id.online_stage_multiplier);
        stageMultiplier2=findViewById(R.id.online_stage_multiplier2);
        stageTotal=findViewById(R.id.online_stage_total);
        grandTotal=findViewById(R.id.online_total_score);

        stageName=findViewById(R.id.stage_name_tv);

        Intent intent = getIntent();

        correctAnswers=intent.getIntExtra("correct",0);
        wrongAnswers=intent.getIntExtra("wrong",0);
        stageName.setText(intent.getStringExtra("stageName"));
        currentStage=intent.getIntExtra("currentStage",0);






        correctAnswersText.setText(getString(R.string.correct_text)+" "+correctAnswers);
        wrongAnswersText.setText(getString(R.string.mistakes_text)+" "+wrongAnswers);

        stageMultiplier=MULTIPLIERS[currentStage];
        stageMultiplier1.setText("Stage Multiplier: x"+stageMultiplier);
        stageMultiplier2.setText("Stage Multiplier: x"+stageMultiplier);

        stageScore=calculateScore(stageMultiplier,wrongAnswers,correctAnswers);

        totalScore= Prefs.getScore(OnlineSummaryActivity.this);
        totalScore+=stageScore;
        stageTotal.setText("Stage Total: "+stageScore);
        grandTotal.setText("Grand Total Score:\n"+totalScore);

        //save total score
        Prefs.saveScore(OnlineSummaryActivity.this,totalScore);


        if(currentStage<5) {
            nextStage = JSONTools.getStage(OnlineSummaryActivity.this, currentStage+1);

        }else{
            nextBtn.setText("Submit Score");
        }


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(nextStage!=null){
                    Intent intent=new Intent(OnlineSummaryActivity.this, TriviaActivity.class);
                    intent.putExtra("selectedStage",nextStage);
                    intent.putExtra("stageNumber",currentStage+1);
                    startActivity(intent);
                    onChangeActivity();

                }else{
                    Intent intent=new Intent(OnlineSummaryActivity.this, OnlineSubmitActivity.class);
                    startActivity(intent);
                    onChangeActivity();
                }

                finish();
            }
        });

    }

    public long calculateScore(int stageMultiplier,int wrongAnswers,int correctAnswers){
        long stageScore=(stageMultiplier*correctAnswers)-(stageMultiplier*wrongAnswers);


        return stageScore;
    }


}
