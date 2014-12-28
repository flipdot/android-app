package org.flipdot.flipdotapp.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.flipdot.flipdotapp.MainActivity;
import org.flipdot.flipdotapp.R;
import org.json.JSONObject;

/**
 * Created by daniel on 27.12.14.
 */
public class SpaceOpenNotificationHandler extends NotificationHandler {
    @Override
    public void HandleNotification(JSONObject parameters) {
        try {
            String newStatus = parameters.getString("newStatus");

            int newStatusImage, newStatusImageSmall;
            String header, message;
            if(newStatus.equals("open")) {
                newStatusImage = R.drawable.doorstatus_notification_open;
                newStatusImageSmall = R.drawable.doorstatus_notification_small_open;
                header = "hackerspace --> open";
                message = "the hackerspace is now open";
            } else if(newStatus.equals("close")) {
                newStatusImage = R.drawable.doorstatus_notification_close;
                newStatusImageSmall = R.drawable.doorstatus_notification_small_close;
                header = "hackerspace --> closed";
                message = "the hackerspace is now closed";
            } else {
                return;
            }

            Bitmap image = BitmapFactory.decodeResource(context.getResources(), newStatusImage);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent mainActivityIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
              .setSmallIcon(newStatusImageSmall)
              .setLargeIcon(image)
              .setContentTitle(header)
              .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
              .setContentIntent(mainActivityIntent)
              .setVibrate(new long[]{100, 100, 100, 100})
              .setLights(Color.YELLOW, 500, 500);

            final int NOTIFICATION_ID = 1;
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        } catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }
    }
}
