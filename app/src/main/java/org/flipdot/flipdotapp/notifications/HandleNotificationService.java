package org.flipdot.flipdotapp.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

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
        String paramsJson = extras.getString("params");

        JSONObject paramters;
        try {
            paramters = new JSONObject(paramsJson);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }


        if(action == null || paramters == null) {
            throw new RuntimeException("invalid push notification: HandleNotificationService.onHandleIntent has no action or params");
        }

        NotificationHandler handler;
        if(action.equals("spaceStatusChanged")) {
            handler = new SpaceOpenNotificationHandler();
        } else {
            // don't throw an exception because maybe the server is newer than this app version
            return;
        }

        handler.context = this;
        handler.HandleNotification(paramters);
    }
}
