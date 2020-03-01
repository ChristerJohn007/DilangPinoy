package com.example.dilangpinoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.Stage;
import com.squareup.picasso.Picasso;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StagesListAdapter extends BaseAdapter {
    final String STAGE_IMAGE_PATH = "file:///android_asset/stage_images/";
    List<Stage> stages;
    Context mContext;

    private final StageListAdapterOnClickHandler mClickHandler;

    public StagesListAdapter(Context context,List<Stage> stages,StageListAdapterOnClickHandler mClickHandler){
        this.mClickHandler=mClickHandler;
        this.stages=stages;
        this.mContext=context;
    }



    public interface StageListAdapterOnClickHandler {
        void onClick(Stage stage,int stageNumber);
    }

    @Override
    public int getCount() {
        return stages.size();
    }

    @Override
    public Stage getItem(int i) {
        return stages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.stage_list_item, viewGroup, false);
        LinearLayout stageBtn=rowView.findViewById(R.id.stage_layout);
        TextView title=rowView.findViewById(R.id.stage_title);
        TextView subtitle=rowView.findViewById(R.id.stage_subtitle);
        CircleImageView stageImage=rowView.findViewById(R.id.stage_image);

        title.setText(stages.get(i).getTitle());
        subtitle.setText(stages.get(i).getSubtitle());

        Picasso.get().load(STAGE_IMAGE_PATH + stages.get(i).getImagePath()).into(stageImage);


        if(!stages.get(i).isEnabled()){
            stageImage.setAlpha(0.2f);
            title.setAlpha(0.2f);
            subtitle.setAlpha(0.2f);
        }

       // stageBtn.setText(stages.get(i).getName());



        final Stage stage=stages.get(i);
        final int stageNumber=i;

        stageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mContext.startActivity(Stage.getStageIntent(stage,mContext,stageNumber));
                if(stage.isEnabled())
                mClickHandler.onClick(stage,stageNumber);
            }
        });



        return rowView;
    }
}
