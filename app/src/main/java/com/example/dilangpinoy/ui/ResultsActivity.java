package com.example.dilangpinoy.ui;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.AudioManager;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.tools.JSONTools;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends  BaseActivity {

    private Button nextBtn;
    private TextView correctAnswers,wrongAnswers,status;

    public SoundPool soundPool;

    int kuyawil;


    int nextStageNumber;
    private boolean  levelCompleted=false;
    private Stage nextStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        nextBtn=findViewById(R.id.next_btn);
        correctAnswers=findViewById(R.id.correct_answers);
        wrongAnswers=findViewById(R.id.wrong_answers);
        status=findViewById(R.id.status);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        kuyawil = soundPool.load(getApplicationContext(), R.raw.wowowinhappy, 1);



//        Intent intent = getIntent();
//        int correct = intent.getIntExtra("correct",0);
//        int wrong = intent.getIntExtra("wrong",0);
//        int mistakesAllowed=intent.getIntExtra("mistakesAllowed",0);
//        nextStageNumber=intent.getIntExtra("currentStage",0)+1;
//
//
//        correctAnswers.setText(getString(R.string.correct_text)+" "+correct);
//        wrongAnswers.setText(getString(R.string.wrong_text)+" "+wrong);
//
//
//        if(wrong<=mistakesAllowed){
//            levelCompleted=true;
//            nextBtn.setText(getString(R.string.button_next_stage));
//            status.setText(getString(R.string.stage_complete));
//            MediaPlayer mpx = MediaPlayer.create(getApplicationContext(), R.raw.wowowinhappy);
//            mpx.start();
//        }else{
//            levelCompleted=false;
//            nextBtn.setText(getString(R.string.button_retry));
//            status.setText(getString(R.string.stage_failed));
//        }
//
//
//        //Get the next level
//        List<Stage> stageList;
//        stageList=new ArrayList<>();
//        JSONTools.readFileWithStages(ResultsActivity.this,stageList);
//
//        if(stageList.size()>nextStageNumber){
//            nextStage=stageList.get(nextStageNumber);
//        }


        Intent intent = getIntent();
        int correct = intent.getIntExtra("correct",0);
        int wrong = intent.getIntExtra("wrong",0);
        int mistakesAllowed=intent.getIntExtra("mistakesAllowed",0);
        nextStageNumber=intent.getIntExtra("currentStage",0)+1;



        correctAnswers.setText(getString(R.string.correct_text)+" "+correct);
        wrongAnswers.setText(getString(R.string.wrong_text)+" "+wrong);


        if(wrong<=mistakesAllowed){
            levelCompleted=true;
            nextBtn.setText(getString(R.string.button_next_stage));
            status.setText(getString(R.string.stage_complete));
            MediaPlayer mpx = MediaPlayer.create(getApplicationContext(), R.raw.wowowinhappy);
            mpx.start();
        }else{
            levelCompleted=false;
            nextBtn.setText(getString(R.string.button_retry));
            status.setText(getString(R.string.stage_failed));
            MediaPlayer mpx = MediaPlayer.create(getApplicationContext(), R.raw.wellgetem);
            mpx.start();
        }


        //Get the next level
        List<Stage> stageList;
        stageList=new ArrayList<>();
        JSONTools.readFileWithStages(ResultsActivity.this,stageList);

        if(levelCompleted){
            SharedPreferences sharedPref = ResultsActivity.this.getSharedPreferences("saves", Context.MODE_PRIVATE);
            //Go to next stage
            if(stageList.size()<=nextStageNumber){
               nextStageNumber=-1;
            }
        }else{
            //Replay the same stage
            nextStageNumber-=1;
        }









        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                   Intent intent = new Intent(ResultsActivity.this, StagesActivity.class);
                   intent.putExtra("selectedStage",nextStageNumber);
                   startActivity(intent);
                   onChangeActivity();




                finish();
            }
        });

    }


}
