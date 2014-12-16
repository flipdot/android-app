package org.flipdot.flipdotapp.spacestatus;

import java.util.ArrayList;

/**
 * Created by daniel on 30.11.14.
 */
public class Spacestatus {

    public Spacestatus(){
        this.knownHackers = new ArrayList<KnownHacker>();
    }

    public boolean loadError;
    public boolean isOpen;
    public int unknownHackers;

    public ArrayList<KnownHacker> knownHackers;
}

