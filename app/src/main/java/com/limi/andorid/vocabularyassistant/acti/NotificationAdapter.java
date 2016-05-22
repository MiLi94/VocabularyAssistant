package com.limi.andorid.vocabularyassistant.acti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.data.NotificationEntry;

import java.util.ArrayList;

/**
 * Created by limi on 16/5/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationEntry> notificationEntries = new ArrayList<>();
    private Context mContext;


    public NotificationAdapter(Context context, ArrayList<NotificationEntry> notificationEntries) {
        this.notificationEntries = notificationEntries;
        this.mContext = context;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder holder, int i) {
        NotificationEntry w = notificationEntries.get(i);

        holder.dateTextView.setText(w.getDate());

        holder.contentTextView.setText(w.getContent());


    }

    @Override
    public int getItemCount() {
        return notificationEntries == null ? 0 : notificationEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView contentTextView;
//        public TextView countTextView;
        //        public ImageButton favButton;


        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            contentTextView = (TextView) itemView.findViewById(R.id.notification_m);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext).setTitle("Message shows")
                            .setSingleChoiceItems(new String[]{"Continue to recite", "Review"}, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Intent meaningIntent = new Intent(mContext, RecitingActivity.class);
                                            mContext.startActivity(meaningIntent);

                                            break;
                                        case 1:
                                            //Review activity
                                            Intent meaningIntent1 = new Intent(mContext, ReviewActivity.class);
                                            mContext.startActivity(meaningIntent1);
                                            break;
                                    }
                                    dialog.dismiss();

                                }

                            })
                            .setNegativeButton("CANCEL", null).show();
                }
            });

        }
    }
}