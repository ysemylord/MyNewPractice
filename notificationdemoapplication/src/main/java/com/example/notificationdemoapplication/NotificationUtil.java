package com.example.notificationdemoapplication;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class NotificationUtil {
    public static void test(Activity context){
        Notification.Builder builder = new Notification.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentText("contenttext")
                .setContentTitle("contenttitle")
                .setSubText("subtext")
                .setAutoCancel(false)
                .setFullScreenIntent(pendingIntent, true);
                //.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        Notification notification = builder.build();




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
