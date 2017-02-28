package com.thesis.velma;

/**
 * Created by admin on 12/21/2016.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    Context mcontext;

    @Override
    public void onReceive(final Context context, Intent intent) {

        mcontext = context;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();

        showNotification(intent.getStringExtra("name"));
    }

    private void showNotification(String eventname) {
        String eventDescription = "Alarm for an Event.";

        android.support.v4.app.NotificationCompat.BigTextStyle bigStyle = new android.support.v4.app.NotificationCompat.BigTextStyle();
        bigStyle.bigText(eventDescription);

        Notification notification = new android.support.v4.app.NotificationCompat.Builder(mcontext)
                .setSmallIcon(R.drawable.velmalogo)
                .setContentTitle(eventname)
                .setContentText(eventDescription).setStyle(bigStyle)
                .extend(new android.support.v4.app.NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                .build();

         notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
//        PendingIntent contentIntent = PendingIntent.getActivity(mcontext, 0,
//                new Intent(mcontext, LandingActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        notification.contentIntent = PendingIntent.getActivity(mcontext, 0,
                new Intent(mcontext, LandingActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mcontext);
        int notificationId = 1;
        notificationManager.notify(notificationId, notification);


    }
}