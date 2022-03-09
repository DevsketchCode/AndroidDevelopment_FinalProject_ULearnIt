package edu.cvtc.doberlander.ulearnit;

import android.provider.BaseColumns;

public class DbContract {

    // Private Constructor
    // Will only be used to hold references to the db fields and table creation statements.
    private DbContract() {}

    // References to the db fields
    public static final class TranslationEntry implements BaseColumns {
        // Constants of Table Names
        public static final String TABLE_NAME = "Translations";

        // Constants of Field Names
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_FIRST_LANGUAGE = "first_language";
        public static final String COLUMN_FIRST_LANGUAGE_WORD = "first_language_word";
        public static final String COLUMN_SECOND_LANGUAGE = "second_language";
        public static final String COLUMN_SECOND_LANGUAGE_WORD = "second_language_word";
        public static final String COLUMN_FAVORITE = "favorite";

        // Constants to hold the values for the index name and value of the translations
        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " + INDEX1 + " ON " +
                TABLE_NAME + "(" + _ID + ")";

        // Constant to create the table
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_CATEGORY + " TEXT NOT NULL, " +
                        COLUMN_FIRST_LANGUAGE + " TEXT NOT NULL, " +
                        COLUMN_FIRST_LANGUAGE_WORD + " TEXT NOT NULL, " +
                        COLUMN_SECOND_LANGUAGE + " TEXT NOT NULL, " +
                        COLUMN_SECOND_LANGUAGE_WORD + " TEXT NOT NULL, " +
                        COLUMN_FAVORITE + " INTEGER NOT NULL)";
    }

}

