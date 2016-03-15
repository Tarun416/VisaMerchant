package com.example.hp.visamerchant;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * Created by hp on 13-03-2016.
 */
public class MyGcmListenerService extends GcmListenerService {

    JSONObject ob;
    JSONObject ob1;
    String score;

    String message="hello";


    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {


       String p= data.toString();
        // Bundle[{message=Manual push notification from Rajkumar, collapse_key=do_not_collapse}]

        String content = p.split("=")[1];
        String msg = content.split(",")[0];









    /* String dat=   data.getString("data");

        try {
            ob=new JSONObject(dat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            score=ob.get("message").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


      /*  try {
            ob=new JSONObject( data.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            score=   ob.get("message").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

/*        ob=new JSONObject(data);

        try {
             ob=new JSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            ob1= ob.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            score=  ob1.get("message").toString();
        } catch (JSONException e) {

        //
       // String message = data.getString("data");






       /* try {
            ob=new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            ob1= ob.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            score=ob1.get("score").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        // message.get
        //  String message1=  message.getString("score");

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Test")
                .setContentText(msg);
        notificationManager.notify(1, mBuilder.build());




        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        //  sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
              //  .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Alert")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

