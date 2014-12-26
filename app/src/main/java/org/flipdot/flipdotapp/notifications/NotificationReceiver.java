package org.flipdot.flipdotapp.notifications;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by daniel on 26.12.14.
 */
public class NotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // set the class to handle the intent
        ComponentName comp = new ComponentName(context.getPackageName(), HandleNotificationService.class.getName());
        intent.setComponent(comp);

        this.startWakefulService(context, intent);
        this.setResultCode(Activity.RESULT_OK);
    }
}
