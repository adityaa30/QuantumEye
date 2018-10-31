package com.example.aditya.quantumeye.cloud_messaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.aditya.quantumeye.R;
import com.example.aditya.quantumeye.api.NotificationAlert;
import com.example.aditya.quantumeye.utils.QuantumUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String token) {
        Log.d("FCMService", "Refreshed token: " + token);
        //fmYOSjDJQX4:APA91bFN4zqKSTAEHYH6iy7NTHlIZc1SybmgDG37sEbzRX0-S0ryRUssjYoQHSPjsphgyYcdinqp8sMBNJ-yOqLLZUTVKkWraKKaOQCtnjzxZvOD6I4px0j3T8yjDeX8ln6zKtKmZ4ma

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FCMService", "From: " + remoteMessage.getFrom());

        String imageUrl = remoteMessage.getData().get("imageUrl");

        if (remoteMessage.getNotification() != null){
            Log.d("FCMService", "Message Notification Body: " + remoteMessage.getNotification().getBody());

            createNotification(remoteMessage.getNotification().getBody());

            NotificationAlert notificationAlert = new NotificationAlert();
            notificationAlert.setImageUrl(imageUrl);
            notificationAlert.setMessage(/*remoteMessage.getNotification().getTitle() + " : " + */ remoteMessage.getNotification().getBody());
            QuantumUtils.notificationAlerts.add(notificationAlert);
        }
    }

    private void createNotification(String alertMessage){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), QuantumUtils.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_security_white_24dp)
                .setContentTitle("DANGER")
                .setContentText(alertMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        mBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.danger_alarm));
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        mBuilder.setLights(Color.RED, 1000, 1000);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(QuantumUtils.NOTIFICATION_ID, mBuilder.build());

    }
}
