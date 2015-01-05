package org.flipdot.flipdotapp.FlipdotIrc;

import java.util.Date;

/**
 * Created by daniel on 05.01.15.
 */
public class IrcEntry {
    public IrcEntry(String msg, Date time, IrcUser user) {
        this.Msg = msg;
        this.Time = time;
        this.User = user;
    }

    public String Msg;
    public Date Time;
    public IrcUser User;
}
