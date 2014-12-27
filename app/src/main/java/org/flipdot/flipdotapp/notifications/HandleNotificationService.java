package org.flipdot.flipdotapp.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

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

        String action = extras.getString("action");
        Bundle paramters = extras.getBundle("params");

        if(action == null || paramters == null) {
            return;
        }

        NotificationHandler handler;
        if(action.equals("spaceStatusChanged")) {
            handler = new SpaceOpenNotificationHandler();
        } else {
            return;
        }

        handler.context = this;
        handler.HandleNotification(paramters);
    }
}
