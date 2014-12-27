package org.flipdot.flipdotapp.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

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
            String msg = parameters.getString("newStatus");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

            notificationBuilder.setSmallIcon(R.drawable.doorstatus_open);
            notificationBuilder.setContentTitle("New Notification!");
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
            notificationBuilder.setContentIntent(contentIntent);
            notificationBuilder.setVibrate(new long[]{100, 100, 100, 100});
            notificationBuilder.setLights(Color.YELLOW, 500, 500);

            final int NOTIFICATION_ID = 1;
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        } catch (Exception ex){
        }
    }
}
