package org.flipdot.flipdotapp.spacestatus;

import android.os.AsyncTask;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by daniel on 30.11.14.
 */
public class SpacestatusLoadTask extends AsyncTask<String,Void,Spacestatus> {

    @Override
    protected Spacestatus doInBackground(String... params) {
        Spacestatus status = new Spacestatus();

        try {
            HttpGet httpGet = new HttpGet("http://flipdot.org/spacestatus/status.json");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseString = new DefaultHttpClient().execute(httpGet, responseHandler);

            JSONObject spaceStatusJson = new JSONObject(responseString);

            status.isOpen = spaceStatusJson.getBoolean("open");

            if(spaceStatusJson.has("unknown_users")) {
                status.unknownHackers = spaceStatusJson.getInt("unknown_users");
            }

            if(spaceStatusJson.has("known_users")) {
                JSONArray knownUsersArray = spaceStatusJson.getJSONArray("known_users");

                for (int i = 0; i < knownUsersArray.length(); i++) {
                    JSONObject knownHackerJson = knownUsersArray.getJSONObject(i);

                    KnownHacker knownHacker = new KnownHacker();
                    knownHacker.Nick = knownHackerJson.getString("nick");
                    status.knownHackers.add(knownHacker);
                }
            }
        } catch(Exception ex) {
            status.loadError = true;
        }
        return status;
    }
}
