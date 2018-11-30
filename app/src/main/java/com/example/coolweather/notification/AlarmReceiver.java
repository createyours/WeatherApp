package com.example.coolweather.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.coolweather.NotificationActivity;
import com.example.coolweather.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1000;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("NOTIFICATION")) {
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent2 = new Intent(context,NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
            Notification notify = new NotificationCompat.Builder(context,"two")
                    .setSmallIcon(R.drawable.alarm_tick)
                    .setTicker("点击查看天气！")
                    .setContentTitle("天气提醒")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("天气内容"))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setNumber(1).build();
            manager.notify(NOTIFICATION_ID, notify);
        }

    }
}
