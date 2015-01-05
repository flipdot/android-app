package org.flipdot.flipdotapp.FlipdotIrc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.flipdot.flipdotapp.R;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by daniel on 05.01.15.
 */
public class IrcEntryAdapter extends ArrayAdapter<IrcEntry> {
    private final int resourceId;

    public IrcEntryAdapter(Context context, int textViewResourceId, List<IrcEntry> objects) {
        super(context, textViewResourceId, objects);

        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resourceId, parent, false);
        }

        IrcEntry ircEntry = this.getItem(position);

        // time
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(this.getContext());
        TextView time = (TextView)view.findViewById(R.id.ircEntryTime);
        time.setText(dateFormat.format(ircEntry.Time));

        // nickname
        TextView nick = (TextView)view.findViewById(R.id.ircUserNick);
        nick.setText(ircEntry.User.Nick);

        // message
        TextView msg = (TextView)view.findViewById(R.id.ircEntryText);
        msg.setText(ircEntry.Msg);

        return view;
    }
}
