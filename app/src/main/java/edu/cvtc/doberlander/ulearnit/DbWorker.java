package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

public class DbWorker {

    private final SQLiteDatabase mDb;

    // Initialize the database attribute
    public DbWorker(SQLiteDatabase db) { mDb = db; }

    // Function to insertEntry from a list
    private void insertEntry(String category, String subCategory, String firstLang, String firstLangEntry, String firstLangExample, String secondLang, String secondLangEntry, String secondLangExample, String entryType, String tense, String singularOrPlural, String masculineOrFeminine, int percentLearned, String notes, int favorite, String tags, String modifiedDate) {
        ContentValues values = new ContentValues();
        values.put(TranslationEntry.COLUMN_CATEGORY, category);
        values.put(TranslationEntry.COLUMN_SUBCATEGORY, subCategory);
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE, firstLang);
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY, firstLangEntry);
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE, firstLangExample);
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE, secondLang);
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY, secondLangEntry);
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE, secondLangExample);
        values.put(TranslationEntry.COLUMN_ENTRY_TYPE, entryType);
        values.put(TranslationEntry.COLUMN_TENSE, tense);
        values.put(TranslationEntry.COLUMN_SINGULAR_OR_PLURAL, singularOrPlural);
        values.put(TranslationEntry.COLUMN_MASCULINE_OR_FEMININE, masculineOrFeminine);
        values.put(TranslationEntry.COLUMN_PERCENT_LEARNED, percentLearned);
        values.put(TranslationEntry.COLUMN_NOTES, notes);
        values.put(TranslationEntry.COLUMN_FAVORITE, favorite);
        values.put(TranslationEntry.COLUMN_TAGS, tags);
        values.put(TranslationEntry.COLUMN_MODIFIED_DATE, modifiedDate);

        // Insert the translation into the database
        mDb.insert(TranslationEntry.TABLE_NAME, null, values);
    }

    // Function to insert a new translation into the database via an object
    public void insertTranslationModelEntry(TranslationModel tm) {

        ContentValues values = new ContentValues();
        values.put(TranslationEntry.COLUMN_CATEGORY, tm.getCategory());
        values.put(TranslationEntry.COLUMN_SUBCATEGORY, tm.getSubCategory());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE, tm.getFirstLanguage());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY, tm.getFirstLanguageEntry());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE, tm.getFirstLanguageExample());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE, tm.getSecondLanguage());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY, tm.getSecondLanguageEntry());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE, tm.getSecondLanguageExample());
        values.put(TranslationEntry.COLUMN_ENTRY_TYPE, tm.getEntryType());
        values.put(TranslationEntry.COLUMN_TENSE, tm.getTense());
        values.put(TranslationEntry.COLUMN_SINGULAR_OR_PLURAL, tm.getSingularOrPlural());
        values.put(TranslationEntry.COLUMN_MASCULINE_OR_FEMININE, tm.getMasculineOrFeminine());
        values.put(TranslationEntry.COLUMN_PERCENT_LEARNED, tm.getPercentLearned());
        values.put(TranslationEntry.COLUMN_NOTES, tm.getNotes());
        values.put(TranslationEntry.COLUMN_FAVORITE, tm.getFavorite());
        values.put(TranslationEntry.COLUMN_TAGS, tm.getTags());
        values.put(TranslationEntry.COLUMN_MODIFIED_DATE, tm.getModifiedDate());

        // Insert the translation into the database
        mDb.insert(TranslationEntry.TABLE_NAME, null, values);

        // Close the database
        mDb.close();
    }

    // Get all of the entries from the predefined lists (Greetings and Numbers)
    private LinkedList<TranslationModel> GetPredefinedTranslationList() {
        // Create the LinkedList variable
        // Create the translation list object
        TranslationList list = new TranslationList();

        // Get the greetings and add it to the list
        list.GetTranslations("Tagalog", "Greetings");
        // Get the numbers and add it to the list, then attach tot the LinkedList
        list.GetTranslations("Tagalog", "Numbers");
        // Get the food list and add it to the list
        list.GetTranslations("Tagalog", "Food");
        // Get the people list and add it to the list
        list.GetTranslations("Tagalog", "People");
        // Get the people list and add it to the list
        list.GetTranslations("Tagalog", "Relationship");
        // Get the people list and add it to the list
        list.GetTranslations("Tagalog", "General");
        // Get the people list and add it to the list
        list.GetTranslations("Tagalog", "Theocratic");
        // Get the korean greetings list and add it to the list
        list.GetTranslations("Korean", "Greetings");
        // Get the korean numbers list and add it to the list
        list.GetTranslations("Korean", "Numbers");
        // Get the korean food list and add it to the list
        list.GetTranslations("Korean", "Food");
        // Get the korean People list and add it to the list
        list.GetTranslations("Korean", "People");
        // Get the korean Relationship list and add it to the list
        list.GetTranslations("Korean", "Relationship");
        // Get the korean General list and add it to the list
        list.GetTranslations("Korean", "General");
        // Get the korean Theocratic list and add them all to the linked list
        // Return the LinkedList
        return new LinkedList<>(list.GetTranslations("Korean", "Theocratic"));
    }

    // Populate the database with Initial Data
    public void insertEntries() {
        // Get the Translation List and attach to a LinkedList
        LinkedList<TranslationModel> tm = GetPredefinedTranslationList();
        // Loop through the items in the list and insert them in the database when called.
        for (TranslationModel m : tm) {
            insertEntry(m.getCategory(),
                    m.getSubCategory(),
                    m.getFirstLanguage(),
                    m.getFirstLanguageEntry(),
                    m.getFirstLanguageExample(),
                    m.getSecondLanguage(),
                    m.getSecondLanguageEntry(),
                    m.getSecondLanguageExample(),
                    m.getEntryType(),
                    m.getTense(),
                    m.getSingularOrPlural(),
                    m.getMasculineOrFeminine(),
                    m.getPercentLearned(),
                    m.getNotes(),
                    m.getFavorite(),
                    m.getTags(),
                    m.getModifiedDate());
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
