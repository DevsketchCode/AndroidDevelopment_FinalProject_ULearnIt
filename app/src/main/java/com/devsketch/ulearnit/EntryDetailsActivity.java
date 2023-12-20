package com.devsketch.ulearnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

    public static final String EXTRA_MESSAGE = "com.devsketch.ulearnit.extra.MESSAGE";

    // Member level variables
    private String mCategory = "";
    private TranslationModel mSelectedEntry;
    private static int mSelectedItemID = 0;
    private String dbResult = "Failed";

    // Public variables
    public static final int LAUNCH_ENTRY_MODIFIER_ACTIVITY = 1;
    public static Menu mModifyMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

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
        mSelectedItemID = getIntent().getExtras().getInt("selectedId");

        // Get the entry's category
        mCategory = mSelectedEntry.getCategory();

        // Set the Title of the page with the Category and Action
        setTitle(mCategory + ": " + "Entry Details");

        // populate the entry fields
        if(mSelectedEntry != null) {
            populateData();
        } else {
            dbResult = "Failed: No data passed";
        }
    }

    private void populateData() {
        // entry details fields
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

        // Containers used for conditional visibility
        ConstraintLayout containerFirstLangEntryRomanization = findViewById(R.id.constraintLayout_Details_Container_Lang1EntryRomanized);
        ConstraintLayout containerSecondLangEntryRomanization = findViewById(R.id.constraintLayout_Details_Container_Lang2EntryRomanized);
        ConstraintLayout containerEntryTense = findViewById(R.id.constraintLayout_Details_Container_Tense);
        ConstraintLayout containerFormalityLevel = findViewById(R.id.constraintLayout_Details_Container_Formality);
        ConstraintLayout containerIsPlural = findViewById(R.id.constraintLayout_Details_Container_IsPlural);
        ConstraintLayout containerEntryGender = findViewById(R.id.constraintLayout_Details_Container_Gender);
        ConstraintLayout containerExample = findViewById(R.id.constraintLayout_Details_Container_Example);
        ConstraintLayout containerSubCategory = findViewById(R.id.constraintLayout_Details_Container_SubCategory);
        ConstraintLayout containerEntryUnit = findViewById(R.id.constraintLayout_Details_Container_Unit);
        ConstraintLayout containerEntryLesson = findViewById(R.id.constraintLayout_Details_Container_Lesson);

        // Special case conditions
        boolean showPlural = mSelectedEntry.getEntryType().equals("Noun") || mSelectedEntry.getEntryType().equals("Pronoun") || mSelectedEntry.getEntryType().equals("Verb");

        // Set Items as Visible based on if they have a value populated
        setVisibility(containerFirstLangEntryRomanization, mSelectedEntry.getFirstLanguageEntryRomanized().isEmpty());
        setVisibility(containerSecondLangEntryRomanization, mSelectedEntry.getSecondLanguageEntryRomanized().isEmpty());
        setVisibility(containerEntryTense, mSelectedEntry.getTense().isEmpty());
        setVisibility(containerFormalityLevel, mSelectedEntry.getFormality().isEmpty());
        setVisibility(containerIsPlural, showPlural);
        setVisibility(containerEntryGender, mSelectedEntry.getGender().isEmpty());
        setVisibility(containerExample, (mSelectedEntry.getFirstLanguageExample().isEmpty() && mSelectedEntry.getSecondLanguageExample().isEmpty()));
        setVisibility(containerSubCategory, mSelectedEntry.getSubCategory().isEmpty());
        setVisibility(containerEntryUnit, false);
        setVisibility(containerEntryLesson, false);

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
        entryUnit.setText("N/A");
        entryLesson.setText("N/A");
        //onQuickList.setChecked(mSelectedEntry.getOnQuickList());
        //isArchived.setChecked(mSelectedEntry.getArchived());
        dateLastModified.setText(mSelectedEntry.getModifiedDate());

        // Set the object with the appropriate item id from the database
        mSelectedEntry.setId(mSelectedItemID);

        // Set the object with the selected category
        mSelectedEntry.setCategory(mCategory);
    }


    // Prepare the options menu to be accessed from the Adapter
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mModifyMenu = menu;
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        // Set the edit item so that it can be visible when an item is highlighted.
        menu.findItem(R.id.action_editEntry).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Initialize Intent
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        Intent modifierIntent = new Intent(this, EntryModifierActivity.class);
        Bundle modifierBundle = new Bundle();
        String modifyType;
        String category;

        // Determine which Intent combination to pass and activity to launch
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("EntryDetailsActivity", "Back button pressed");
                finish();
                return true;
            case R.id.action_newEntry:
                // Bundle up the data
                modifyType = getString(R.string.action_new);
                modifierBundle.putString("category", mCategory);
                modifierBundle.putString("message", getString(R.string.new_entry_modifier_message));
                modifierBundle.putString("modifyType", modifyType);
                // Put the bundle in an intent
                modifierIntent.putExtras(modifierBundle);
                // Start the activity and pass the bundled intent, expecting success result
                startActivityForResult(modifierIntent,LAUNCH_ENTRY_MODIFIER_ACTIVITY);
                return true;
            case R.id.action_editEntry:
                // Bundle up the data
                modifyType = getString(R.string.action_edit);
                modifierBundle.putInt("selectedId", mSelectedItemID);
                modifierBundle.putString("category", mCategory);
                modifierBundle.putString("message", getString(R.string.edit_entry_modifier_message));
                modifierBundle.putString("modifyType", modifyType);
                modifierBundle.putParcelable("SelectedItem", mSelectedEntry);
                // Put the bundle in an intent
                modifierIntent.putExtras(modifierBundle);
                // Start the activity and pass the bundled intent, expecting success result
                startActivityForResult(modifierIntent,LAUNCH_ENTRY_MODIFIER_ACTIVITY);
                return true;
            case R.id.action_favorites:
                // Favorites are separate so the category is set manually here
                category = getString(R.string.action_favorites);
                categoryIntent.putExtra(EXTRA_MESSAGE, category);
                startActivity(categoryIntent);
                return true;
            default:
                // Do Nothing
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_ENTRY_MODIFIER_ACTIVITY && resultCode == RESULT_OK) {
            // Retrieve the updated entry from the returned Intent
            TranslationModel updatedEntry = data.getParcelableExtra("Entry");

            // Update the mSelectedEntry with the new data
            if (updatedEntry != null) {
                mSelectedEntry = updatedEntry;

                // Call a method to update UI elements with the new data
                populateData();
                displayToast(data.getStringExtra("Result"));
            }
        }
    }

    private void setVisibility (ConstraintLayout container, Boolean isEmpty) {
        if(isEmpty) {
            container.setVisibility(View.GONE);
        } else {
            container.setVisibility(View.VISIBLE);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}