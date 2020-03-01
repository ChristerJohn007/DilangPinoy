package com.example.dilangpinoy.tools;

import android.content.Context;

import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.objects.Trivia;
import com.example.dilangpinoy.ui.LeaderboardsActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public  class JSONTools {

    public static void readFileWithStages(Context context, List<Stage> stageList){
        stageList.clear();
        String json = null;
        try {
            InputStream is = context.getAssets().open("stages.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();

        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray stages=obj.getJSONArray("stages");
            Gson gson = new Gson();
            for(int i=0;i<stages.length();i++){
                stageList.add(gson.fromJson(stages.getString(i),Stage.class));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    public static Stage getStage(Context context,int number){
        List<Stage> stages=new ArrayList<>();
        JSONTools.readFileWithStages(context,stages);
        return stages.get(number);
    }

    public static void readFileWithTrivia(Context context, List<Trivia> triviaList){
        triviaList.clear();
        String json = null;
        try {
            InputStream is = context.getAssets().open("trivia.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();

        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray trivias=obj.getJSONArray("trivia");
            Gson gson = new Gson();
            for(int i=0;i<trivias.length();i++){
                triviaList.add(gson.fromJson(trivias.getString(i),Trivia.class));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
