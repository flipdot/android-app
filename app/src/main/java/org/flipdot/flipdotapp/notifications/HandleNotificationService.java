package org.flipdot.flipdotapp.notifications;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import org.flipdot.flipdotapp.MainActivity;
import org.flipdot.flipdotapp.R;

/**
 * Created by daniel on 26.12.14.
 */
public class HandleNotificationService extends IntentService {
    public HandleNotificationService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String msg = (String)extras.get("msg");

        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.drawable.doorstatus_open);
        notificationBuilder.setContentTitle("New Notification!");
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
        notificationBuilder.setContentIntent(contentIntent);
        notificationBuilder.setVibrate(new long[] { 100, 100, 100, 100 });
        notificationBuilder.setLights(Color.YELLOW, 500, 500);

        final int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
