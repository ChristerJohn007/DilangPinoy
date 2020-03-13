package com.example.dilangpinoy.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.LeaderboardItem;
import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.tools.JSONTools;
import com.example.dilangpinoy.tools.Prefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OnlineSubmitActivity extends  BaseActivity {



    private DatabaseReference mDatabase;
    private Button submitBtn;
    private TextView grandTotalScore,targetScore;
    private TextView status;


    private EditText userName;
    private TextView enterNameText;



    private long totalScore;
    private LeaderboardItem opponentInfo;

    private boolean passedTheTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_submit);

        submitBtn=findViewById(R.id.submit_btn);

        grandTotalScore=findViewById(R.id.online_grand_total_score);
        targetScore=findViewById(R.id.online_target_score);
        status=findViewById(R.id.online_status);
        userName=findViewById(R.id.user_name_edit);
        enterNameText=findViewById(R.id.textView3);

        totalScore=Prefs.getScore(OnlineSubmitActivity.this);
        opponentInfo=Prefs.getOpponent(OnlineSubmitActivity.this);


        //Show the scores
        grandTotalScore.setText("Grand Total Score:\n"+totalScore);
        targetScore.setText("Target Score:\n"+opponentInfo.getScore());


        //Check if the user passed the target
        if(totalScore>=opponentInfo.getScore()){
            passedTheTarget=true;
        }else{
            passedTheTarget=false;
        }



        if(passedTheTarget){
            status.setText("You Won!");
            status.setTextColor(ContextCompat.getColor(OnlineSubmitActivity.this,R.color.text_color_blue));


        }else{
            enterNameText.setVisibility(View.INVISIBLE);
            userName.setVisibility(View.INVISIBLE);

            status.setText("You Failed to\n beat the score");
            status.setTextColor(ContextCompat.getColor(OnlineSubmitActivity.this,R.color.text_color_red));

            submitBtn.setText("Main Menu");
            submitBtn.setBackground(ContextCompat.getDrawable(OnlineSubmitActivity.this,R.drawable.stage_btn));

        }






        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(passedTheTarget){

                    if(userName.getText().length()>0){
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        LeaderboardItem userInfo=new LeaderboardItem(userName.getText().toString(),totalScore);
                        mDatabase.child("leaderboard").child(""+opponentInfo.getPosition()).setValue(userInfo);


                    }else{
                        return;
                    }

                }

                finish();
            }
        });

    }



}
