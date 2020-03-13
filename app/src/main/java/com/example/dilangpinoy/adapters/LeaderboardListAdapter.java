package com.example.dilangpinoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.dilangpinoy.R;
import com.example.dilangpinoy.objects.LeaderboardItem;
import com.example.dilangpinoy.objects.Stage;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class LeaderboardListAdapter extends BaseAdapter {

    List<LeaderboardItem> scores;
    Context mContext;

    private final LeaderboardListAdapterOnClickHandler mClickHandler;

    public LeaderboardListAdapter(Context context, List<LeaderboardItem> scores, LeaderboardListAdapterOnClickHandler mClickHandler){
        this.mClickHandler=mClickHandler;
        this.scores=scores;
        this.mContext=context;
    }



    public interface LeaderboardListAdapterOnClickHandler {


        void onClick(LeaderboardItem leaderboardItem);
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public LeaderboardItem getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.leaderboard_item, viewGroup, false);

        Button challengeBtn = rowView.findViewById(R.id.challenge_btn);

        TextView rankTv = rowView.findViewById(R.id.rank_tv);
        TextView name= rowView.findViewById(R.id.leader_name);
        TextView score = rowView.findViewById(R.id.leader_points);


        rankTv.setText("#"+(i+1));
        //Set Color
        if(i==0){
            rankTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_red));
        }
        if(i==1){
            rankTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_blue));
        }
        if(i==2){
            rankTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_purple));
        }

        name.setText(""+scores.get(i).getName());
        score.setText(""+scores.get(i).getScore()+" pts");

        final int scoreIndex=i;

        challengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                scores.get(scoreIndex).setPosition(scoreIndex+1);
                mClickHandler.onClick(scores.get(scoreIndex));
            }
        });



        return rowView;
    }
}
