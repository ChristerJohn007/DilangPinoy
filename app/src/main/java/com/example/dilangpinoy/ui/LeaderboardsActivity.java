package com.example.dilangpinoy.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.adapters.LeaderboardListAdapter;
import com.example.dilangpinoy.objects.LeaderboardItem;
import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.tools.JSONTools;
import com.example.dilangpinoy.tools.Prefs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardsActivity extends BaseActivity implements LeaderboardListAdapter.LeaderboardListAdapterOnClickHandler {

    Button backBtn;
    private DatabaseReference mDatabase;
    LeaderboardListAdapter adapter;
    List<LeaderboardItem> scores;
    private ListView leaderboardsListView;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);


        loadingPB=findViewById(R.id.loadingPB);
        loadingPB.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        leaderboardsListView = findViewById(R.id.stages_list_view);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        initListView();





    }

    public void initListView() {
        scores=new ArrayList<>();
        scores.clear();
        loadingPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query leaderboardQuery = mDatabase.child("leaderboard");
        leaderboardQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scores.clear();
                for (DataSnapshot scoreSnapshot: dataSnapshot.getChildren()) {
                    LeaderboardItem item=scoreSnapshot.getValue(LeaderboardItem.class);
                    scores.add(item);
                }
                adapter=new LeaderboardListAdapter(LeaderboardsActivity.this,scores,LeaderboardsActivity.this);
                leaderboardsListView.setAdapter(adapter);
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LeaderboardsActivity.this,"Couldn't contact the database.",Toast.LENGTH_LONG).show();
            }
        });

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    @Override
    public void onClick(LeaderboardItem leaderboardItem) {
    showOnlineDialog(leaderboardItem);
    }




    //Dialog for the selected stage
    private void showOnlineDialog(final LeaderboardItem item) {
        final Dialog dialog = new Dialog(LeaderboardsActivity.this, R.style.Theme_Dialog);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.StageDialogAnimation;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        dialog.setCancelable(true);

        // set the custom layout

        final View customLayout = getLayoutInflater().inflate(R.layout.online_dialog, null);
        dialog.setContentView(customLayout);


        TextView name,score;
        Button playBtn;


        name=customLayout.findViewById(R.id.online_dialog_name);
        score=customLayout.findViewById(R.id.online_dialog_score);

        playBtn=customLayout.findViewById(R.id.online_play_btn);









        name.setText(item.getName()+"?");
        score.setText(item.getScore()+ " pts");






        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stage stage=JSONTools.getStage(LeaderboardsActivity.this,0);


                Prefs.saveOpponent(LeaderboardsActivity.this,item);
                Prefs.setOnline(LeaderboardsActivity.this,true);
                Prefs.saveScore(LeaderboardsActivity.this,0);


                Intent intent=new Intent(LeaderboardsActivity.this, TriviaActivity.class);
                intent.putExtra("selectedStage",stage);
                intent.putExtra("stageNumber",0);
                startActivity(intent);
                onChangeActivity();
                dialog.dismiss();
            }
        });




        dialog.show();


    }



}
