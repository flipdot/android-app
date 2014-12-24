package org.flipdot.flipdotapp;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

/**
 * Created by daniel on 20.12.14.
 */
public class FlipdotAuthentication {
    private String username;

    public FlipdotAuthentication(){
    }

    private final static  int SELECT_ACCOUNT_REQUEST_CODE = 1234;
    private final static int AUTHENTICATE_GOOGLE_REQUEST_CODE = 1235;

    public void authenticate(){
        String selectedGoogleAccountUsername = MainActivity.instance.settings.getSelectedGoogleAccountUsername();
        if(selectedGoogleAccountUsername == null) {
            Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
            MainActivity.instance.startActivityForResult(intent, SELECT_ACCOUNT_REQUEST_CODE);
            return;
        }

        getTokenAsync(selectedGoogleAccountUsername);
    }

    private void getTokenAsync(final String username) {
        MainActivity.instance.settings.setSelectedGoogleAccountUsername(username);
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    String token = GoogleAuthUtil.getToken(
                            MainActivity.instance, username, "oauth2:https://www.googleapis.com/auth/userinfo.email");
                    FlipdotAuthentication.this.onTokenReceived(token);
                    return null;
                }
                catch (UserRecoverableAuthException ex) {
                    MainActivity.instance.startActivityForResult(ex.getIntent(), AUTHENTICATE_GOOGLE_REQUEST_CODE);
                    return null;
                }
                catch (Exception ex) {
                    return null;
                }
            }
        };

        task.execute();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode){
            case SELECT_ACCOUNT_REQUEST_CODE:{
                this.username = data.getExtras().getString("authAccount");
                //this.accountType = data.getExtras().getString("accountType");
                this.getTokenAsync(this.username);
            }
            case AUTHENTICATE_GOOGLE_REQUEST_CODE:{
                this.getTokenAsync(this.username);
            }
        }
    }

    private void onTokenReceived(String token){
        FlipdotAuthentication.this.onAuthenticateDone();
    }

    public void onAuthenticateDone() {

    }
}
