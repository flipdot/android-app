package org.flipdot.flipdotapp.notifications;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.flipdot.flipdotapp.AppSettings;

import java.io.IOException;

/**
 * Created by daniel on 25.12.14.
 */
public class GcmRegistration {
    private final String GCM_APP_ID = "253061035622";

    private AppSettings settings;
    private Context context;

    public GcmRegistration(AppSettings settings, Context context) {
        this.settings = settings;
        this.context = context;
    }

    public void ensureRegistered() {
        String storedId = this.settings.getGcmId();
        if(storedId == null) {
            gcmRegisterAsync();
        }

        this.onRegisterEnsured(storedId);
    }

    private void gcmRegisterAsync() {

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(GcmRegistration.this.context);
                    String gcmId = gcm.register(GCM_APP_ID);
                    return gcmId;
                } catch (IOException ex) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                String gcmId = (String)o;

                if(gcmId == null) return;

                GcmRegistration.this.settings.setGcmId(gcmId);
                GcmRegistration.this.onRegisterEnsured(gcmId);
            }
        };

        task.execute();
    }

    public void onRegisterEnsured(String gcmId){
    }
}
