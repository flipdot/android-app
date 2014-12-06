package org.flipdot.flipdotapp.openDoor;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileReader;

/**
 * Created by daniel on 06.12.14.
 */
public class PushOpenDoorKeyToServerTask extends AsyncTask<String, Void, Object> {
    @Override
    protected Object doInBackground(String... params) {

        HttpPost httpPost = new HttpPost("http://tuer-pi/opendoor/storePublicKey.php");
        File publicKeyFile = new File(OpenDoorConstants.PublicKeyFilePath);
        try {
            FileReader fileReader = new FileReader(publicKeyFile);

            char[] buffer = new char[(int)publicKeyFile.length()];
            fileReader.read(buffer);
            String content = new String(buffer);
            HttpEntity entity = new StringEntity(content);
            httpPost.setEntity(entity);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = new DefaultHttpClient().execute(httpPost, responseHandler);
        } catch(Exception ex){
            System.out.println(ex.toString());
        }

        return null;
    }
}
