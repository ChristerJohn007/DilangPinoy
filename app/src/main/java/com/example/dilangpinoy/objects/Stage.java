package com.example.dilangpinoy.objects;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.dilangpinoy.ui.stages.AnalogyActivity;
import com.example.dilangpinoy.ui.stages.AncientActivity;
import com.example.dilangpinoy.ui.stages.FillInTheBlanksActivity;
import com.example.dilangpinoy.ui.stages.GuessThePhraseActivity;
import com.example.dilangpinoy.ui.stages.HardcorePairsActivity;
import com.example.dilangpinoy.ui.stages.PictionaryActivity;
import com.example.dilangpinoy.ui.stages.TapThePairsActivity;

import java.io.Serializable;

public class Stage implements Serializable {

    private String title;
    private String subtitle;
    private String imagePath;
    private String type;
    private String stageFile;
    private int mistakesAllowed;
    private boolean enabled = true;
    private int time;





    public Stage(String title, String subtitle, String imagePath, String type, String stageFile, int mistakesAllowed, boolean enabled, int time) {
        this.title = title;
        this.subtitle = subtitle;
        this.imagePath = imagePath;
        this.type = type;
        this.stageFile = stageFile;
        this.mistakesAllowed = mistakesAllowed;
        this.enabled = enabled;
        this.time = time;
    }


    public int getMistakesAllowed() {
        return mistakesAllowed;
    }

    public void setMistakesAllowed(int mistakesAllowed) {
        this.mistakesAllowed = mistakesAllowed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String sutitle) {
        this.subtitle = sutitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStageFile() {
        return stageFile;
    }

    public void setStageFile(String stageFile) {
        this.stageFile = stageFile;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    //Check the stage's type and calls the corresponding activity.
    public static Intent getStageIntent(Stage stage, Context mContext, int stageNumber) {
        Intent intent = null;
        if (stage.getType().equals("Tap the Pairs")) {
            intent = new Intent(mContext, TapThePairsActivity.class);


        } else if (stage.getType().equals("Guess the Phrase")) {
            intent = new Intent(mContext, GuessThePhraseActivity.class);

        } else if (stage.getType().equals("Pictionary")) {
            intent = new Intent(mContext, PictionaryActivity.class);
        } else if (stage.getType().equals("Fill in the Blanks")) {
            intent = new Intent(mContext, FillInTheBlanksActivity.class);
        } else if (stage.getType().equals("Analogy")) {
            intent = new Intent(mContext, AnalogyActivity.class);
        } else if (stage.getType().equals("Ancient")) {
            intent = new Intent(mContext, AncientActivity.class);
        } else if (stage.getType().equals("Hardcore Pairs")) {
            intent = new Intent(mContext, HardcorePairsActivity.class);
        }

        if (intent != null) {
            intent.putExtra("stageFile", stage.stageFile);
            intent.putExtra("stageName", stage.title + stage.subtitle);
            intent.putExtra("stageNumber", stageNumber);
            intent.putExtra("mistakes", stage.mistakesAllowed);
            intent.putExtra("time", stage.getTime());
        }
        return intent;
    }




}
