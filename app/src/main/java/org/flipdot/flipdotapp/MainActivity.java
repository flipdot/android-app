package org.flipdot.flipdotapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import org.flipdot.flipdotapp.helpers.Font;
import org.flipdot.flipdotapp.helpers.FontHelper;
import org.flipdot.flipdotapp.openDoor.OpenDoorConstants;
import org.flipdot.flipdotapp.openDoor.SshKeyGenerator;
import org.flipdot.flipdotapp.openDoor.SshOpenDoorTask;
import org.flipdot.flipdotapp.spacestatus.Spacestatus;
import org.flipdot.flipdotapp.spacestatus.SpacestatusLoadTask;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFontForElements();

        initWebView();
        updateDoorStatus();

        SshKeyGenerator.ensureKeypairExists();
    }

    private void setFontForElements() {
        ViewGroup layout = (ViewGroup)findViewById(R.id.layoutroot);
        for(int i = 0; i < layout.getChildCount(); i++){
            View view = layout.getChildAt(i);
            if(view instanceof TextView){
                TextView textView = (TextView)view;
                FontHelper.setFont(textView, Font.IsocpeurRegular);
            }
        }
    }

    private void initWebView() {
        WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        webView.loadUrl("http://flipdot.org");
    }

    private void updateDoorStatus() {

        final ImageButton spaceOpenImage = (ImageButton)this.findViewById(R.id.openDoorButton);
        SpacestatusLoadTask task = new SpacestatusLoadTask(){
            @Override
            public void onPostExecute(Spacestatus status){
                if(status.loadError) return;

                int doorOpenImage = status.isOpen ? R.drawable.doorstatus_open : R.drawable.doorstatus_closed;
                spaceOpenImage.setBackgroundResource(doorOpenImage);
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
        SshOpenDoorTask sshOpenDoorTask = new SshOpenDoorTask(OpenDoorConstants.PrivateKeyFilePath);
        sshOpenDoorTask.execute();
    }

    public void uploadSshKeyOnClick(MenuItem item) {
        SshKeyGenerator.pushKeyToServer();
    }

    public void refreshDoorStatus(View view) {
        this.updateDoorStatus();
    }
}
