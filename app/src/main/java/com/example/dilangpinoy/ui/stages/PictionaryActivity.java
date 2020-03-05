package com.example.dilangpinoy.ui.stages;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.PictionaryLevel;
import com.example.dilangpinoy.ui.stages.PictionaryActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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

public class PictionaryActivity extends MainStageActivity {


    private LinearLayout[] choices;
    private ImageView[] images;
    private TextView[] imageTexts;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    TextView lvldesc;



    TextView instruction;
    LinearLayout levelsbar;
    TextView titlebar;
    TextView currentLeveltext;


    public SoundPool soundPool;

    int correctchoice;
    int wrongchoice;
    int buttonclickpair;
    int kuyawil;
    int correct;
    int wrong;



    private TextView question;


    private Integer[] options = {0, 1, 2};


    private List<PictionaryLevel> pictionaryLevels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictionary);
        mContext = this;

        init();


        choices = new LinearLayout[]{findViewById(R.id.photo_choice_1), findViewById(R.id.photo_choice_2), findViewById(R.id.photo_choice_3)};
        images = new ImageView[]{findViewById(R.id.image_1), findViewById(R.id.image_2), findViewById(R.id.image_3)};
        imageTexts = new TextView[]{findViewById(R.id.image_text_1), findViewById(R.id.image_text_2), findViewById(R.id.image_text_3)};

        question = findViewById(R.id.level_description);


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
        lvldesc=(TextView) findViewById(R.id.level_description);

        Animation lvlbar = AnimationUtils.loadAnimation(PictionaryActivity.this, R.anim.bounce);
        Animation titlebarx = AnimationUtils.loadAnimation(PictionaryActivity.this, R.anim.slide_in_bottom);
        Animation inst = AnimationUtils.loadAnimation(PictionaryActivity.this, R.anim.fadein);
        Animation lvldsc = AnimationUtils.loadAnimation(PictionaryActivity.this, R.anim.fadein2);

        levelsbar.startAnimation(lvlbar);
        titlebar.startAnimation(titlebarx);








        initLevel(level);


    }


    public void initLevel(int level) {

        suffleArray(options);


        enableAllButtons();

        img1=(ImageView) findViewById(R.id.image_1);
        img2=(ImageView) findViewById(R.id.image_2);
        img3=(ImageView) findViewById(R.id.image_3);
        currentLeveltext=(TextView) findViewById(R.id.stage_text);

        Animation img1anim = AnimationUtils.loadAnimation(PictionaryActivity.this, R.anim.pop_up_in);
        Animation currentstage = AnimationUtils.loadAnimation(PictionaryActivity.this, R.anim.blink_anim);
        img1.startAnimation(img1anim);
        img2.startAnimation(img1anim);
        img3.startAnimation(img1anim);
        currentLevelText.startAnimation(currentstage);


        question.setText(pictionaryLevels.get(level).getQuestion());


        for (int i = 0; i < choices.length; i++) {
            final int finalI = i;
            String imageFilePath = "file:///android_asset/images/";
            switch (options[i]) {
                case 0:
                    Picasso.get().load(imageFilePath + pictionaryLevels.get(level).getImages().get(0).getImagePath()).resize(200, 200).centerCrop().into(images[i]);
                    imageTexts[i].setText(pictionaryLevels.get(level).getImages().get(0).getImageText());
                    break;
                case 1:
                    Picasso.get().load(imageFilePath + pictionaryLevels.get(level).getImages().get(1).getImagePath()).resize(200, 200).centerCrop().into(images[i]);
                    imageTexts[i].setText(pictionaryLevels.get(level).getImages().get(1).getImageText());
                    break;
                case 2:
                    Picasso.get().load(imageFilePath + pictionaryLevels.get(level).getImages().get(2).getImagePath()).resize(200, 200).centerCrop().into(images[i]);
                    imageTexts[i].setText(pictionaryLevels.get(level).getImages().get(2).getImageText());
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
            imageTexts[i].setTextColor(Color.WHITE);
        }

    }


    public void getStageLevels(String stageFile) {
        pictionaryLevels = readFileWithPictures(mContext, stageFile);


        Collections.shuffle(pictionaryLevels);
    }


    public List<PictionaryLevel> readFileWithPictures(Context context, String stageFile) {
        List<PictionaryLevel> picturesList = new ArrayList<PictionaryLevel>();
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
                picturesList.add(gson.fromJson(pairs.getString(i), PictionaryLevel.class));


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return picturesList;
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
                imageTexts[i].setTextColor(mContext.getResources().getColor(R.color.level_succeed_color));


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
            imageTexts[i].setTextColor(mContext.getResources().getColor(R.color.level_succeed_color));
            onCorrectAnswer();
        } else {
            imageTexts[i].setTextColor(mContext.getResources().getColor(R.color.level_failed_color));
            onWrongAnswer();
        }

    }

}
