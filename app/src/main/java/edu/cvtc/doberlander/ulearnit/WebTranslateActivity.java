package edu.cvtc.doberlander.ulearnit;

import static android.net.Uri.encode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.HashMap;

public class WebTranslateActivity extends AppCompatActivity {

    private WebView mGTranslateWebView;
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
        setTitle(getString(R.string.app_name) + " : " + getString(R.string.action_translate));

        // Initialized variables
        String firstLangCode = "";
        String secondLangCode = "";
        String secondLangWord = "";

        // Create hashmap for various languages and providing their google translate code
        HashMap<String, String> googleLanguageCodeMap = new HashMap<>();
        googleLanguageCodeMap.put("Chinese", "zh-CN");
        googleLanguageCodeMap.put("English", "en");
        googleLanguageCodeMap.put("Korean", "ko");
        googleLanguageCodeMap.put("French", "fr");
        googleLanguageCodeMap.put("German", "de");
        googleLanguageCodeMap.put("Greek", "el");
        googleLanguageCodeMap.put("Hmong", "hmn");
        googleLanguageCodeMap.put("Japanese", "ja");
        googleLanguageCodeMap.put("Spanish", "es");
        googleLanguageCodeMap.put("Tagalog", "tl");

        // Get the selected TranslationModel object from the Intent
        try {
            mSelectedEntry = getIntent().getExtras().getParcelable("SelectedItem");
        } catch (NullPointerException ex) {
            System.out.println("Unable to open web activity. Error: " + ex.getMessage());
        }
        if(mSelectedEntry != null) {
            // Check to see if the first language is listed in the keys available in this app
            if(googleLanguageCodeMap.containsKey(mSelectedEntry.getFirstLanguage())) {
                // Key is found, get the value to use it to create the url
                firstLangCode = googleLanguageCodeMap.get(mSelectedEntry.getFirstLanguage());
            } else {
                // Display an alert to the user stating that the language could not be loaded
                Toast.makeText(this, mSelectedEntry.getFirstLanguage() + ":" +
                        R.string.web_translate_language_not_avail, Toast.LENGTH_LONG).show();
            }

            // Check to see if the second language is listed in the keys available for this app
            if(googleLanguageCodeMap.containsKey(mSelectedEntry.getSecondLanguage())) {
                // Key is found, get the value to use it to create the url
                secondLangCode = googleLanguageCodeMap.get(mSelectedEntry.getSecondLanguage());
            } else {
                // Display an alert to the user stating that the language could not be loaded
                Toast.makeText(this, mSelectedEntry.getFirstLanguage() + ":" +
                        R.string.web_translate_language_not_avail, Toast.LENGTH_LONG).show();
            }

            // Get the first language word to input to use in the translation
            secondLangWord = mSelectedEntry.getSecondLanguageEntry();

            // Encode the word or phrase to be passed via http
            secondLangWord = encode (secondLangWord, String.valueOf(Charset.defaultCharset()));
        } else {
            // Set the default values that match the languages in the startup database
            firstLangCode = "en";
            secondLangCode = "tl";
        }

        // Create the url string
        String url = "https://translate.google.com?sl=" + secondLangCode + "&tl=" +
                firstLangCode + "&text=" + secondLangWord + "&op=translate";

        // Setup the WebView to be able to open the URL
        // Web View resource: https://developer.android.com/guide/webapps/webview
        mGTranslateWebView = findViewById(R.id.webView_GTranslate);
        mGTranslateWebView.setWebViewClient(new WebViewClient());
        // Enable the website to use JavaScript, which is a requirement for Google Translate to work.
        mGTranslateWebView.getSettings().setJavaScriptEnabled(true);
        // Open the translation website
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