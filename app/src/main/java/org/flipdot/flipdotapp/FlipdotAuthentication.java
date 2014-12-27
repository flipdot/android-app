package org.flipdot.flipdotapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by daniel on 20.12.14.
 */
public class FlipdotAuthentication {
    private String username;
    public String gcmId;
    private String token;

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

        this.username = selectedGoogleAccountUsername;
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

    public void registerDevice() {
        try {
            HttpPost httpPost = new HttpPost("http://flipdot.org/android-app/registerClient");
            httpPost.setHeader("Content-Type", "application/json");

            JSONObject request = new JSONObject();
            request.put("token", this.token);
            request.put("gcmId", this.gcmId);

            StringEntity requestEntity = new StringEntity(request.toString());
            httpPost.setEntity(requestEntity);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseString = new DefaultHttpClient().execute(httpPost, responseHandler);
        } catch(Exception ex) {
            Log.e("ERROR", ex.toString());
        }
    }

    private void onTokenReceived(String token){
        this.token = token;
        this.registerDevice();

        FlipdotAuthentication.this.onAuthenticateDone();
    }

    public void onAuthenticateDone() {
    }
}
