package org.flipdot.flipdotapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import org.flipdot.flipdotapp.helpers.FontAwesomeHelper;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWebView();

        FontAwesomeHelper fontAwesomeHelper = new FontAwesomeHelper(this);
        Button refreshButton = (Button)this.findViewById(R.id.reloadStatusButton);
        fontAwesomeHelper.setIcon(refreshButton,"\uf021"); // refresh icon

        updateDoorStatus();
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
        Spacestatus status = SpacestatusLoader.getInstance().getStatus();
        int doorOpenImage = status.isOpen ? R.drawable.dooropen : R.drawable.doorclose;
        ImageView spaceOpenImage = (ImageView)this.findViewById(R.id.doorOpenImage);
        spaceOpenImage.setImageResource(doorOpenImage);
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
}
