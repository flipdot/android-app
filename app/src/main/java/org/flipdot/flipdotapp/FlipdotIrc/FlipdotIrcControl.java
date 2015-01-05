package org.flipdot.flipdotapp.FlipdotIrc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.flipdot.flipdotapp.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FlipdotIrcControl extends LinearLayout {

    private IrcEntryAdapter _entryAdapter;

    public FlipdotIrcControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.flipdot_irc_control, this);

        this._entryAdapter = new IrcEntryAdapter(context, R.layout.irc_chat_entry, new ArrayList<IrcEntry>());

        ListView ircEntriesList = (ListView)this.findViewById(R.id.ircEntriesList);
        ircEntriesList.setAdapter(this._entryAdapter);

        loadFlipdotIrcBacklog();
    }

    public void addIrcEntry(IrcEntry entry) {
        this._entryAdapter.add(entry);
    }

    private void loadFlipdotIrcBacklog() {
        AsyncTask<Object, Object, List<IrcEntry>> task = new AsyncTask<Object, Object, List<IrcEntry>>() {
            @Override
            protected List<IrcEntry> doInBackground(Object[] params) {
                ArrayList<IrcEntry> ircEntries = new ArrayList<IrcEntry>();

                try {
                    HttpGet httpGet = new HttpGet("http://flipdot.org/android-app/irc/backlog");
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    String responseString = new DefaultHttpClient().execute(httpGet, responseHandler);

                    JSONArray ircBacklogList = new JSONArray(responseString);
                    for (int i = 0; i < ircBacklogList.length(); i++){
                        JSONObject ircEntry = ircBacklogList.getJSONObject(i);

                        String from = ircEntry.getString("from");
                        long time = ircEntry.getLong("time");
                        String msg = ircEntry.getString("msg");

                        long unixMilliseconds = time * 1000L; // x 1000 because the date ctor expects milliseconds, not seconds
                        IrcEntry entry = new IrcEntry(msg, new Date(unixMilliseconds), new IrcUser(from));
                        ircEntries.add(entry);
                    }

                    return ircEntries;
                } catch(Exception ex) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<IrcEntry> ircEntries) {
                if(ircEntries == null){
                    return;
                }

                for(IrcEntry entry : ircEntries) {
                    FlipdotIrcControl.this.addIrcEntry(entry);
                }

                // scroll to the last item
                final ListView entriesList = (ListView)FlipdotIrcControl.this.findViewById(R.id.ircEntriesList);
                entriesList.post(new Runnable() {
                    @Override
                    public void run() {
                        entriesList.setSelection(entriesList.getCount() - 1);
                    }
                });
            }
        };
        task.execute();
    }
}
