package org.flipdot.flipdotapp;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by daniel on 25.12.14.
 */
public class GooglePlayServices {
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static boolean isAvailable(Activity activity, AppSettings settings) {
        boolean isAvailable = checkPlayServices(activity, settings);
        return isAvailable;
    }

    private static boolean checkPlayServices(Activity activity, AppSettings settings) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                if(!settings.getInstallPlayServicesShown()) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                    settings.setInstallPlayServicesShown(true);
                }
            }

            return false;
        }
        return true;
    }
}
