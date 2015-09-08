package com.example.naren.mango.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.naren.mango.R;

public class GifActivity extends AppCompatActivity {

    private String gifUrl;
    private WebView mGifWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        Bundle bundle = getIntent().getExtras();

        gifUrl = bundle.getString("gif");

        mGifWebView = (WebView) findViewById(R.id.gif_webView);

        mGifWebView.getSettings().setLoadsImagesAutomatically(true);
        mGifWebView.getSettings().setJavaScriptEnabled(true);
        mGifWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mGifWebView.getSettings().setLoadWithOverviewMode(true);
        mGifWebView.getSettings().setUseWideViewPort(true);
        mGifWebView.getSettings().setBuiltInZoomControls(true);
        mGifWebView.getSettings().setSupportZoom(true);
        // Configure the client to use when opening URLs
        mGifWebView.setWebViewClient(new MyBrowser());
        mGifWebView.loadUrl(gifUrl);


    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gif, menu);
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
