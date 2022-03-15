package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

public class DbWorker {

    private static final String TAG = "EntryModifierActivity";
    private final SQLiteDatabase mDb;

    // Initialize the database attribute
    public DbWorker(SQLiteDatabase db) { mDb = db; }

    // Function to insertEntry from a list
    private void insertEntry(String category, String firstLang, String firstLangWord, String secondLang, String secondLangWord, int favorite) {
        ContentValues values = new ContentValues();
        values.put(DbContract.TranslationEntry.COLUMN_CATEGORY, category);
        values.put(DbContract.TranslationEntry.COLUMN_FIRST_LANGUAGE, firstLang);
        values.put(DbContract.TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD, firstLangWord);
        values.put(DbContract.TranslationEntry.COLUMN_SECOND_LANGUAGE, secondLang);
        values.put(DbContract.TranslationEntry.COLUMN_SECOND_LANGUAGE_WORD, secondLangWord);
        values.put(DbContract.TranslationEntry.COLUMN_FAVORITE, favorite);

        long newRowId = mDb.insert(TranslationEntry.TABLE_NAME, null, values);
    }

    // Function to
    public void insertTranslationModelEntry(TranslationModel tm) {

        ContentValues values = new ContentValues();
        values.put(DbContract.TranslationEntry.COLUMN_CATEGORY, tm.getCategory());
        values.put(DbContract.TranslationEntry.COLUMN_FIRST_LANGUAGE, tm.getFirstLanguage());
        values.put(DbContract.TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD, tm.getFirstLanguageWord());
        values.put(DbContract.TranslationEntry.COLUMN_SECOND_LANGUAGE, tm.getSecondLanguage());
        values.put(DbContract.TranslationEntry.COLUMN_SECOND_LANGUAGE_WORD, tm.getSecondLanguageWord());
        values.put(DbContract.TranslationEntry.COLUMN_FAVORITE, tm.getFavorite());

        // Insert the translation into the database - return a row id if wanting it later
        long newRowId = mDb.insert(TranslationEntry.TABLE_NAME, null, values);

        // Close the database
        mDb.close();
    }

    // Get all of the entries from the predefined lists (Greetings and Numbers)
    private LinkedList<TranslationModel> GetPredefinedTranslationList() {
        // Create the LinkedList variable
        LinkedList<TranslationModel> tm;
        // Create the translation list object
        TranslationList list = new TranslationList();
        // Get the greetings and add it to the list
        list.GetTranslations("Greetings");
        // Get the numbers and add it to the list, then attach tot the LinkedList
        tm = list.GetTranslations("Numbers");
        // Return the LinkedList
        return tm;
    }

    // Populate the database with Initial Data
    public void insertEntries() {
        // Get the Translation List and attach to a LinkedList
        LinkedList<TranslationModel> tm = GetPredefinedTranslationList();
        // Loop through the items in the list and insert them in the database when called.
        for (TranslationModel m : tm) {
            insertEntry(m.getCategory(),
                    m.getFirstLanguage(),
                    m.getFirstLanguageWord(),
                    m.getSecondLanguage(),
                    m.getSecondLanguageWord(),
                    m.getFavorite());
        }
    }

    public void UpdateOrDeleteEntries(int entryId, TranslationModel tEntry, Context context, String action) {
        // Create selection criteria as constants
        final String selection = DbContract.TranslationEntry._ID + " = ?";
        final String[] selectionArgs = {Integer.toString(entryId)};


        // Use a ContentValues object to put the information into
        final ContentValues values = DataManager.populateValues(tEntry);

        // Run update on a new thread, code method was found from
        // https://developer.android.com/guide/components/processes-and-threads
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Get connection to the database. Use the writable method since we are changing the data.
                // Get the activity context (example: CategoryActivity.this) and create the DbHelper with it
                DbHelper dbHelper = new DbHelper(context);
                // Assign the writable database to apply updates
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // Run the potentially time consuming task
                if(action.equalsIgnoreCase("update")) {
                    // Update the selected entry
                    db.update(TranslationEntry.TABLE_NAME, values, selection, selectionArgs);
                } else if (action.equalsIgnoreCase("delete")) {
                    // Delete the selected entry
                    db.delete(TranslationEntry.TABLE_NAME,selection,selectionArgs);
                }
                // Close the database
                db.close();
            }
        // Start the thread
        }).start();
    }
}
