package com.kotlinapps.quizapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.kotlinapps.quizapp.R;
import com.kotlinapps.quizapp.UI.MainActivity;
import com.kotlinapps.quizapp.data.State;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notifications {

    private static final String PRIMARY_CHANNEL_ID = "notification_channel";
    private static final int NOTIFICATION_ID = 1;

    public static void getDailyNotification(Context context, State state){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Daily Quiz",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.CYAN);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent  = PendingIntent.getActivity(context,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        String notificationTitle = "Daily quiz";
        String notification_content = "What is the capital of "+state.getStateName()+ " ?";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(notification_content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_settings_black_24dp)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }

}
