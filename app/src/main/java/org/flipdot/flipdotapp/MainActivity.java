package org.flipdot.flipdotapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.flipdot.flipdotapp.helpers.Font;
import org.flipdot.flipdotapp.helpers.FontHelper;
import org.flipdot.flipdotapp.openDoor.OpenDoorConstants;
import org.flipdot.flipdotapp.openDoor.SshKeyGenerator;
import org.flipdot.flipdotapp.openDoor.SshOpenDoorTask;
import org.flipdot.flipdotapp.spacestatus.KnownHackersAdapter;
import org.flipdot.flipdotapp.spacestatus.Spacestatus;
import org.flipdot.flipdotapp.spacestatus.SpacestatusLoadTask;

import java.io.IOException;


public class MainActivity extends Activity {

    private FlipdotAuthentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFontForElements();

        updateSpaceStatus();

        SshKeyGenerator.ensureKeypairExists();

        this.authentication = new FlipdotAuthentication(this);
        this.authentication.authenticate();
    }

    private void setFontForElements() {
        ViewGroup layout = (ViewGroup)findViewById(R.id.layoutroot);
        for(int i = 0; i < layout.getChildCount(); i++){
            View view = layout.getChildAt(i);
            if(view instanceof TextView){
                TextView textView = (TextView)view;
                FontHelper.setFont(textView, Font.Default);
            }
        }
    }

    private void updateSpaceStatus() {

        final Context activity = this;
        final ImageButton spaceOpenImage = (ImageButton)this.findViewById(R.id.openDoorButton);
        final TextView peopleCountText = (TextView)this.findViewById(R.id.peopleCountText);
        final ListView onlinePeopleList = (ListView)this.findViewById(R.id.onlinePeopleList);
        final TextView otherPeopleCount = (TextView)this.findViewById(R.id.otherPeopleCount);
        SpacestatusLoadTask task = new SpacestatusLoadTask(){
            @Override
            public void onPostExecute(Spacestatus status){
                if(status.loadError) return;

                int doorOpenImage = status.isOpen ? R.drawable.doorstatus_open : R.drawable.doorstatus_closed;
                spaceOpenImage.setBackgroundResource(doorOpenImage);

                int hackerCount = status.knownHackers.size();
                int unknownHackerCount = status.unknownHackers;
                peopleCountText.setText(String.valueOf(hackerCount + unknownHackerCount));

                // make the elements invisible
                if(!status.isOpen){
                    onlinePeopleList.setVisibility(View.GONE);
                    otherPeopleCount.setVisibility(View.GONE);
                    return;
                }

                otherPeopleCount.setText("und "+unknownHackerCount+" andere ...");
                onlinePeopleList.setAdapter(new KnownHackersAdapter(activity, R.layout.known_hacker_item, status.knownHackers));
            }
        };

        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDoorOnClick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Open Door");
        alert.setMessage("Enter your Pin:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setTransformationMethod(new PasswordTransformationMethod());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();

                SshOpenDoorTask sshOpenDoorTask = new SshOpenDoorTask(OpenDoorConstants.PrivateKeyFilePath);
                sshOpenDoorTask.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alert.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    public void uploadSshKeyOnClick(MenuItem item) {
        Intent sendMailIntent = new Intent(Intent.ACTION_SEND);
        sendMailIntent.setType("message/rfc822");
        sendMailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "com@flipdot.org" });
        sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android App Türzugang");
        sendMailIntent.putExtra(Intent.EXTRA_TEXT, "Hiermit beantrage ich einen Flipdot Tür-Zugang :)");

        Uri uri = Uri.parse("file://" + OpenDoorConstants.PublicKeyFilePath);
        sendMailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            startActivity(Intent.createChooser(sendMailIntent, "Sending mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    public void refreshDoorStatus(View view) {
        this.updateSpaceStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.authentication.onActivityResult(requestCode, resultCode, data);
    }
}
