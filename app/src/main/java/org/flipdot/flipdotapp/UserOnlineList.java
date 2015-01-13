package org.flipdot.flipdotapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.flipdot.flipdotapp.spacestatus.KnownHackersAdapter;
import org.flipdot.flipdotapp.spacestatus.Spacestatus;


public class UserOnlineList extends LinearLayout {

    private final Context context;
    private Spacestatus spacestatus;

    public UserOnlineList(Context context, AttributeSet attrs){
        super(context, attrs);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.users_online_list, this);
    }

    private void updateView() {
        final ListView onlinePeopleList = (ListView) this.findViewById(R.id.onlinePeopleList);
        final TextView otherPeopleCount = (TextView) this.findViewById(R.id.otherPeopleCount);

        int unknownHackerCount = this.spacestatus.unknownHackers;
        otherPeopleCount.setText("und " + unknownHackerCount + " andere ...");
        onlinePeopleList.setAdapter(new KnownHackersAdapter(UserOnlineList.this.context, R.layout.known_hacker_item, this.spacestatus.knownHackers));
    }

    public void setSpacestatus(Spacestatus spacestatus) {
        this.spacestatus = spacestatus;
        updateView();
    }
}
