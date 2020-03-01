package com.example.dilangpinoy.ui;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.services.MusicService;

public class BaseActivity extends AppCompatActivity {


    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    Intent objIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mServ!=null){
            mServ.pauseMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_up_in,R.anim.pop_up_out);
    }


    public void onChangeActivity(){
        overridePendingTransition(R.anim.pop_up_in,R.anim.pop_up_out);
    }
}
