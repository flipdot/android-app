package org.flipdot.flipdotapp.openDoor;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * Created by daniel on 06.12.14.
 */
public class SshOpenDoorTask extends AsyncTask<String,Void,Object> {
    private JSch _jsch;

    private String _hostname;
    private String _user;

    protected Exception Exception;

    public SshOpenDoorTask(String publicKey) {
        this._hostname = "192.168.3.42";
        this._user = "opendoor";

        this._jsch = new JSch();
        try {
            this._jsch.addIdentity(publicKey);
        }  catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected Object doInBackground(String... params) {
        Session session = null;
        try {
            session = this._jsch.getSession(this._user, this._hostname, 22);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect(5000);

            Channel channel = session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(5000);
        } catch (Exception ex) {
            this.Exception = ex;
        }

        return null;
    }
}
