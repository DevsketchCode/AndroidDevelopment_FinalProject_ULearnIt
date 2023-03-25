package edu.cvtc.doberlander.ulearnit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.Objects;

public class EntryModifierActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayAdapter<CharSequence> adapter = null;
    private ArrayAdapter<CharSequence> categoryAdapter = null;
    private ArrayAdapter<CharSequence> entryTypeAdapter = null;
    private ArrayAdapter<CharSequence> genderAdapter = null;
    private ArrayAdapter<CharSequence> tenseAdapter = null;
    private ArrayAdapter<CharSequence> formalityAdapter = null;
    private Button firstLangRomanizeButton = null;
    private Button secondLangRomanizeButton = null;
    private Button langExampleButton = null;
    // Member level variables
    private TranslationModel mSelectedEntry;
    private boolean isNewEntry = false;
    private String dbResult = "Failed";
    private String defaultFirstLanguage = "English";
    private String defaultSecondLanguage = "Korean";
    private String defaultEntryType = "Noun";
    private String defaultTense = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_modifier);

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

        // Set onClickListeners to the buttons
        Button saveButton = findViewById(R.id.btn_Save);
        saveButton.setOnClickListener(this);
        Button archiveButton = findViewById(R.id.btn_Archive);
        archiveButton.setOnClickListener(this);
        Button deleteButton = findViewById(R.id.btn_Delete);
        deleteButton.setOnClickListener(this);
        firstLangRomanizeButton = findViewById(R.id.btn_FirstLangDisplayRomanization);
        firstLangRomanizeButton.setOnClickListener(this);
        langExampleButton = findViewById(R.id.btn_LangDisplayExample);
        langExampleButton.setOnClickListener(this);
        secondLangRomanizeButton = findViewById(R.id.btn_SecondLangDisplayRomanization);
        secondLangRomanizeButton.setOnClickListener(this);

        // Setup Language Spinners
        adapter=ArrayAdapter.createFromResource(this, R.array.languages, R.layout.dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        AutoCompleteTextView firstLang = findViewById(R.id.firstLangDropdown);
        firstLang.setAdapter(adapter);
        TextInputLayout firstLangTextInputLayout = findViewById(R.id.firstLangDropdownLayout);

        AutoCompleteTextView secondLang = findViewById(R.id.secondLangDropdown);
        secondLang.setAdapter(adapter);
        TextInputLayout secondLangTextInputLayout = findViewById(R.id.secondLangDropdownLayout);
        //Spinner firstLang=findViewById(R.id.spinner_FirstLang);
        //firstLang.setAdapter(adapter);
        //Spinner secondLang=findViewById(R.id.spinner_SecondLang);
        //secondLang.setAdapter(adapter);

        categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryOptions, R.layout.dropdown_item);
        AutoCompleteTextView categoryAutoCompleteTextView =findViewById(R.id.categoryDropdown);
        categoryAutoCompleteTextView.setAdapter(categoryAdapter);
        TextInputLayout categoryTextInputLayout = findViewById(R.id.categoryDropdownLayout);

        //entryTypeAdapter=ArrayAdapter.createFromResource(this, R.array.entryTypeOptions, android.R.layout.simple_spinner_item);
        //Spinner entryType=findViewById(R.id.spinner_EntryType);
        //entryType.setAdapter(entryTypeAdapter);
        entryTypeAdapter= ArrayAdapter.createFromResource(this, R.array.entryTypeOptions, R.layout.dropdown_item);
        AutoCompleteTextView entryType=findViewById(R.id.entryTypeDropdown);
        entryType.setAdapter(entryTypeAdapter);
        TextInputLayout entryTypeTextInputLayout = findViewById(R.id.entryTypeDropdownLayout);

        genderAdapter= ArrayAdapter.createFromResource(this, R.array.genderOptions, R.layout.dropdown_item);
        AutoCompleteTextView gender=findViewById(R.id.genderDropdown);
        gender.setAdapter(genderAdapter);
        TextInputLayout genderTextInputLayout = findViewById(R.id.genderDropdownLayout);

        tenseAdapter= ArrayAdapter.createFromResource(this, R.array.tenseOptions, R.layout.dropdown_item);
        AutoCompleteTextView tense=findViewById(R.id.tenseDropdown);
        tense.setAdapter(tenseAdapter);
        TextInputLayout tenseTextInputLayout = findViewById(R.id.tenseDropdownLayout);

        formalityAdapter = ArrayAdapter.createFromResource(this, R.array.formalityOptions, R.layout.dropdown_item);
        AutoCompleteTextView formality=findViewById(R.id.formalityDropdown);
        formality.setAdapter(formalityAdapter);
        TextInputLayout formalityTextInputLayout = findViewById(R.id.formalityDropdownLayout);

        // Get the previous activities category
        String category = getIntent().getExtras().getString("category");

        // Get the modification type from the Intent
        String modifyType = getIntent().getExtras().getString("modifyType");

        // Set the Title of the page with the Category and Action
        setTitle(category + ": " + modifyType + " entry");

        // Get the message and set it.
        String message = getIntent().getExtras().getString("message");
        TextView header = findViewById(R.id.entryModifierHeaderText);
        header.setText(message);

        // Get the selected TranslationModel object from the Intent
        mSelectedEntry = getIntent().getExtras().getParcelable("SelectedItem");

        // Get the id of the selected item
        int selectedItemId = getIntent().getExtras().getInt("selectedId");

        // Perform tasks depending on modifyType that has been passed
        if(modifyType.equals("Edit")) {
            // if there is a translation item found, then populate the fields
            if(mSelectedEntry != null) {
                // fill the entry fields to edit
                EditText firstLangEntry = findViewById(R.id.editText_FirstLangEntry);
                EditText firstLangEntryRomanization = findViewById(R.id.editText_FirstLangEntryRomanization);
                EditText firstLangEntryExample = findViewById(R.id.editText_FirstLangEntryExample);
                EditText secondLangEntry = findViewById(R.id.editText_SecondLangEntry);
                EditText secondLangEntryRomanization = findViewById(R.id.editText_SecondLangEntryRomanization);
                EditText secondLangEntryExample = findViewById(R.id.editText_SecondLangEntryExample);
                EditText notes = findViewById(R.id.editText_Notes);
                CheckBox isPlural = findViewById(R.id.checkBox_IsPlural);
                CheckBox onQuickList = findViewById(R.id.checkBox_IsOnQuickList);
                CheckBox isArchived = findViewById(R.id.checkBox_IsArchived);
                TextView percentageLearned = findViewById(R.id.textView_PercentLearned);
                TextView dateLastModifiedLabel = findViewById(R.id.textView_DateModified_label);
                TextView dateLastModified = findViewById(R.id.textView_DateModified);

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
                categoryAutoCompleteTextView.setText(mSelectedEntry.getCategory(), false);
                firstLang.setText(mSelectedEntry.getFirstLanguage(), false);
                firstLangEntry.setText(mSelectedEntry.getFirstLanguageEntry());
                firstLangEntryRomanization.setText(mSelectedEntry.getFirstLanguageEntryRomanized());
                firstLangEntryExample.setText(mSelectedEntry.getFirstLanguageExample());
                //secondLang.setSelection(adapter.getPosition(mSelectedEntry.getSecondLanguage()));
                secondLang.setText(mSelectedEntry.getSecondLanguage(), false);
                secondLangEntry.setText(mSelectedEntry.getSecondLanguageEntry());
                secondLangEntryRomanization.setText(mSelectedEntry.getSecondLanguageEntryRomanized());
                secondLangEntryExample.setText(mSelectedEntry.getSecondLanguageExample());
                entryType.setText(mSelectedEntry.getEntryType(), false);
                gender.setText(mSelectedEntry.getGender(), false);
                tense.setText(mSelectedEntry.getTense(),false);
                formality.setText(mSelectedEntry.getFormality(), false);
                notes.setText(mSelectedEntry.getNotes().replace("\\n", Objects.requireNonNull(System.getProperty("line.separator"))));
                isPlural.setChecked(mSelectedEntry.getIsPlural());
                onQuickList.setChecked(mSelectedEntry.getOnQuickList());
                isArchived.setChecked(mSelectedEntry.getArchived());
                percentageLearned.setText(Integer.toString(mSelectedEntry.getPercentLearned()));
                dateLastModified.setText(mSelectedEntry.getModifiedDate().toString());
                dateLastModifiedLabel.setVisibility(View.VISIBLE);
                dateLastModified.setVisibility(View.VISIBLE);

                //Adjust color for empty dropdowns
                CheckDropdownSelection(firstLangTextInputLayout, firstLang);
                CheckDropdownSelection(secondLangTextInputLayout, secondLang);
                CheckDropdownSelection(entryTypeTextInputLayout, entryType);
                CheckDropdownSelection(genderTextInputLayout, gender);
                CheckDropdownSelection(tenseTextInputLayout, tense);
                CheckDropdownSelection(formalityTextInputLayout, formality);

                // Set the object with the appropriate item id from the database
                mSelectedEntry.setId(selectedItemId);

                // Set the object with the selected category
                mSelectedEntry.setCategory(category);
            } else {
                dbResult = "Failed: No data passed";
            }
        } else if (modifyType.equals("New")) {
            // Create new translation object with selected Category and default favorite value of 0
            mSelectedEntry = new TranslationModel(category, 0);
            // The rest will be filled in using the function below, when click is saved and
            // after the form has been filled out.
            // Set Dropdown to the current category for new entries
            categoryAutoCompleteTextView.setText(category, false);
            firstLang.setText(defaultFirstLanguage, false);
            secondLang.setText(defaultSecondLanguage, false); // Leave this blank so the dropdown title shows up before selection made
            entryType.setText(""); // Leave this blank so the dropdown title shows up before selection made
            tense.setText(""); // Leave this blank so the dropdown title shows up before selection made
            // New entry to true, so the proper db function can be ran.
            isNewEntry = true;

            // Hide delete button, as it's not needed for new records
            archiveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);

        }
    }

    private TranslationModel getEntryFormValues() {
        // Get the fields
        //Spinner firstLang = findViewById(R.id.spinner_FirstLang);
        AutoCompleteTextView categoryAutoComplete = findViewById(R.id.categoryDropdown);
        AutoCompleteTextView firstLang = findViewById(R.id.firstLangDropdown);
        EditText firstLangEntry = findViewById(R.id.editText_FirstLangEntry);
        EditText firstLangEntryRomanized = findViewById(R.id.editText_FirstLangEntryRomanization);
        EditText firstLangEntryExample = findViewById(R.id.editText_FirstLangEntryExample);

        AutoCompleteTextView secondLang = findViewById(R.id.secondLangDropdown);
        EditText secondLangEntry = findViewById(R.id.editText_SecondLangEntry);
        EditText secondLangEntryRomanized = findViewById(R.id.editText_SecondLangEntryRomanization);
        EditText secondLangEntryExample = findViewById(R.id.editText_SecondLangEntryExample);

        AutoCompleteTextView entryType = findViewById(R.id.entryTypeDropdown);
        AutoCompleteTextView gender = findViewById(R.id.genderDropdown);
        AutoCompleteTextView tense = findViewById(R.id.tenseDropdown);
        AutoCompleteTextView formality = findViewById(R.id.formalityDropdown);
        EditText notes = findViewById(R.id.editText_Notes);
        CheckBox isPlural = findViewById(R.id.checkBox_IsPlural);
        CheckBox onQuickList = findViewById(R.id.checkBox_IsOnQuickList);
        CheckBox isArchived = findViewById(R.id.checkBox_IsArchived);
        TextView percentageLearned = findViewById(R.id.textView_PercentLearned);
        TextView dateLastModified = findViewById(R.id.textView_DateModified);

        // Update the selected entry with the new values
        mSelectedEntry.setCategory(categoryAutoComplete.getText().toString());
        mSelectedEntry.setFirstLanguage(firstLang.getText().toString());
        mSelectedEntry.setFirstLanguageEntry(firstLangEntry.getText().toString());
        mSelectedEntry.setFirstLanguageEntryRomanized(firstLangEntryRomanized.getText().toString());
        mSelectedEntry.setFirstLanguageExample(firstLangEntryExample.getText().toString());
        mSelectedEntry.setSecondLanguage(secondLang.getText().toString());
        mSelectedEntry.setSecondLanguageEntry(secondLangEntry.getText().toString());
        mSelectedEntry.setSecondLanguageEntryRomanized(secondLangEntryRomanized.getText().toString());
        mSelectedEntry.setSecondLanguageExample(secondLangEntryExample.getText().toString());
        mSelectedEntry.setEntryType(entryType.getText().toString());
        mSelectedEntry.setGender(gender.getText().toString());
        mSelectedEntry.setTense(tense.getText().toString());
        mSelectedEntry.setFormality(formality.getText().toString());
        mSelectedEntry.setNotes(notes.getText().toString().replace(Objects.requireNonNull(System.getProperty("line.separator")), "\\n"));
        mSelectedEntry.setIsPlural(isPlural.isChecked());
        mSelectedEntry.setOnQuickList(onQuickList.isChecked());
        mSelectedEntry.setArchived(isArchived.isChecked());
        mSelectedEntry.setPercentLearned(Integer.parseInt(percentageLearned.getText().toString()));
        mSelectedEntry.setModifiedDate(dateLastModified.getText().toString());

        // Return the updated/new selectedItem
        return mSelectedEntry;
    }

    // Event that is enabled when pressing the back button in the ActionBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        // Get updated values from the form
        mSelectedEntry = getEntryFormValues();
        Boolean dbChangesMade = false;

        // Prepare the database
        DbHelper dbHelper = new DbHelper(EntryModifierActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DbWorker dbWorker = new DbWorker(db);

        if (!isNewEntry) {
            // If the entry is not new, get the values from the form and
            // use the update function in Data Manager
            // Also saves updated favorite item to the database

            // Find out which button was pressed, Save or Delete, then do the appropriate action
            switch (view.getId()) {
                // Update
                case R.id.btn_Save:
                    try {
                        // Run the Save to DB function in the Data Manager
                        dbWorker.UpdateOrDeleteEntries(mSelectedEntry.getId(), mSelectedEntry,
                                EntryModifierActivity.this, "update");
                        adapter.notifyDataSetChanged();
                        dbResult = "Successfully Updated";
                        dbChangesMade = true;
                    } catch (Exception ex) {
                        // Display error for the user to see
                        dbResult = "Failed Update";
                        // Print Error to Console
                        System.out.println("Error updating record: " + ex.getMessage());
                    }
                    break;
                case R.id.btn_Archive:
                    // Archive is to be used like a recycling bin
                    try {
                        // Run the Save to DB function in the Data Manager
                        dbWorker.UpdateOrDeleteEntries(mSelectedEntry.getId(), mSelectedEntry,
                                EntryModifierActivity.this, "update");
                        adapter.notifyDataSetChanged();
                        dbResult = "Successfully Archived";
                        dbChangesMade = true;
                    } catch (Exception ex) {
                        // Display error for the user to see
                        dbResult = "Archive Failed";
                        // Print Error to Console
                        System.out.println("Error archiving record: " + ex.getMessage());
                    }
                    break;
                case R.id.btn_Delete:
                    // Prompt the user first before deleting to confirm that's what they want
                    try {
                        // Run the Save to DB function in the Data Manager
                        dbWorker.UpdateOrDeleteEntries(mSelectedEntry.getId(), mSelectedEntry,
                                EntryModifierActivity.this, "delete");

                        dbResult = "Successfully Deleted";
                        dbChangesMade = true;
                    } catch (Exception ex) {
                        // Display error for the user to see
                        dbResult = "Failed Deletion";
                        // Print Error to Console
                        System.out.println("Error deleting record: " + ex.getMessage());
                    }
                    break;
            }

        } else {
            // New Entry
            // Find out which button was pressed, Save or Delete, then do the appropriate action
            switch (view.getId()) {
                // Update
                case R.id.btn_Save:
                    // Verify if both fields are populated
                    if (!mSelectedEntry.getFirstLanguage().isEmpty() && !mSelectedEntry.getFirstLanguageEntry().isEmpty() &&
                            !mSelectedEntry.getSecondLanguage().isEmpty() && !mSelectedEntry.getSecondLanguageEntry().isEmpty()
                            && !mSelectedEntry.getEntryType().isEmpty()) {
                        // Both fields are populated, continue to insert entry
                        try {
                            // If the entry is new, then use the insert the entry into the database
                            dbWorker.insertTranslationModelEntry(mSelectedEntry);

                            dbResult = "Successfully Added";
                            dbChangesMade = true;
                        } catch (Exception ex) {
                            dbResult = "Failed Adding New Entry";
                            // Display Error
                            System.out.println("Failed adding new entry. Error: " + ex.getMessage());
                        }
                    } else {
                        // One or both fields are empty
                        displayToast("All fields must be populated");
                        return;
                    }
                    break;
            }
        }

        // Find out which view button was pressed, Show/Hide view then do the appropriate action
        switch (view.getId()) {
            case R.id.btn_FirstLangDisplayRomanization:
                EditText firstLangRandomize = findViewById(R.id.editText_FirstLangEntryRomanization);
                if(firstLangRandomize.getVisibility() == View.GONE)
                {
                    firstLangRandomize.setVisibility(View.VISIBLE);
                    firstLangRomanizeButton.setText(R.string.edit_hide_romanize);
                } else {
                    firstLangRandomize.setText("");
                    firstLangRandomize.setVisibility(View.GONE);
                    firstLangRomanizeButton.setText(R.string.edit_show_romanize);
                }
                break;

            case R.id.btn_SecondLangDisplayRomanization:
                EditText secondLangRandomize = findViewById(R.id.editText_SecondLangEntryRomanization);
                if(secondLangRandomize.getVisibility() == View.GONE)
                {
                    secondLangRandomize.setVisibility(View.VISIBLE);
                    secondLangRomanizeButton.setText(R.string.edit_hide_romanize);
                } else {
                    secondLangRandomize.setText("");
                    secondLangRandomize.setVisibility(View.GONE);
                    secondLangRomanizeButton.setText(R.string.edit_show_romanize);
                }
                break;

            case R.id.btn_LangDisplayExample:
                EditText firstLangExample = findViewById(R.id.editText_FirstLangEntryExample);
                EditText secondLangExample = findViewById(R.id.editText_SecondLangEntryExample);
                if(firstLangExample.getVisibility() == View.GONE || secondLangExample.getVisibility() == View.GONE)
                {
                    firstLangExample.setVisibility(View.VISIBLE);
                    secondLangExample.setVisibility(View.VISIBLE);
                    langExampleButton.setText(R.string.edit_hide_example);
                } else {
                    firstLangExample.setText("");
                    secondLangExample.setText("");
                    firstLangExample.setVisibility(View.GONE);
                    secondLangExample.setVisibility(View.GONE);
                    langExampleButton.setText(R.string.edit_show_example);
                }
                break;
        }

        if(dbChangesMade)
        {
            // Close the database
            db.close();

            // Create intent to display result
            Intent intent = new Intent();
            intent.putExtra("Result", dbResult);
            // Set the result so the the Category Activity can be updated
            setResult(CategoryActivity.RESULT_OK, intent);
            // Close the activity and go back
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void CheckDropdownSelection(TextInputLayout textInputLayout, AutoCompleteTextView autoCompleteTextView) {
        if(autoCompleteTextView.getText().toString().isEmpty() || autoCompleteTextView.getText().toString().equals(""))
        {
            textInputLayout.setBoxBackgroundColor(Color.parseColor("#FFFBE86C"));
        } else {
            textInputLayout.setBoxBackgroundColor(Color.parseColor("#B3EEEEEE"));
        }
    }
}