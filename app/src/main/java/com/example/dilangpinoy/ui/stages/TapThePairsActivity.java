package com.example.dilangpinoy.ui.stages;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.PairLevel;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.AudioManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TapThePairsActivity extends MainStageActivity {


    private Button[] choices;
    MediaPlayer mp;
    public SoundPool soundPool;


    TextView instruction;
    LinearLayout levelsbar;
    TextView titlebar;
    TextView currentLeveltext;


    final int CHOICE_NORMAL = R.drawable.choice_normal;
    final int CHOICE_SELECTED = R.drawable.choice_selected;
    final int CHOICE_FALSE = R.drawable.choice_false;
    final int CHOICE_CORRECT = R.drawable.choice_correct;

    private Integer[] leftOptions = {0, 1, 2, 3};
    private Integer[] rightOptions = {0, 1, 2, 3};


    private List<PairLevel> pairLevels;
    private int selected = -1;

    int correctchoice;
    int wrongchoice;
    int buttonclickpair;
    int kuyawil;
    int correct;
    int wrong;


    private int correctAnswers = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairs);


        mContext = this;
        init();
        choices = new Button[]{findViewById(R.id.choice1), findViewById(R.id.choice2), findViewById(R.id.choice3), findViewById(R.id.choice4),
                findViewById(R.id.choice5), findViewById(R.id.choice6), findViewById(R.id.choice7), findViewById(R.id.choice8)};



        initLevel(level);

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

        correctchoice = soundPool.load(getApplicationContext(), R.raw.correctchoice,1);
        wrongchoice = soundPool.load (getApplicationContext(), R.raw.wrongchoice,1);
        buttonclickpair = soundPool.load(getApplicationContext(), R.raw.buttonclickpair, 1);
        kuyawil = soundPool.load(getApplicationContext(), R.raw.wowowinhappy, 1);
        wrong = soundPool.load(getApplicationContext(), R.raw.wrong, 1);
        correct = soundPool.load(getApplicationContext(), R.raw.correct, 1);


        levelsbar=(LinearLayout) findViewById(R.id.linearLayout4);
        titlebar=(TextView) findViewById(R.id.stage_text);
        instruction=(TextView) findViewById(R.id.stage_instructions);

        Animation lvlbar = AnimationUtils.loadAnimation(TapThePairsActivity.this, R.anim.bounce);
        Animation titlebarx = AnimationUtils.loadAnimation(TapThePairsActivity.this, R.anim.slide_in_bottom);
        Animation inst = AnimationUtils.loadAnimation(TapThePairsActivity.this, R.anim.fadein);
        Animation lvldsc = AnimationUtils.loadAnimation(TapThePairsActivity.this, R.anim.fadein2);

        levelsbar.startAnimation(lvlbar);
        titlebar.startAnimation(titlebarx);





    }

    public void getStageLevels(String stageFile) {
        pairLevels = readFileWithPairs(mContext, stageFile);
        Collections.shuffle(pairLevels);
    }


    public void initLevel(int level) {


        suffleArray(leftOptions);
        suffleArray(rightOptions);
        correctAnswers = 0;

        currentLeveltext=(TextView) findViewById(R.id.stage_text);
        Animation currentstage = AnimationUtils.loadAnimation(TapThePairsActivity.this, R.anim.blink_anim);
        currentLevelText.startAnimation(currentstage);


        enableAllButtons();


        for (int i = 0; i < choices.length; i++) {
            final int finalI = i;

            if (i < 4)

                choices[i].setText(pairLevels.get(level).getPairs().get(leftOptions[i]).getOption1());
            else
                choices[i].setText(pairLevels.get(level).getPairs().get(rightOptions[i - 4]).getOption2());

            choices[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonPress(finalI);
                    soundPool.play(buttonclickpair, 1, 1, 1, 0, 1);

                }
            });
        }
    }

    public void suffleArray(Integer[] intArray) {
        List<Integer> intList = Arrays.asList(intArray);

        Collections.shuffle(intList);

        intList.toArray(intArray);
    }


    public void disableAllButtons() {
        selected = -1;
        for (int i = 0; i < choices.length; i++)
            choices[i].setEnabled(false);
    }

    public void enableAllButtons() {
        selected = -1;
        for (int i = 0; i < choices.length; i++) {
            choices[i].setEnabled(true);
            choices[i].setBackgroundResource(CHOICE_NORMAL);
        }

    }


    public List<PairLevel> readFileWithPairs(Context context, String stageFile) {
        List<PairLevel> pairsList = new ArrayList<PairLevel>();
        String json = null;
        try {
            InputStream is = context.getAssets().open(stageFile);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray pairs = obj.getJSONArray("levels");
            Gson gson = new Gson();
            for (int i = 0; i < pairs.length(); i++) {
                pairsList.add(gson.fromJson(pairs.getString(i), PairLevel.class));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return pairsList;
    }


    public void onCorrectAnswer(int choice1, int choice2) {
        correctAnswers++;
        choices[choice1].setBackgroundResource(CHOICE_CORRECT);
        choices[choice2].setBackgroundResource(CHOICE_CORRECT);
        choices[choice1].setEnabled(false);
        choices[choice2].setEnabled(false);
        selected = -1;

        soundPool.play(correctchoice, 1, 1, 1, 0, 1);

        if (correctAnswers >= 4) {
            levelComplete(false);
        }



    }


    public void onWrongAnswer(int choice1, int choice2) {
        choices[choice1].setBackgroundResource(CHOICE_FALSE);
        choices[choice2].setBackgroundResource(CHOICE_FALSE);
        disableAllButtons();

        soundPool.play(wrongchoice, 1, 1, 1, 0, 1);


        levelReset();


    }

    public void resetTheLevel(){

        enableAllButtons();
        correctAnswers = 0;
    }

    public void onButtonPress(int i) {


        if (selected == -1) {
            selected = i;
            choices[i].setBackgroundResource(CHOICE_SELECTED);
        } else if ((selected < 4 && i < 4) || selected >= 4 && i >= 4) {
            if (selected == i) {
                choices[selected].setBackgroundResource(CHOICE_NORMAL);
                selected = -1;
            } else {
                choices[selected].setBackgroundResource(CHOICE_NORMAL);
                selected = i;
                choices[i].setBackgroundResource(CHOICE_SELECTED);
            }

        } else {
            if (selected < 4) {

                if (leftOptions[selected] == rightOptions[i - 4]) {
                    onCorrectAnswer(selected, i);
                } else {
                    onWrongAnswer(selected, i);
                }
            } else {
                if (leftOptions[i] == rightOptions[selected - 4]) {
                    onCorrectAnswer(selected, i);
                } else {
                    onWrongAnswer(selected, i);
                }
            }
        }

    }

}
