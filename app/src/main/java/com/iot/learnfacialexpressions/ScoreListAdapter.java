package com.iot.learnfacialexpressions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ScoreListAdapter extends ArrayAdapter<Score> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Score> scores;

    public ScoreListAdapter(Context context, ArrayList<Score> scores){
        super(context,0, scores);
        this.context = context;
        this.scores = scores;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Nullable
    @Override
    public Score getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return scores.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.score, null);

            holder = new ViewHolder();
            holder.scoreImg = (ImageView) convertView.findViewById(R.id.score_img);
            holder.dateTxt = (TextView) convertView.findViewById(R.id.date_txt);
            holder.scoreTxt = (TextView) convertView.findViewById(R.id.score_txt);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Score score = scores.get(position);
        if(score != null){
            holder.scoreImg.setImageResource(score.getScoreImage());
            holder.scoreTxt.setText(score.getScore());
            holder.dateTxt.setText(score.getDate());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView dateTxt;
        TextView scoreTxt;
        ImageView scoreImg;
    }
}
