package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import edu.cvtc.doberlander.ulearnit.databinding.ActivityMainBinding;

public class ProfileActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ArrayAdapter<CharSequence> firstLangAdapter = null;
    private ArrayAdapter<CharSequence> secondLangAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Set the Activity Title
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("Profile");

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.profile_menuItem);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.categories_menuItem:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.quiz_menuItem:
                        return true;
                    case R.id.profile_menuItem:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        // Set Language Dropdown
        firstLangAdapter= ArrayAdapter.createFromResource(this, R.array.languages, R.layout.dropdown_item);
        AutoCompleteTextView firstLangAutoComplete = findViewById(R.id.prefs_FirstLanguage);
        firstLangAutoComplete.setAdapter(firstLangAdapter);

        secondLangAdapter= ArrayAdapter.createFromResource(this, R.array.languages, R.layout.dropdown_item);
        AutoCompleteTextView secondLangAutoComplete = findViewById(R.id.prefs_SecondLanguage);
        secondLangAutoComplete.setAdapter(secondLangAdapter);


        // Retrieve Preferences
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences prefs = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        firstLangAutoComplete.setText(prefs.getString("FirstLanguage", ""), false);
        secondLangAutoComplete.setText(prefs.getString("SecondLanguage", ""), false);


        // Save Preferences
        Button save = findViewById(R.id.btn_save_preferences);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save Preferences
                //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                SharedPreferences prefs = ProfileActivity.this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefs_editor = prefs.edit();
                prefs_editor.putString("FirstLanguage", firstLangAutoComplete.getText().toString());
                prefs_editor.putString("SecondLanguage", secondLangAutoComplete.getText().toString());

                prefs_editor.apply();
                Toast.makeText(ProfileActivity.this, "Preferences Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}