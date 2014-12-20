package org.flipdot.flipdotapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import java.io.IOException;

/**
 * Created by daniel on 20.12.14.
 */
public class FlipdotAuthentication {
    private final Activity activity;

    public FlipdotAuthentication(Activity activity){
        this.activity = activity;
    }

    private static int AUTHENTICATE_GOOGLE_REQUEST_CODE = 1234;

    public void authenticate(){
        final Activity resActivity = new Activity(){
            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(requestCode != AUTHENTICATE_GOOGLE_REQUEST_CODE) return;

                FlipdotAuthentication.this.onAuthenticateDone();
            }
        };

        Activity selectAccountActivity = new Activity(){
            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                FlipdotAuthentication.this.onAuthenticateDone();
            }
        };

        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        selectAccountActivity.startActivityForResult(intent, 123);

//        AccountManager manager = (AccountManager) activity.getSystemService(Context.ACCOUNT_SERVICE);
//        Account[] googleAccounts = manager.getAccountsByType("com.google");
//        if(googleAccounts.length > 0) {
//            final Account account = googleAccounts[0];
//
//            AsyncTask task = new AsyncTask() {
//                @Override
//                protected Object doInBackground(Object[] params) {
//                    try {
//                        String token = GoogleAuthUtil.getToken(activity, account.name, "oauth2:https://www.googleapis.com/auth/userinfo.email");
//                        return token;
//                    }
//                    catch (IOException ex) {
//                        return null;
//                    }
//                    catch (UserRecoverableAuthException ex) {
//                        resActivity.startActivityForResult(ex.getIntent(), AUTHENTICATE_GOOGLE_REQUEST_CODE);
//                        return null;
//                    }
//                    catch (GoogleAuthException ex) {
//                        return null;
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(Object o) {
//                    String sss = (String)o;
//                }
//            };
//
//            task.execute();
//        }
    }

    public void onAuthenticateDone() {

    }
}
