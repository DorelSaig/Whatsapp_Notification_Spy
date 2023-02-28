package com.example.whatsapp_notification_spy.services;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.TimeUtils;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class NotificationListener extends NotificationListenerService {

    public static final String RADIO_STAION = "100FM";
    private static final String TAG = "pttt";
    private static final String TAG1 = "ptttt";
    private static final String WA_PACKAGE = "com.whatsapp";

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Notification Listener connected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
//
//        Notification notification = sbn.getNotification();
//        int id = 0;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//            id = sbn.getUid();
//        }
//
//        Bundle bundle = notification.extras;
//
//        String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
//        String contant = bundle.getString(Objects.requireNonNull(NotificationCompat.getContentInfo(notification)).toString());
//        String message = bundle.getString(NotificationCompat.EXTRA_TEXT);
//
//        if(message.contains("messages from")){
//
//        }else {
//            Log.i(TAG, "From: " + from);
//            Log.i(TAG, "Contant: " + contant);
//            Log.i(TAG, "Message: " + message);
//            Log.i(TAG, "Notification ID" + id);
//
//
//        }

//        Intent intent = new Intent(RADIO_STAION);
//        intent.putExtra("sender", from);
//        intent.putExtra("message", message);
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;

        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;

        String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
        String message = bundle.getString(NotificationCompat.EXTRA_TEXT);
        long time = sbn.getPostTime();


        String id = sbn.getKey();

        if(message.contains("messages from") || message.contains("new messages")){

        }else {
            Log.i(TAG1, "Deleted From: " + from);
            Log.i(TAG1, "Deleted Message: " + message);
            Log.i(TAG1, "Notification ID" + id);

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String sTime = formatter.format(new Date(time));
            Log.i(TAG, "Time" + sTime);

            Intent intent = new Intent(RADIO_STAION);
            intent.putExtra("sender", from);
            intent.putExtra("message", message);
            intent.putExtra("time", sTime);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }
}
