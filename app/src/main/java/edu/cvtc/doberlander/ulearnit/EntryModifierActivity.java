package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EntryModifierActivity extends AppCompatActivity {

    // Member level variables
    private TranslationModel mSelectedEntry;
    private String mCategory = "";
    private boolean isNewEntry = false;
    private String dbResult = "Failed";
    private int mselectedItemId = 0;

    private static final String TAG = "EntryModifierActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_modifier);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the previous activities category
        mCategory = getIntent().getExtras().getString("category");

        // Get the modification type from the Intent
        String modifyType = getIntent().getExtras().getString("modifyType");

        // Get the selected TranslationModel object from the Intent
        mSelectedEntry = getIntent().getExtras().getParcelable("SelectedItem");

        // Get the id of the selected item
        mselectedItemId = getIntent().getExtras().getInt("selectedId");

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
                mSelectedEntry.setId(mselectedItemId);
            } else {
                dbResult = "Failed: No data passed";
            }
        } else if (modifyType.equals("New")) {
            // Create new translation object
            mSelectedEntry = new TranslationModel();

            // Set the Favorites of the null SelectedEntry to 0
            mSelectedEntry.setFavorite(0);
            // Set the selected Category
            mSelectedEntry.setCategory(mCategory);
            // The rest will be filled in using the function below, when click is saved
            // That is after the form has been filled out.

            // New entry to true, so the proper db function can be ran.
            isNewEntry = true;
        }

        // Get the Save button
        Button saveButton = (Button) findViewById(R.id.btn_Save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get updated values from the form
                getEntryFormValues();

                if (!isNewEntry) {
                    // If the entry is not new, get the values from the form and
                    // use the update function in Data Manager
                    // Save the updated favorite item to the database
                    DataManager dm = new DataManager();

                    // Get the LayoutInflator from the view Context to pass to the Save to DB function
                    LayoutInflater mInflater = LayoutInflater.from(EntryModifierActivity.this);



                    // Run the Save to DB function in the Data Manager
                    // TODO: This doesn't update the database, may be because of the inflater
                    Log.d(TAG, "Test ID: " + String.valueOf(mSelectedEntry.getId()));
                    Log.d(TAG, "Test Updated FirstWord: " + String.valueOf(mSelectedEntry.getFirstLanguageWord()));
                    // ID passes correctly
                    try {
                        dm.saveEntryToDatabase(mSelectedEntry.getId(), mSelectedEntry, mInflater);

                        dbResult = "Success";
                    } catch (Exception ex) {
                        dbResult = "Failed Update";
                        // Display Error
                        Log.d(TAG, "Error: " + ex.getMessage());
                    }

                } else {
                    // New Entry
                    try {
                        // If the entry is new, then use the insert the entry into the database
                        DbHelper dh = new DbHelper(EntryModifierActivity.this);
                        SQLiteDatabase db = dh.getWritableDatabase();
                        DbWorker dw = new DbWorker(db);
                        dw.insertTranslationModelEntry(mSelectedEntry);
                        // Close the database
                        db.close();
                        dbResult = "Success";
                    } catch (Exception ex) {
                        dbResult = "Failed Adding New Entry";
                        // Display Error
                        Log.d(TAG, "Error: " + ex.getMessage());
                    }
                }

                // Create intent to display result
                Intent intent = new Intent();
                intent.putExtra("Result", dbResult);
                // Set the result so the the Category Activity can be updated
                setResult(CategoryActivity.RESULT_OK, intent);
                // Close the activity and go back
                //finish();
            }
        });
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
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}