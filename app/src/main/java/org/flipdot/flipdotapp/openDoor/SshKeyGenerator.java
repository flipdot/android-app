package org.flipdot.flipdotapp.openDoor;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;

import java.io.File;

/**
 * Created by daniel on 06.12.14.
 */
public class SshKeyGenerator {

    public static void ensureKeypairExists(){
        JSch jsch = new JSch();
        try {
            File privateKey = new File(OpenDoorConstants.PrivateKeyFilePath);
            if(privateKey.exists()) return;

            new File(OpenDoorConstants.FlipdotRootFolder).mkdirs();

            KeyPair keyPair = KeyPair.genKeyPair(jsch, KeyPair.RSA);
            keyPair.writePrivateKey(OpenDoorConstants.PrivateKeyFilePath);
            keyPair.writePublicKey(OpenDoorConstants.PublicKeyFilePath, "flipdot_app");
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public static void pushKeyToServer() {
        PushOpenDoorKeyToServerTask task = new PushOpenDoorKeyToServerTask();
        task.execute();
    }
}
