package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

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
        int listFirstLangExamplePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE);
        int listSecondLangPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE);
        int listSecondLangEntryPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY);
        int listSecondLangExamplePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE);
        int listEntryTypePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_ENTRY_TYPE);
        int listTensePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_TENSE);
        int listSingularOrPluralPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SINGULAR_OR_PLURAL);
        int listMasculineOrFemininePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_MASCULINE_OR_FEMININE);
        int listPercentLearnedPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_PERCENT_LEARNED);
        int listNotesPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_NOTES);
        int listFavoritePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FAVORITE);
        int listTagsPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_TAGS);
        int listModifiedDatePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_MODIFIED_DATE);
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
            String listFirstLangExample = cursor.getString(listFirstLangExamplePosition);
            String listSecondLang = cursor.getString(listSecondLangPosition);
            String listSecondLangEntry = cursor.getString(listSecondLangEntryPosition);
            String listSecondLangExample = cursor.getString(listSecondLangExamplePosition);
            String listEntryType = cursor.getString(listEntryTypePosition);
            String listTense = cursor.getString(listTensePosition);
            String listSingularOrPlural = cursor.getString(listSingularOrPluralPosition);
            String listMasculineOrFeminine = cursor.getString(listMasculineOrFemininePosition);
            int listPercentLearned = cursor.getInt(listPercentLearnedPosition);
            String listNotes = cursor.getString(listNotesPosition);
            int listFavorite = cursor.getInt(listFavoritePosition);
            String listTags = cursor.getString(listTagsPosition);
            String listModifiedDate = cursor.getString(listModifiedDatePosition);

            TranslationModel list = new TranslationModel(id, listCategory, listSubCategory, listFirstLang,
                    listFirstLangEntry, listFirstLangExample, listSecondLang, listSecondLangEntry, listSecondLangExample,
                    listEntryType, listTense, listSingularOrPlural, listMasculineOrFeminine, listPercentLearned, listNotes, listFavorite, listTags, listModifiedDate);

            dm.mTranslations.add(list);
        }

        // Close the cursor to prevent memory leaks
        cursor.close();
    }

    public static void loadFromDatabase(DbHelper dbHelper, String category) {
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
          TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE,
          TranslationEntry.COLUMN_SECOND_LANGUAGE,
          TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY,
          TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE,
          TranslationEntry.COLUMN_ENTRY_TYPE,
          TranslationEntry.COLUMN_TENSE,
          TranslationEntry.COLUMN_SINGULAR_OR_PLURAL,
          TranslationEntry.COLUMN_MASCULINE_OR_FEMININE,
          TranslationEntry.COLUMN_PERCENT_LEARNED,
          TranslationEntry.COLUMN_NOTES,
          TranslationEntry.COLUMN_FAVORITE,
          TranslationEntry.COLUMN_TAGS,
          TranslationEntry.COLUMN_MODIFIED_DATE
        };

        // Create an order by field for sorting
        String entryOrderBy = TranslationEntry.COLUMN_FIRST_LANGUAGE_ENTRY;
        // Where statement
        if(category.equals("Favorites")) {
            entryWhere = "favorite = ?";
            entryWhereArgs = new String[]{String.valueOf(1)};
        } else {
            entryWhere = "category = ?";
            entryWhereArgs = new String[]{category};
        }

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
        values.put(TranslationEntry.COLUMN_FIRST_LANGUAGE_EXAMPLE, tEntry.getFirstLanguageExample());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE, tEntry.getSecondLanguage());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_ENTRY, tEntry.getSecondLanguageEntry());
        values.put(TranslationEntry.COLUMN_SECOND_LANGUAGE_EXAMPLE, tEntry.getSecondLanguageExample());
        values.put(TranslationEntry.COLUMN_ENTRY_TYPE, tEntry.getEntryType());
        values.put(TranslationEntry.COLUMN_TENSE, tEntry.getTense());
        values.put(TranslationEntry.COLUMN_SINGULAR_OR_PLURAL, tEntry.getSingularOrPlural());
        values.put(TranslationEntry.COLUMN_MASCULINE_OR_FEMININE, tEntry.getMasculineOrFeminine());
        values.put(TranslationEntry.COLUMN_PERCENT_LEARNED, tEntry.getPercentLearned());
        values.put(TranslationEntry.COLUMN_NOTES, tEntry.getNotes());
        values.put(TranslationEntry.COLUMN_FAVORITE, tEntry.getFavorite());
        values.put(TranslationEntry.COLUMN_TAGS, tEntry.getTags());
        values.put(TranslationEntry.COLUMN_MODIFIED_DATE, tEntry.getModifiedDate());

        return values;
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
