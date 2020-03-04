package com.example.dilangpinoy.ui.stages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.tools.Prefs;
import com.example.dilangpinoy.ui.BaseActivity;
import com.example.dilangpinoy.ui.OnlineSummaryActivity;
import com.example.dilangpinoy.ui.ResultsActivity;

//This class has the main functionality that is needed from all the stages

abstract class MainStageActivity extends BaseActivity {

    //Each level's max time
    private int maxTime=61;

    //Standard views
    protected TextView currentLevelText;
    protected TextView[] levelsText;
    protected TextView timerText;
    protected int level=0;
    protected Context mContext;
    private Button backBtn;
    private TextView stageName;
    LinearLayout levelsbar;

    Handler handler = new Handler();

    protected int correctAnswers =0;

    private int time=maxTime;
    private int stageNumber;
    private int mistakesAllowed;
    private Runnable updateTime;
    private Runnable nextLevel;
    private Runnable resetLevel;
    private Runnable stageCompleted;

    private boolean paused=false,timeIsRunning=false;
    private boolean timeIsUp=false;

    MediaPlayer mp;

    protected void init(){
        mContext=this;

        //Get the data from the previous activity
        Intent intent = getIntent();
        String stageFile = intent.getStringExtra("stageFile");
        String stageName = intent.getStringExtra("stageName");
        stageNumber = intent.getIntExtra("stageNumber",0);
        mistakesAllowed = intent.getIntExtra("mistakes",0);
        maxTime=intent.getIntExtra("time",61);
        time=maxTime;


        //Get the data from the JSON file
        getStageLevels(stageFile);



        //Initialize the views
        this.stageName=findViewById(R.id.stage_text);
        this.stageName.setText(stageName);
        levelsText=new TextView[]{findViewById(R.id.tv_level_1),findViewById(R.id.tv_level_2),findViewById(R.id.tv_level_3),findViewById(R.id.tv_level_4),
                findViewById(R.id.tv_level_5),findViewById(R.id.tv_level_6),findViewById(R.id.tv_level_7),findViewById(R.id.tv_level_8),findViewById(R.id.tv_level_9),findViewById(R.id.tv_level_10)};

        timerText = findViewById(R.id.textView_timer);
        currentLevelText = findViewById(R.id.textView_current_level);
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        //Initialize the tasks
        updateTime = new Runnable() {
            @Override
            public void run() {

                timeIsRunning=true;
                // Do something here on the main thread
                updateTimer();
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000);
            }
        };

        nextLevel = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread


                nextLevel(level);
            }
        };

        stageCompleted = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                stageCompleted();
            }
        };


        resetLevel = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                resetTheLevel();
            }
        };

        handler.post(updateTime);

    }

    //Update the timer
    protected void updateTimer(){
        if(time>0 ) {
            timeIsUp=false;
            if (!paused)
                time--;
        }
        else if(!timeIsUp){

            timeIsUp=true;
            levelComplete(true);
            showCorrectAnswer();
            //show the correct answer
            //there is a bug
        }

        timerText.setText(time+"s");

    }

    @Override
    protected void onPause() {
        super.onPause();

        paused=true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        paused=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTime);
        handler.removeCallbacks(stageCompleted);
        handler.removeCallbacks(nextLevel);
    }

    //On level completion
    protected void levelComplete(boolean failed){
        timeIsRunning=false;
        handler.removeCallbacks(updateTime);

        //Change the text color
        if(failed){
            levelsText[level].setTextColor(mContext.getResources().getColor(R.color.level_failed_color));
        }else{
            correctAnswers++;
            levelsText[level].setTextColor(mContext.getResources().getColor(R.color.level_succeed_color));

        }


        level++;

        //If stage is finished end the game otherwise go to the next level
        if(level<10) {

            handler.postDelayed(nextLevel, 2000);


        }
        else {

            handler.postDelayed(stageCompleted, 2000);

        }
    }


    protected void levelReset(){

        handler.postDelayed(resetLevel, 1000);
    }

    //On next level
    private void nextLevel(int level){
        //Change the text and reset the timer




        currentLevelText.setText("Level "+(level+1)+" out of 10");
        initLevel(level);
        time=maxTime;

        if(!timeIsRunning)
        handler.post(updateTime);

    }

    protected void initLevel(int level){}

    protected void getStageLevels(String stageFile){};

    protected void showCorrectAnswer(){}

    protected void resetTheLevel(){}




    protected void stageCompleted(){



        //If the stage is completed unlock the next stage
        if(10-correctAnswers<=mistakesAllowed){
            SharedPreferences sharedPref = mContext.getSharedPreferences("saves",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            int completeStages=sharedPref.getInt("completeStages",0);
            if(stageNumber==completeStages) {
                editor.putInt("completeStages", stageNumber + 1);
                editor.commit();
            }

        }
        //Send the data to the Results activity
        Intent intent;

        //Check if it is in online mode
        if(Prefs.getOnline(mContext))
        intent = new Intent(MainStageActivity.this, OnlineSummaryActivity.class);
        else
        intent = new Intent(MainStageActivity.this, ResultsActivity.class);

        intent.putExtra("correct",correctAnswers);
        intent.putExtra("wrong",10-correctAnswers);
        intent.putExtra("mistakesAllowed",mistakesAllowed);
        intent.putExtra("currentStage",stageNumber);
        intent.putExtra("stageName",stageName.getText());
        startActivity(intent);
        onChangeActivity();
        finish();


    }


}
