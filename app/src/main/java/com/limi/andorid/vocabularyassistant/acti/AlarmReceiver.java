package com.limi.andorid.vocabularyassistant.acti;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;

/**
 * Created by limi on 16/5/18.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager manager;

    private MySQLiteHandler mySQLiteHandler;

    /**
     * called when the BroadcastReceiver is receiving an Intent broadcast.
     */
    @Override
    public void onReceive(Context context, Intent intent) {


        manager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        String id = "0";
        Intent playIntent = new Intent(context, LoginActivity.class);
        playIntent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Vocabulary Assistant").setContentText("You need new words to review.").setSmallIcon(R.mipmap.logo3).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true);
        manager.notify(1, builder.build());

    }
}