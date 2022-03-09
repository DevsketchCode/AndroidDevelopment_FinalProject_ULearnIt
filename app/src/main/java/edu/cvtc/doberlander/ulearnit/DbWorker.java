package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Node;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

import java.util.LinkedList;

public class DbWorker {
    private static final String TAG = "MainActivity";
    private SQLiteDatabase mDb;

    // Initialize the database attribute
    public DbWorker(SQLiteDatabase db) { mDb = db; }

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
                    (int)m.getFavorite());
        }
    }
}
