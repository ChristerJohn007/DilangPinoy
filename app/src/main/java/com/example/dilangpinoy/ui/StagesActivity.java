package com.example.dilangpinoy.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.adapters.StagesListAdapter;
import com.example.dilangpinoy.objects.Stage;
import com.example.dilangpinoy.tools.JSONTools;
import com.example.dilangpinoy.tools.Prefs;
import com.example.dilangpinoy.ui.stages.TapThePairsActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StagesActivity extends  BaseActivity implements StagesListAdapter.StageListAdapterOnClickHandler {
    final String STAGE_IMAGE_PATH = "file:///android_asset/stage_images/";
    List<Stage> stageList=new ArrayList<Stage>();
    Button backBtn;
    SharedPreferences.OnSharedPreferenceChangeListener onPrefsChangeListener;


    private int selectedStage;
    private ListView stagesListView;
    SharedPreferences sharedPref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stages);

       sharedPref = StagesActivity.this.getSharedPreferences("saves",Context.MODE_PRIVATE);
        stagesListView=findViewById(R.id.stages_list_view);
        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        initListView();


        Intent intent=getIntent();
        selectedStage=intent.getIntExtra("selectedStage",-1);
        if(selectedStage!=-1){
            showStageDialog(stageList.get(selectedStage), selectedStage);
        }

        onPrefsChangeListener=new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                initListView();
            }
        };

        sharedPref.registerOnSharedPreferenceChangeListener(onPrefsChangeListener);



    }



    public void initListView(){
        JSONTools.readFileWithStages(StagesActivity.this,stageList);
        int completeStages=sharedPref.getInt("completeStages",0);
        Log.d("COMPLETED","COM:"+completeStages);
        for(int i=0;i<stageList.size();i++){
            if(completeStages>=i)
            stageList.get(i).setEnabled(true);
        }

        StagesListAdapter adapter = new StagesListAdapter(StagesActivity.this,stageList,this);


        stagesListView.setAdapter(adapter);

    }




    //Dialog for the selected stage
    private void showStageDialog(final Stage stage, final int stageNumber) {
        final Dialog dialog = new Dialog(StagesActivity.this, R.style.Theme_Dialog);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.getAttributes().windowAnimations = R.style.StageDialogAnimation;
        dialog.setCancelable(true);

        // set the custom layout

        final View customLayout = getLayoutInflater().inflate(R.layout.stage_dialog, null);
        dialog.setContentView(customLayout);


        TextView stageTitle,stageSubtitle,stageTime,stageMistakes;
        Button playBtn;
        CircleImageView stageImage;

        stageTitle=customLayout.findViewById(R.id.stage_dialog_title);
        stageSubtitle=customLayout.findViewById(R.id.stage_dialog_subtitle);
        stageTime=customLayout.findViewById(R.id.stage_dialog_time);
        stageMistakes=customLayout.findViewById(R.id.stage_dialog_mistakes);
        playBtn=customLayout.findViewById(R.id.stage_play_btn);
        stageImage=customLayout.findViewById(R.id.stage_dialog_image);






        Picasso.get().load(STAGE_IMAGE_PATH + stage.getImagePath()).into(stageImage);


        stageTitle.setText(stage.getTitle());
        stageSubtitle.setText(stage.getSubtitle());
        stageTime.setText(stage.getTime() + " seconds per level");
        stageMistakes.setText("Mistakes Allowed: "+ stage.getMistakesAllowed());





       playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //
                Prefs.setOnline(StagesActivity.this,false);

                Intent intent=new Intent(StagesActivity.this, TriviaActivity.class);
                intent.putExtra("selectedStage",stage);
                intent.putExtra("stageNumber",stageNumber);
                startActivity(intent);
                onChangeActivity();
               dialog.dismiss();

            }
        });




        dialog.show();


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        selectedStage=intent.getIntExtra("selectedStage",-1);
        if(selectedStage!=-1){
            showStageDialog(stageList.get(selectedStage), selectedStage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPref.unregisterOnSharedPreferenceChangeListener(onPrefsChangeListener);
    }

    @Override
    public void onClick(Stage stage,int stageNumber) {
        showStageDialog(stage,stageNumber);
    }


}
