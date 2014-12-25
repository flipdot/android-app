package org.flipdot.flipdotapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by daniel on 24.12.14.
 */
public class AppSettings {
    private final static String appSettingsFileName = "FlipdotAppSettings";

    private Activity activity;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AppSettings(Activity activity) {
        this.activity = activity;
        this.preferences = this.activity.getSharedPreferences(appSettingsFileName, Context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public String getSelectedGoogleAccountUsername() {
        return this.preferences.getString("selectedGoogleAccountUsername", null);
    }

    public void setSelectedGoogleAccountUsername(String selectedGoogleAccountUsername) {
        this.editor.putString("selectedGoogleAccountUsername", selectedGoogleAccountUsername);
        this.editor.commit();
    }

    public String getGcmId() {
        return this.preferences.getString("GcmId", null);
    }

    public void setGcmId(String gcmId) {
        this.editor.putString("selectedGoogleAccountUsername", gcmId);
        this.editor.commit();
    }
}
