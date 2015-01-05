package org.flipdot.flipdotapp.FlipdotIrc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.flipdot.flipdotapp.R;

import java.util.ArrayList;
import java.util.Calendar;


public class FlipdotIrcControl extends LinearLayout {

    private IrcEntryAdapter _entryAdapter;

    public FlipdotIrcControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_flipdot_irc_control, this);

        this._entryAdapter = new IrcEntryAdapter(context, R.layout.irc_chat_entry, new ArrayList<IrcEntry>());

        ListView ircEntriesList = (ListView)this.findViewById(R.id.ircEntriesList);
        ircEntriesList.setAdapter(this._entryAdapter);

        // test data
        Calendar calendar = Calendar.getInstance();

        this.addIrcEntry(new IrcEntry("Hallo Welt", calendar.getTime(), new IrcUser("GameScripting")));
        this.addIrcEntry(new IrcEntry("Und noch mal", calendar.getTime(), new IrcUser("GameScripting")));
        this.addIrcEntry(new IrcEntry("na du auch hier? :)", calendar.getTime(), new IrcUser("testuser2")));
    }

    public void addIrcEntry(IrcEntry entry) {
        this._entryAdapter.add(entry);
    }
}
