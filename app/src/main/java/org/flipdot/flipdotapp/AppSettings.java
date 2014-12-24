package org.flipdot.flipdotapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by daniel on 24.12.14.
 */
public class AppSettings {
    private final static String appSettingsFileName = "FlipdotAppSettings";

    public static String selectedGoogleAccountUsername;

    public static void load() {
        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(appSettingsFileName, Context.MODE_PRIVATE);

        selectedGoogleAccountUsername = preferences.getString("selectedGoogleAccountUsername", null);
    }

    public static void save() {
        SharedPreferences preferences = MainActivity.instance.getSharedPreferences(appSettingsFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("selectedGoogleAccountUsername", selectedGoogleAccountUsername);

        editor.commit();
    }
}
