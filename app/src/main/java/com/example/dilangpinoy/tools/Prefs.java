package com.example.dilangpinoy.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dilangpinoy.objects.LeaderboardItem;
import com.example.dilangpinoy.ui.StagesActivity;

public class Prefs {
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;

    public static void initPreferences(Context mContext){

        if(sharedPref==null )
        sharedPref = mContext.getSharedPreferences("online", Context.MODE_PRIVATE);
        if(editor==null)
        editor = sharedPref.edit();
    }



    public static void saveOpponent(Context mContext, LeaderboardItem item){
        initPreferences( mContext);
        editor.putLong("scoreToBeat",item.getScore());
        editor.putString("nameToBeat",item.getName());
        editor.putInt("positionToBeat",item.getPosition());
        editor.commit();
    }

    public static LeaderboardItem getOpponent(Context mContext){
        initPreferences( mContext);
        LeaderboardItem item=new LeaderboardItem();

        item.setName(sharedPref.getString("nameToBeat",""));
        item.setScore(sharedPref.getLong("scoreToBeat",0));
        item.setPosition(sharedPref.getInt("positionToBeat",0));

        return item;
    }

    public static void setOnline(Context mContext,boolean online){
        initPreferences( mContext);
        editor.putBoolean("onlineMode",online);
        editor.commit();
    }

    public static boolean getOnline(Context mContext){
        initPreferences( mContext);
        return sharedPref.getBoolean("onlineMode",false);
    }

    public static void saveScore(Context mContext,long score){
        initPreferences( mContext);
        editor.putLong("onlineScore",score);
        editor.commit();
    }

    public static long getScore(Context mContext){
        initPreferences( mContext);
        return sharedPref.getLong("onlineScore",0);
    }
}
