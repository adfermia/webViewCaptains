package com.example.webviewcaptains;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFCMService extends FirebaseMessagingService {
    public MyFCMService() {
    }

    @Override@JavascriptInterface
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                createAndSendNotificationB(remoteMessage);
            } else {
                // Handle message within 10 seconds
                createAndSendNotificationC(remoteMessage.getNotification().getBody());
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Vaig a intentar crear un canal per a la merda de la notificacio
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my fucking channel right nigga???";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setAutoCancel(true);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pi  = PendingIntent.getActivity(this, 0, intent, 0);
            builder.setContentIntent(pi);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);

            mNotificationManager.notify(0, builder.build());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void createAndSendNotificationB(RemoteMessage remoteMessage) {
        Log.v("CREATE", "Hola desde createAnd ¡SendNOTIFICATION");
    }
    private void createAndSendNotificationC(String messageBody) {
        Log.v("CREATE", "Hola desde createAnd ¡SendNOTIFICATIONc");
    }
    private void sendRegistrationToServer(String token) {
        Log.v("SEND", "Hola desde send Registration to server");

    }




}
