package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EntryModifierActivity extends AppCompatActivity implements View.OnClickListener {

    // Member level variables
    private TranslationModel mSelectedEntry;
    private boolean isNewEntry = false;
    private String dbResult = "Failed";

    private static final String TAG = "EntryModifierActivity";

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
            }
        } catch (NullPointerException ex) {
            System.out.println("Error showing back button on Action Bar. Error: " + ex.getMessage());
        }

        // Set onClickListeners to the buttons
        Button saveButton = findViewById(R.id.btn_Save);
        saveButton.setOnClickListener(this);
        Button deleteButton = findViewById(R.id.btn_Delete);
        deleteButton.setOnClickListener(this);


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
                EditText firstLangWord = findViewById(R.id.editText_FirstLangWord);
                EditText secondLangWord = findViewById(R.id.editText_SecondLangWord);
                firstLangWord.setText(mSelectedEntry.getFirstLanguageWord());
                secondLangWord.setText(mSelectedEntry.getSecondLanguageWord());

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

            // New entry to true, so the proper db function can be ran.
            isNewEntry = true;

            // Hide delete button, as it's not needed for new records
            deleteButton.setVisibility(View.GONE);

        }
    }

    private TranslationModel getEntryFormValues() {
        // Get the fields
        EditText firstLang = findViewById(R.id.editText_FirstLang);
        EditText firstLangWord = findViewById(R.id.editText_FirstLangWord);
        EditText secondLang = findViewById(R.id.editText_SecondLang);
        EditText secondLangWord = findViewById(R.id.editText_SecondLangWord);

        // Update the selected entry with the new values
        mSelectedEntry.setFirstLanguage(firstLang.getText().toString());
        mSelectedEntry.setFirstLanguageWord(firstLangWord.getText().toString());
        mSelectedEntry.setSecondLanguage(secondLang.getText().toString());
        mSelectedEntry.setSecondLanguageWord(secondLangWord.getText().toString());

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
                        dbResult = "Successfully Updated";
                    } catch (Exception ex) {
                        // Display error for the user to see
                        dbResult = "Failed Update";
                        // Print Error to Console
                        System.out.println("Error updating record: " + ex.getMessage());
                    }
                    break;
                case R.id.btn_Delete:
                    // Prompt the user first before deleting to confirm that's what they want
                    try {
                        // Run the Save to DB function in the Data Manager
                        dbWorker.UpdateOrDeleteEntries(mSelectedEntry.getId(), mSelectedEntry,
                                EntryModifierActivity.this, "delete");

                        dbResult = "Successfully Deleted";
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
            // Verify if both fields are populated
            if(!mSelectedEntry.getFirstLanguageWord().isEmpty() && !mSelectedEntry.getSecondLanguageWord().isEmpty()) {
                // Both fields are populated, continue to insert entry
                try {
                    // If the entry is new, then use the insert the entry into the database
                    dbWorker.insertTranslationModelEntry(mSelectedEntry);

                    dbResult = "Successfully Added";
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
        }

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

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}