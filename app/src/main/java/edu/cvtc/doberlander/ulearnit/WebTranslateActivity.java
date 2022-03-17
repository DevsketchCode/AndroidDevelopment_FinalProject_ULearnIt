package edu.cvtc.doberlander.ulearnit;

import static android.net.Uri.encode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;
import java.nio.charset.Charset;

public class WebTranslateActivity extends AppCompatActivity {

    private WebView mGTranslateWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_translate);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the title of the page
        setTitle(getString(R.string.app_name) + " : " + getString(R.string.action_translate));

        String firstLangCode = "en";
        String secondLangCode = "tl";
        String inputWord = encode ("Good morning", String.valueOf(Charset.defaultCharset()));

        String url = "https://translate.google.com?sl=" + firstLangCode + "&tl=" +
                secondLangCode + "&text=" + inputWord + "&op=translate";

        // Web View resource: https://developer.android.com/guide/webapps/webview
        mGTranslateWebView = findViewById(R.id.webView_GTranslate);
        mGTranslateWebView.setWebViewClient(new WebViewClient());
        mGTranslateWebView.getSettings().setJavaScriptEnabled(true);
        mGTranslateWebView.loadUrl(url);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // When the back button is pressed, determine how to respond
        if(mGTranslateWebView.canGoBack()) {
            // Go back on the website
            mGTranslateWebView.goBack();
        } else {
            // Go back the app
            super.onBackPressed();
        }
    }
}