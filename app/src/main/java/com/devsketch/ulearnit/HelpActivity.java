package com.devsketch.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Show the Activity Title
            actionBar.setDisplayShowTitleEnabled(true);
            // Set the title of the page
            actionBar.setTitle(getString(R.string.app_name) + " : " + getString(R.string.action_help));
        }

        // Access version name from BuildConfig
        String version = BuildConfig.VERSION_NAME;

        // Display version name in a TextView or any other view
        TextView versionTextView = findViewById(R.id.textView_Version);
        versionTextView.setText(getString(R.string.version_number_text, version));
    }

    // Event that is enabled when pressing the back button in the ActionBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}