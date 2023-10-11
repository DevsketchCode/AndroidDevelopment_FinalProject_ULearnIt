package edu.cvtc.doberlander.ulearnit;

import static android.net.Uri.encode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.HashMap;

public class WebAIActivity  extends AppCompatActivity{
        private WebView mAIWebView;

        private TranslationModel mSelectedEntry = null;

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
            setTitle(getString(R.string.app_name) + " : " + getString(R.string.action_ai));

            // Create the url string
            String url = "https://chat.openai.com";

            // Setup the WebView to be able to open the URL
            // Web View resource: https://developer.android.com/guide/webapps/webview
            mAIWebView = findViewById(R.id.webView_Translate);

            // Enable the website to use JavaScript
            WebSettings settings = mAIWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            settings.setSafeBrowsingEnabled(true);

            // Change the user agent string
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36";
            settings.setUserAgentString(userAgent);

            //mAIWebView.setWebViewClient(new WebViewClient());
            // Enable the website to use JavaScript, which is a requirement for Google Translate to work.
            //mAIWebView.getSettings().setJavaScriptEnabled(true);
            // Open the translation website
            mAIWebView.loadUrl(url);

            // This opens the website in the browser, so the activity can now be closed at this time.
            // May need to remove this if ABLE to integrate in the app in the future.
            // At this time it was not possible due to browser authentication issues.

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
            if(mAIWebView.canGoBack()) {
                // Go back on the website
                mAIWebView.goBack();
            } else {
                // Go back the app
                super.onBackPressed();
            }
        }
}
