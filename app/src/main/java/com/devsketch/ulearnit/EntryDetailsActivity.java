package com.devsketch.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class EntryDetailsActivity extends AppCompatActivity {

    // Member level variables
    private TranslationModel mSelectedEntry;
    private String dbResult = "Failed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        String category = "";
        int selectedItemId = 0;

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        try {
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                // Set the Activity Title with instruction how to cancel
                actionBar.setDisplayShowTitleEnabled(true);
            }
        } catch (NullPointerException ex) {
            System.out.println("Error showing back button on Action Bar. Error: " + ex.getMessage());
        }

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.flashcards_menuItem);
        // Show the text below each icon
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
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
                    case R.id.flashcards_menuItem:
                        displayToast("Review is unavailable at this time.");
                        return false;
                    case R.id.search_menuItem:
                        displayToast("Search is unavailable at this time.");
                        return false;
                    case R.id.quiz_menuItem:
                        displayToast("Quiz is unavailable at this time.");
                        return false;
                    case R.id.profile_menuItem:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Get the selected TranslationModel object from the Intent
        mSelectedEntry = getIntent().getExtras().getParcelable("SelectedItem");
        // Get the id of the selected item
        selectedItemId = getIntent().getExtras().getInt("selectedId");

        // Get the entry's category
        category = mSelectedEntry.getCategory();

        // Set the Title of the page with the Category and Action
        setTitle(category + ": " + "Entry Details");

        // populate the entry fields
        if(mSelectedEntry != null) {
            // fill the entry fields to edit
            TextView firstLang = findViewById(R.id.textView_Details_Lang1);
            TextView firstLangEntry = findViewById(R.id.textView_Details_Lang1Entry);
            TextView firstLangEntryRomanization = findViewById(R.id.textView_Details_Lang1EntryRomanized);
            TextView secondLang = findViewById(R.id.textView_Details_Lang2);
            TextView secondLangEntry = findViewById(R.id.textView_Details_Lang2Entry);
            TextView secondLangEntryRomanization = findViewById(R.id.textView_Details_Lang2EntryRomanized);

            TextView entryType = findViewById(R.id.textView_Details_EntryType);
            TextView entryTense = findViewById(R.id.textView_Details_Tense);
            TextView formalityLevel = findViewById(R.id.textView_Details_Formality);
            TextView isPlural = findViewById(R.id.textView_Details_IsPlural);
            TextView entryGender = findViewById(R.id.textView_Details_Gender);
            //CheckBox onQuickList = findViewById(R.id.checkBox_IsOnQuickList);
            //CheckBox isArchived = findViewById(R.id.checkBox_IsArchived);
            TextView percentageLearned = findViewById(R.id.textView_Details_PercentLearned);

            TextView summaryNotes = findViewById(R.id.textView_Details_SummaryNotes);
            TextView notes = findViewById(R.id.textView_Details_Notes);
            TextView firstLangEntryExample = findViewById(R.id.textView_Details_Lang1EntryExample);
            TextView secondLangEntryExample = findViewById(R.id.textView_Details_Lang2EntryExample);

            TextView entryCategory = findViewById(R.id.textView_Details_Category);
            TextView entrySubCategory = findViewById(R.id.textView_Details_SubCategory);
            TextView entryUnit = findViewById(R.id.textView_Details_Unit);
            TextView entryLesson = findViewById(R.id.textView_Details_Lesson);

            TextView dateLastModified = findViewById(R.id.textView_Label_Details_DateModified);

            // Make Romanization or Example visible if has a value
            if(!mSelectedEntry.getFirstLanguageEntryRomanized().isEmpty()) {
                firstLangEntryRomanization.setVisibility(View.VISIBLE);
            }
            if(!mSelectedEntry.getFirstLanguageExample().isEmpty()) {
                firstLangEntryExample.setVisibility(View.VISIBLE);
            }
            if(!mSelectedEntry.getSecondLanguageEntryRomanized().isEmpty()) {
                secondLangEntryRomanization.setVisibility(View.VISIBLE);
            }
            if(!mSelectedEntry.getSecondLanguageExample().isEmpty()) {
                secondLangEntryExample.setVisibility(View.VISIBLE);
            }


            //firstLang.setSelection(adapter.getPosition(mSelectedEntry.getFirstLanguage()));
            firstLang.setText(mSelectedEntry.getFirstLanguage());
            firstLangEntry.setText(mSelectedEntry.getFirstLanguageEntry());
            firstLangEntryRomanization.setText(mSelectedEntry.getFirstLanguageEntryRomanized());
            firstLangEntryExample.setText(mSelectedEntry.getFirstLanguageExample());
            //secondLang.setSelection(adapter.getPosition(mSelectedEntry.getSecondLanguage()));
            secondLang.setText(mSelectedEntry.getSecondLanguage());
            secondLangEntry.setText(mSelectedEntry.getSecondLanguageEntry());
            secondLangEntryRomanization.setText(mSelectedEntry.getSecondLanguageEntryRomanized());
            secondLangEntryExample.setText(mSelectedEntry.getSecondLanguageExample());
            entryType.setText(mSelectedEntry.getEntryType());
            entryTense.setText(mSelectedEntry.getTense());
            formalityLevel.setText(mSelectedEntry.getFormality());
            isPlural.setText((mSelectedEntry.getIsPlural()) ? "Plural" : "Singular");
            entryGender.setText(mSelectedEntry.getGender());
            percentageLearned.setText(Integer.toString(mSelectedEntry.getPercentLearned()));
            summaryNotes.setText(mSelectedEntry.getSummaryNotes());
            notes.setText(mSelectedEntry.getNotes().replace("\\n", Objects.requireNonNull(System.getProperty("line.separator"))));

            entryCategory.setText(mSelectedEntry.getCategory());
            entrySubCategory.setText(mSelectedEntry.getSubCategory());
            entryUnit.setText("Not Implemented Yet");
            entryLesson.setText("Not Implemented Yet");
            //onQuickList.setChecked(mSelectedEntry.getOnQuickList());
            //isArchived.setChecked(mSelectedEntry.getArchived());
            dateLastModified.setText(mSelectedEntry.getModifiedDate());

            // Set the object with the appropriate item id from the database
            mSelectedEntry.setId(selectedItemId);

            // Set the object with the selected category
            mSelectedEntry.setCategory(category);
        } else {
            dbResult = "Failed: No data passed";
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("EntryDetailsActivity", "Back button pressed");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}