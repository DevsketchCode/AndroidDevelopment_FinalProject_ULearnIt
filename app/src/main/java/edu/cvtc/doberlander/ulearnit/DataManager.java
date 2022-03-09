package edu.cvtc.doberlander.ulearnit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

public class DataManager {

    private static DataManager ourInstance = null;
    private List<TranslationModel> mTranslations = new ArrayList<>();

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
        int listFirstLangPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE);
        int listFirstLangWordPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD);
        int listSecondLangPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE);
        int listSecondLangWordPosition = cursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_WORD);
        int listFavoritePosition = cursor.getColumnIndex(TranslationEntry.COLUMN_FAVORITE);
        int idPosition = cursor.getColumnIndex(TranslationEntry._ID);

        // Create an instance of the DataManager and use it to clear any information from the array.
        DataManager dm = getInstance();
        dm.mTranslations.clear();

        // Loop through the cursor rows and add new course objects to your array list.
        while(cursor.moveToNext()) {
            int id = cursor.getInt(idPosition);
            String listCategory = cursor.getString(listCategoryPosition);
            String listFirstLang = cursor.getString(listFirstLangPosition);
            String listFirstLangWord = cursor.getString(listFirstLangWordPosition);
            String listSecondLang = cursor.getString(listSecondLangPosition);
            String listSecondLangWord = cursor.getString(listSecondLangWordPosition);
            int listFavorite = cursor.getInt(listFavoritePosition);

            TranslationModel list = new TranslationModel(id, listCategory, listFirstLang,
                    listFirstLangWord, listSecondLang, listSecondLangWord, listFavorite);

            dm.mTranslations.add(list);
        }

        // Close the cursor to prevent memory leaks
        cursor.close();
    }

    public static void loadFromDatabase(DbHelper dbHelper) {
        // Open your database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a list of columns that you want to return.
        String[] translationEntryColumns = {
          TranslationEntry._ID,
          TranslationEntry.COLUMN_CATEGORY,
          TranslationEntry.COLUMN_FIRST_LANGUAGE,
          TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD,
          TranslationEntry.COLUMN_SECOND_LANGUAGE,
          TranslationEntry.COLUMN_SECOND_LANGUAGE_WORD,
          TranslationEntry.COLUMN_FAVORITE
        };

        // Create an order by field for sorting
        String entryOrderBy = TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD;

        // Populate the cursor with results from the query
        final Cursor entryCursor = db.query(TranslationEntry.TABLE_NAME, translationEntryColumns,
                null, null, null, null, entryOrderBy);

        // Call the method to load the array list
        loadTranslationsFromDatabase(entryCursor);
    }


}
