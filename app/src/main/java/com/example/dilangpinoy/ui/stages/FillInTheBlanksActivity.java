package com.example.dilangpinoy.ui.stages;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.BlanksLevel;
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

public class FillInTheBlanksActivity extends MainStageActivity {

    //Button types
    final int CHOICE_NORMAL = R.drawable.choice_normal;
    final int CHOICE_FALSE = R.drawable.choice_false;
    final int CHOICE_CORRECT = R.drawable.choice_correct;

    private Button[] choices;
    private TextView question;

    public SoundPool soundPool;

    int correctchoice;
    int wrongchoice;
    int buttonclickpair;
    int kuyawil;
    int correct;
    int wrong;


    private Integer[] options = {0, 1, 2};
    private List<BlanksLevel> blankLevels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blanks);


        mContext = this;
        init();
        choices = new Button[]{findViewById(R.id.btn_choice_1), findViewById(R.id.btn_choice_2), findViewById(R.id.btn_choice_3)};

        question = findViewById(R.id.textView_question);


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


        initLevel(level);


    }


    public void getStageLevels(String stageFile) {
        blankLevels = readFileWithAnswers(mContext, stageFile);
        Collections.shuffle(blankLevels);
    }


    public void initLevel(int level) {

        //Shuffle the answers
        suffleArray(options);


        enableAllButtons();


        question.setText(blankLevels.get(level).getQuestion());


        //Set up the buttons
        for (int i = 0; i < choices.length; i++) {
            final int finalI = i;

            switch (options[i]) {
                case 0:
                    choices[i].setText(blankLevels.get(level).getCorrectAnswer());
                    break;
                case 1:
                    choices[i].setText(blankLevels.get(level).getWrongAnswer1());
                    break;
                case 2:
                    choices[i].setText(blankLevels.get(level).getWrongAnswer2());
                    break;
            }


            choices[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonPress(finalI);

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

        for (int i = 0; i < choices.length; i++)
            choices[i].setEnabled(false);
    }

    public void enableAllButtons() {

        for (int i = 0; i < choices.length; i++) {
            choices[i].setEnabled(true);
            choices[i].setBackgroundResource(CHOICE_NORMAL);
        }

    }


    public List<BlanksLevel> readFileWithAnswers(Context context, String stageFile) {
        List<BlanksLevel> phrasesList = new ArrayList<BlanksLevel>();
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
                phrasesList.add(gson.fromJson(pairs.getString(i), BlanksLevel.class));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return phrasesList;
    }


    public void onCorrectAnswer() {
        soundPool.play(correctchoice, 1, 1, 1, 0, 1);
        disableAllButtons();
        levelComplete(false);
    }

    public void showCorrectAnswer() {
        disableAllButtons();
        //Find and show the correct answer
        for (int i = 0; i < options.length; i++) {
            if (options[i] == 0) {
                choices[i].setBackgroundResource(CHOICE_CORRECT);
            }
        }
    }


    public void onWrongAnswer() {
        soundPool.play(wrongchoice, 1, 1, 1, 0, 1);
        showCorrectAnswer();
        levelComplete(true);
    }


    public void onButtonPress(int i) {


        if (options[i] == 0) {
            choices[i].setBackgroundResource(CHOICE_CORRECT);
            onCorrectAnswer();
        } else {
            choices[i].setBackgroundResource(CHOICE_FALSE);
            onWrongAnswer();
        }

    }

}
