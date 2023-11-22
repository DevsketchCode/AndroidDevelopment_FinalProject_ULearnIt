package com.devsketch.ulearnit;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import com.devsketch.ulearnit.DbContract.TranslationEntry;

public class DataManager {

    private static DataManager ourInstance = null;
    private final List<TranslationModel> mTranslations = new ArrayList<>();

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    // Return a list of translations
    public List<TranslationModel> getTranslations() {
        return mTranslations;
    }

    private static void loadTranslationsFromDatabase(Cursor cursor) {
        // Retrieve the field position from the database.
        int listCategoryPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_CATEGORY);
        int listSubCategoryPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SUBCATEGORY);
        int listFirstLangPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE);
        int listFirstLangEntryPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY);
        int listFirstLangEntryRomanizedPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY_ROMANIZED);
        int listFirstLangExamplePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE);
        int listSecondLangPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE);
        int listSecondLangEntryPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY);
        int listSecondLangEntryRomanizedPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY_ROMANIZED);
        int listSecondLangExamplePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE);
        int listEntryTypePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_ENTRY_TYPE);
        int listTensePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_TENSE);
        int listLinkEntryIdTenseBasePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_BASE);
        int listLinkEntryIdTensePastPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_PAST);
        int listLinkEntryIdTensePresentPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_PRESENT);
        int listLinkEntryIdTenseFuturePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_FUTURE);
        int listIsPluralPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_IS_PLURAL);
        int listGenderPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_GENDER);
        int listFormalityPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FORMALITY);
        int listPercentLearnedPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_PERCENT_LEARNED);
        int listPercentLearnedModifiedDatePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_PERCENT_LEARNED_MODIFIED_DATE);
        int listFailedAttemptsPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FAILED_ATTEMPTS);
        int listMemorizedPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_MEMORIZED);
        int listNotesPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_NOTES);
        int listSummaryNotesPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SUMMARY_NOTES);
        int listImagePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_IMAGE);
        int listAudioPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_AUDIO);
        int listUserAudioPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_USER_AUDIO);
        int listUserAudioModifiedDatePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_USER_AUDIO_MODIFIED_DATE);
        int listFavoritePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FAVORITE);
        int listTagsPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_TAGS);
        int listModifiedDatePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_MODIFIED_DATE);
        int listOnQuickListPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_ON_QUICKLIST);
        int listArchivedPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_ARCHIVED);
        int idPosition = cursor.getColumnIndex(TranslationEntry._ID);

        // Create an instance of the DataManager and use it to clear any information from the array.
        DataManager dm = getInstance();
        dm.mTranslations.clear();

        // Loop through the cursor rows and add new course objects to your array list.
        while(cursor.moveToNext()) {
            int id = cursor.getInt(idPosition);
            String listCategory = cursor.getString(listCategoryPosition);
            String listSubCategory = cursor.getString(listSubCategoryPosition);
            String listFirstLang = cursor.getString(listFirstLangPosition);
            String listFirstLangEntry = cursor.getString(listFirstLangEntryPosition);
            String listFirstLangEntryRomanized = cursor.getString(listFirstLangEntryRomanizedPosition);
            String listFirstLangExample = cursor.getString(listFirstLangExamplePosition);
            String listSecondLang = cursor.getString(listSecondLangPosition);
            String listSecondLangEntry = cursor.getString(listSecondLangEntryPosition);
            String listSecondLangEntryRomanized = cursor.getString(listSecondLangEntryRomanizedPosition);
            String listSecondLangExample = cursor.getString(listSecondLangExamplePosition);
            String listEntryType = cursor.getString(listEntryTypePosition);
            String listTense = cursor.getString(listTensePosition);
            int listLinkEntryIdTenseBase = cursor.getInt(listLinkEntryIdTenseBasePosition);
            int listLinkEntryIdTensePast = cursor.getInt(listLinkEntryIdTensePastPosition);
            int listLinkEntryIdTensePresent = cursor.getInt(listLinkEntryIdTensePresentPosition);
            int listLinkEntryIdTenseFuture = cursor.getInt(listLinkEntryIdTenseFuturePosition);
            Boolean listIsPlural = cursor.getInt(listIsPluralPosition) != 0;
            String listGender = cursor.getString(listGenderPosition);
            String listFormality = cursor.getString(listFormalityPosition);
            int listPercentLearned = cursor.getInt(listPercentLearnedPosition);
            String listPercentLearnedModifiedDate = cursor.getString(listPercentLearnedModifiedDatePosition);
            int listFailedAttempts = cursor.getInt(listFailedAttemptsPosition);
            Boolean listMemorized = cursor.getInt(listMemorizedPosition) != 0;
            String listNotes = cursor.getString(listNotesPosition);
            String listSummaryNotes = cursor.getString(listSummaryNotesPosition);
            String listImage = cursor.getString(listImagePosition);
            String listAudio = cursor.getString(listAudioPosition);
            String listUserAudio = cursor.getString(listUserAudioPosition);
            String listUserAudioModifiedDate = cursor.getString(listUserAudioModifiedDatePosition);
            int listFavorite = cursor.getInt(listFavoritePosition);
            String listTags = cursor.getString(listTagsPosition);
            String listModifiedDate = cursor.getString(listModifiedDatePosition);
            Boolean listOnQuickList = cursor.getInt(listOnQuickListPosition) != 0;
            Boolean listArchived = cursor.getInt(listArchivedPosition) != 0;

            TranslationModel list = new TranslationModel(id, listCategory, listSubCategory, listFirstLang,
                    listFirstLangEntry, listFirstLangEntryRomanized, listFirstLangExample, listSecondLang, listSecondLangEntry, listSecondLangEntryRomanized, listSecondLangExample, listEntryType, listTense, listLinkEntryIdTenseBase, listLinkEntryIdTensePast, listLinkEntryIdTensePresent, listLinkEntryIdTenseFuture, listIsPlural, listGender, listFormality, listPercentLearned, listPercentLearnedModifiedDate, listFailedAttempts, listMemorized, listNotes, listSummaryNotes, listImage, listAudio, listUserAudio, listUserAudioModifiedDate, listFavorite, listTags, listModifiedDate, listOnQuickList, listArchived);

            dm.mTranslations.add(list);
        }

        // Close the cursor to prevent memory leaks
        cursor.close();
    }

    public static void loadFromDatabase(DbHelper dbHelper, Preferences preferences) {
        // Declare SQL Statement variables
        String entryWhere;
        String[] entryWhereArgs;

        // Open your database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a list of columns that you want to return.
        String[] translationEntryColumns = {
          TranslationEntry._ID,
          TranslationEntry.COLUMN_CATEGORY,
          TranslationEntry.COLUMN_SUBCATEGORY,
          TranslationEntry.COLUMN_FIRST_LANGUAGE,
          TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY,
          TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY_ROMANIZED,
          TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE,
          TranslationEntry.COLUMN_SECOND_LANGUAGE,
          TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY,
          TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY_ROMANIZED,
          TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE,
          TranslationEntry.COLUMN_ENTRY_TYPE,
          TranslationEntry.COLUMN_TENSE,
          TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_BASE,
          TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_PAST,
          TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_PRESENT,
          TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_FUTURE,
          TranslationEntry.COLUMN_IS_PLURAL,
          TranslationEntry.COLUMN_GENDER,
          TranslationEntry.COLUMN_FORMALITY,
          TranslationEntry.COLUMN_PERCENT_LEARNED,
          TranslationEntry.COLUMN_PERCENT_LEARNED_MODIFIED_DATE,
          TranslationEntry.COLUMN_FAILED_ATTEMPTS,
          TranslationEntry.COLUMN_MEMORIZED,
          TranslationEntry.COLUMN_NOTES,
          TranslationEntry.COLUMN_SUMMARY_NOTES,
          TranslationEntry.COLUMN_IMAGE,
          TranslationEntry.COLUMN_AUDIO,
          TranslationEntry.COLUMN_USER_AUDIO,
          TranslationEntry.COLUMN_USER_AUDIO_MODIFIED_DATE,
          TranslationEntry.COLUMN_FAVORITE,
          TranslationEntry.COLUMN_TAGS,
          TranslationEntry.COLUMN_MODIFIED_DATE,
          TranslationEntry.COLUMN_ON_QUICKLIST,
          TranslationEntry.COLUMN_ARCHIVED
        };

        // Create an order by field for sorting
        String entryOrderBy = TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY;
        // Where statement
        //if(preferences.getCategory().equals("Favorites")) {
        //    entryWhere = "favorite = ?";
        //    entryWhereArgs = new String[]{String.valueOf(1)};
        //} else {
            // Check Saved Preferences
            String prefWhereColumns = "";

            if (!preferences.getFirstLanguage().equals("")) {
                prefWhereColumns = "first_language = ? AND ";
            } else {
                // Set Default Native Language
                preferences.setFirstLanguage("English");
                prefWhereColumns = "first_language = ? AND ";
            }
            if (!preferences.getSecondLanguage().equals("")) {
                prefWhereColumns = prefWhereColumns + "second_language = ? AND ";
            } else {
                // Show all, there should always be a second language
                prefWhereColumns = prefWhereColumns + "second_language != ? AND ";
            }

            if(preferences.getCategory().equals("Favorites")) {
                prefWhereColumns = prefWhereColumns + "favorite = ?";
            } else {
                //TODO: This is where you could also get a SharedPref to show/hide Deleted or Archived Rows
                prefWhereColumns = prefWhereColumns +  "category = ?";
            }

            entryWhere = prefWhereColumns;
            if(preferences.getCategory().equals("Favorites")) {
            entryWhereArgs = new String[]{preferences.getFirstLanguage(), preferences.getSecondLanguage(), "1"};
            } else {
                entryWhereArgs = new String[]{preferences.getFirstLanguage(), preferences.getSecondLanguage(), preferences.getCategory()};
            }
        //}

        // Populate the cursor with results from the query
        final Cursor entryCursor = db.query(TranslationEntry.TABLE_NAME, translationEntryColumns,
                entryWhere, entryWhereArgs, null, null, entryOrderBy);




        // Call the method to load the array list
        loadTranslationsFromDatabase(entryCursor);
    }

    public static ContentValues populateValues(TranslationModel tEntry) {
        // Use a ContentValues object to put the information into
        ContentValues values = new ContentValues();

        // fill all the values from the Translation Model Object
        // This will update both the favorites as well as if the translation was edited
        values.put(TranslationEntry.COLUMN_CATEGORY, tEntry.getCategory());
        values.put(TranslationEntry.COLUMN_SUBCATEGORY, tEntry.getSubCategory());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE, tEntry.getFirstLanguage());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY, tEntry.getFirstLanguageEntry());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY_ROMANIZED, tEntry.getFirstLanguageEntryRomanized());
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE, tEntry.getFirstLanguageExample());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE, tEntry.getSecondLanguage());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY, tEntry.getSecondLanguageEntry());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY_ROMANIZED, tEntry.getSecondLanguageEntryRomanized());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE, tEntry.getSecondLanguageExample());
        values.put(TranslationEntry.COLUMN_ENTRY_TYPE, tEntry.getEntryType());
        values.put(TranslationEntry.COLUMN_TENSE, tEntry.getTense());
        values.put(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_BASE, tEntry.getLinkEntryIdTenseBase());
        values.put(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_PAST, tEntry.getLinkEntryIdTensePast());
        values.put(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_PRESENT, tEntry.getLinkEntryIdTensePresent());
        values.put(TranslationEntry.COLUMN_LINK_ENTRY_ID_TENSE_FUTURE, tEntry.getLinkEntryIdTenseFuture());
        values.put(TranslationEntry.COLUMN_IS_PLURAL, tEntry.getIsPlural());
        values.put(TranslationEntry.COLUMN_GENDER, tEntry.getGender());
        values.put(TranslationEntry.COLUMN_FORMALITY, tEntry.getFormality());
        values.put(TranslationEntry.COLUMN_PERCENT_LEARNED, tEntry.getPercentLearned());
        values.put(TranslationEntry.COLUMN_PERCENT_LEARNED_MODIFIED_DATE, tEntry.getPercentLearnedModifiedDate());
        values.put(TranslationEntry.COLUMN_FAILED_ATTEMPTS, tEntry.getFailedAttempts());
        values.put(TranslationEntry.COLUMN_MEMORIZED, tEntry.getMemorized());
        values.put(TranslationEntry.COLUMN_NOTES, tEntry.getNotes());
        values.put(TranslationEntry.COLUMN_SUMMARY_NOTES, tEntry.getSummaryNotes());
        values.put(TranslationEntry.COLUMN_IMAGE, tEntry.getImage());
        values.put(TranslationEntry.COLUMN_AUDIO, tEntry.getAudio());
        values.put(TranslationEntry.COLUMN_USER_AUDIO, tEntry.getUserAudio());
        values.put(TranslationEntry.COLUMN_USER_AUDIO_MODIFIED_DATE, tEntry.getUserAudioModifiedDate());
        values.put(TranslationEntry.COLUMN_FAVORITE, tEntry.getFavorite());
        values.put(TranslationEntry.COLUMN_TAGS, tEntry.getTags());
        values.put(TranslationEntry.COLUMN_MODIFIED_DATE, tEntry.getModifiedDate());
        values.put(TranslationEntry.COLUMN_ON_QUICKLIST, tEntry.getOnQuickList());
        values.put(TranslationEntry.COLUMN_ARCHIVED, tEntry.getArchived());

        return values;
    }

    public static int getEntryCountFromDatabase(DbHelper dbHelper, SharedPreferences sharedPrefs,
                                                String filterColumnName1, String filterColumnValue1,
                                                String filterColumnName2, String filterColumnValue2,
                                                String filterColumnName3, String filterColumnValue3) {
        // Declare SQL Statement variables
        String entryWhere;
        String[] entryWhereArgs;
        int returnValue = 0;
        Boolean filterColumnsAdded = false;

        // Open your database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Where statement
        // Check Saved Preferences
        String prefWhereColumns = "";
        String firstLanguage = "";
        String secondLanguage = "";
        if (!sharedPrefs.getString("FirstLanguage", "").equals("")) {
            firstLanguage = sharedPrefs.getString("FirstLanguage", "");
            prefWhereColumns = "first_language = ? AND ";
        } else {
            // Set Default Native Language
            SharedPreferences.Editor prefs_editor = sharedPrefs.edit();
            prefs_editor.putString("FirstLanguage", "English");
            prefs_editor.apply();
            firstLanguage = sharedPrefs.getString("FirstLanguage", "");
            prefWhereColumns = "first_language = ? AND ";
        }
        if (!sharedPrefs.getString("SecondLanguage", "").equals("")) {
            secondLanguage = sharedPrefs.getString("SecondLanguage", "");
            prefWhereColumns = prefWhereColumns + "second_language = ? AND ";
        } else {
            // Show all, there should always be a second language
            secondLanguage = sharedPrefs.getString("SecondLanguage", "");
            prefWhereColumns = prefWhereColumns + "second_language != ? AND ";
        }

        entryWhere = prefWhereColumns;

        if (!filterColumnName1.equals("") && filterColumnName2.equals("") && filterColumnName3.equals("")) {
            entryWhere = prefWhereColumns + filterColumnName1 + " = ?";
            entryWhereArgs = new String[]{firstLanguage, secondLanguage, filterColumnValue1};
            filterColumnsAdded = true;
        } else if (!filterColumnName1.equals("") && !filterColumnName2.equals("") && filterColumnName3.equals("")) {
            entryWhere = prefWhereColumns + filterColumnName1 + " = ? AND " + filterColumnName2 + " = ?";
            entryWhereArgs = new String[]{firstLanguage, secondLanguage, filterColumnValue1, filterColumnValue2};
            filterColumnsAdded = true;
        } else if (!filterColumnName1.equals("") && !filterColumnName2.equals("") && !filterColumnName3.equals("")) {
            entryWhere = prefWhereColumns + filterColumnName1 + " = ? AND " + filterColumnName2 + " = ? AND " + filterColumnName2 + " = ?";
            entryWhereArgs = new String[]{firstLanguage, secondLanguage, filterColumnValue1, filterColumnValue2, filterColumnValue3};
            filterColumnsAdded = true;
        } else {
            entryWhereArgs = new String[]{firstLanguage, secondLanguage};
        }
        // Log.d(TAG, "DataManager - Second Language: " + secondLanguage);
        // Log.d(TAG, "DataManager - Where: " + Arrays.toString(entryWhereArgs));

        if (filterColumnsAdded) {
            // Populate the cursor with results from the query
            final Cursor entryCursor = db.rawQuery("SELECT COUNT(*) FROM " + TranslationEntry.TABLE_NAME + " WHERE " + entryWhere, entryWhereArgs);

            // Get the count from the cursor
            if (entryCursor.moveToFirst()) {
                returnValue = entryCursor.getInt(0);
            }

            // Close the cursor to avoid memory leak
            entryCursor.close();
        }

        return returnValue;
    }

    // Save the item to favorites from the Translation Adapter that passes a LayoutInflater
    public void saveEntryFromAdapterToDatabase(int entryId, TranslationModel tEntry, LayoutInflater inflater) {
        // Create selection criteria as constants
        final String selection = DbContract.TranslationEntry._ID + " = ?";
        final String[] selectionArgs = {Integer.toString(entryId)};

        // Use a ContentValues object to put the information into
        final ContentValues values = populateValues(tEntry);

        // Use AsyncTaskLoader to run the update process on a separate thread from main
        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(inflater.getContext()) {
            @Nullable
            @Override
            public String loadInBackground() {

                // Get connection to the database. Use the writable method since we are changing the data.
                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Call the update method
                db.update(TranslationEntry.TABLE_NAME, values, selection, selectionArgs);
                // Close the database
                db.close();
                return null;
            }
        };

        // Update the db from the Adapter on a separate thread from main
        task.loadInBackground();
    }
}
