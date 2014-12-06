package org.flipdot.flipdotapp.openDoor;

import android.os.Environment;

/**
 * Created by daniel on 06.12.14.
 */
public class OpenDoorConstants {
    public static String PrivateKeyFilePath = Environment.getExternalStorageDirectory().getPath() + "/flipdot_doorsshkey";
    public static String PublicKeyFilePath = PrivateKeyFilePath+".pub";
}
