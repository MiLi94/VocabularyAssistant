package com.limi.andorid.vocabularyassistant.acti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.Record;

import java.util.ArrayList;

/**
 * Created by limi on 16/5/17.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private ArrayList<Record> wordArrayList = new ArrayList<>();
    private Context mContext;


    public RankAdapter(Context context, ArrayList<Record> recordArrayList) {
        this.wordArrayList = recordArrayList;
        this.mContext = context;
    }

    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_rank, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final RankAdapter.ViewHolder holder, int i) {
        Record w = wordArrayList.get(i);
        switch (i) {
            case 0:
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.one));
                break;
            case 1:
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.two));
                break;
            case 2:
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.three));
                break;
            case 3:
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.four));
                break;
            case 4:
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.five));
                break;


        }
        holder.nameTextView.setText(String.valueOf(w.getUserName()));

        String sCount = String.valueOf(w.getThisCount());
        holder.countTextView.setText(sCount + " Words");

        int difference = w.getLastRank() - w.getTodayRank();
        String s = "New";
        if (difference != 0) {
            s = String.valueOf(difference);
        }


        holder.numberTextView.setText(String.valueOf(difference));


    }

    @Override
    public int getItemCount() {
        return wordArrayList == null ? 0 : wordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView numberTextView;
        public TextView countTextView;
        //        public ImageButton favButton;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.ranking_name);
            numberTextView = (TextView) itemView.findViewById(R.id.floating);
            countTextView = (TextView) itemView.findViewById(R.id.rank_count);
            imageView = (ImageView) itemView.findViewById(R.id.ranking_image);

        }
    }
}