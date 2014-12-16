package org.flipdot.flipdotapp.spacestatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.flipdot.flipdotapp.R;

import java.util.ArrayList;

/**
 * Created by daniel on 16.12.14.
 */
public class KnownHackersAdapter extends ArrayAdapter<KnownHacker> {
    private final Context context;
    private final int resourceId;
    private final ArrayList<KnownHacker> knownHackers;

    public KnownHackersAdapter(Context context, int textViewResourceId, ArrayList<KnownHacker> list) {
        super(context, textViewResourceId, list);

        this.context = context;
        this.resourceId = textViewResourceId;
        this.knownHackers = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null) {
            return convertView;
        }

        KnownHacker knownHacker = this.knownHackers.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.resourceId, parent, false);

        TextView nick = (TextView)rowView.findViewById(R.id.knownHackerNick);
        nick.setText(knownHacker.Nick);

        return rowView;
    }
}

