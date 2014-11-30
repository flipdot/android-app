package org.flipdot.flipdotapp;

import android.os.AsyncTask;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by daniel on 30.11.14.
 */
public class SpacestatusLoadTask extends AsyncTask<String,Void,Spacestatus> {
    @Override
    protected Spacestatus doInBackground(String... params) {
        Spacestatus status = new Spacestatus();

        try {
            HttpGet httpGet = new HttpGet("http://flipdot.org/spacestatus/door.txt");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = new DefaultHttpClient().execute(httpGet, responseHandler);

            status.isOpen = response.equalsIgnoreCase("1");
        } catch(Exception ex) {
            status.loadError = true;
        }
        return status;
    }
}
