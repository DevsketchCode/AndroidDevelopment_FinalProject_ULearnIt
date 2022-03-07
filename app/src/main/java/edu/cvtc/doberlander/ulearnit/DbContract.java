package edu.cvtc.doberlander.ulearnit;

import android.provider.BaseColumns;

public class DbContract {


    // References to the db fields
    public static final class TranslationEntry implements BaseColumns {
        // Constants of Table Names
        public static final String TABLE_NAME = "Translations";

        // Constants of Field Names
        public static final String CATEGORY = "category";
        public static final String FIRST_LANGUAGE = "first_language";
        public static final String FIRST_LANGUAGE_WORD = "first_language_word";
        public static final String SECOND_LANGUAGE = "second_language";
        public static final String SECOND_LANGUAGE_WORD = "second_language_word";
        public static final String FAVORITE = "favorite";

        // Constant to create the table
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + "INTEGER PRIMARY KEY, " +
                        CATEGORY + "TEXT NOT NULL, " +
                        FIRST_LANGUAGE + " TEXT NOT NULL, " +
                        FIRST_LANGUAGE_WORD + " TEXT NOT NULL, " +
                        SECOND_LANGUAGE + " TEXT NOT NULL, " +
                        SECOND_LANGUAGE_WORD + " TEXT NOT NULL, " +
                        FAVORITE + " INTEGER NOT NULL)";
    }

}

