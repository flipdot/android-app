package org.flipdot.flipdotapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

/**
 * Created by daniel on 20.12.14.
 */
public class FlipdotAuthentication {
    private final Activity activity;
    private String username;
    private String accountType;

    public FlipdotAuthentication(Activity activity){
        this.activity = activity;
    }

    private final static  int SELECT_ACCOUNT_REQUEST_CODE = 1234;
    private final static int AUTHENTICATE_GOOGLE_REQUEST_CODE = 1235;

    public void authenticate(){
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{ "com.google" }, false, null, null, null, null);
        this.activity.startActivityForResult(intent, SELECT_ACCOUNT_REQUEST_CODE);
    }

    private void getTokenAsync(final String username, String accountType) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    String token = GoogleAuthUtil.getToken(
                            FlipdotAuthentication.this.activity, username, "oauth2:https://www.googleapis.com/auth/userinfo.email");
                    FlipdotAuthentication.this.onTokenReceived(token);
                    return null;
                }
                catch (UserRecoverableAuthException ex) {
                    FlipdotAuthentication.this.activity.startActivityForResult(ex.getIntent(), AUTHENTICATE_GOOGLE_REQUEST_CODE);
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
                this.accountType = data.getExtras().getString("accountType");
                this.getTokenAsync(this.username, this.accountType);
            }
            case AUTHENTICATE_GOOGLE_REQUEST_CODE:{
                this.getTokenAsync(this.username, this.accountType);
            }
        }
    }

    private void onTokenReceived(String token){
        FlipdotAuthentication.this.onAuthenticateDone();
    }

    public void onAuthenticateDone() {

    }
}
