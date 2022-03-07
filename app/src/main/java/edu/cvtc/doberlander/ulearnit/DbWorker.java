package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class DbWorker {

    private SQLiteDatabase mDb;

    // Initialize the database attribute
    public DbWorker(SQLiteDatabase db) { mDb = db; }

    private void insertEntry(String category, String firstLang, String firstLangWord, String secondLang, String secondLangWord, int favorite) {
        ContentValues values = new ContentValues();
        values.put(DbContract.TranslationEntry.CATEGORY, category);
        values.put(DbContract.TranslationEntry.FIRST_LANGUAGE, firstLang);
        values.put(DbContract.TranslationEntry.FIRST_LANGUAGE_WORD, firstLangWord);
        values.put(DbContract.TranslationEntry.SECOND_LANGUAGE, secondLang);
        values.put(DbContract.TranslationEntry.SECOND_LANGUAGE_WORD, secondLangWord);
        values.put(DbContract.TranslationEntry.FAVORITE, favorite);
    }

    // Populate the database with Initial Data
    public void insertEntries() {
        TranslationList list = new TranslationList();
        LinkedList<TranslationModel> tm = list.GetTranslations("Greetings");
        tm.addAll(list.GetTranslations("Number"));

        for (TranslationModel m : tm) {
            insertEntry(m.getCategory(),
                    m.getFirstLanguage(),
                    m.getFirstLanguageWord(),
                    m.getSecondLanguage(),
                    m.getSecondLanguageWord(),
                    m.getFavorite());
        }

    }
}
