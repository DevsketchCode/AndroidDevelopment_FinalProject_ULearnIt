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
        public static final String COLUMN_SUBCATEGORY = "sub_category";
        public static final String COLUMN_FIRST_LANGUAGE = "first_language";
        public static final String COLUMN_FIRST_LANGUAGE_ENTRY = "first_language_entry";
        public static final String COLUMN_FIRST_LANGUAGE_ENTRY_ROMANIZED = "first_language_entry_romanized";
        public static final String COLUMN_FIRST_LANGUAGE_EXAMPLE = "first_language_example";
        public static final String COLUMN_SECOND_LANGUAGE = "second_language";
        public static final String COLUMN_SECOND_LANGUAGE_ENTRY = "second_language_entry";
        public static final String COLUMN_SECOND_LANGUAGE_ENTRY_ROMANIZED = "second_language_entry_romanized";
        public static final String COLUMN_SECOND_LANGUAGE_EXAMPLE = "second_language_example";
        public static final String COLUMN_ENTRY_TYPE = "entry_type"; // sentence, question, phrase, or word classifications (noun, verb, particle, etc)
        public static final String COLUMN_TENSE = "tense"; // Past Present Future or NA (Not Applicable)
        public static final String COLUMN_IS_PLURAL = "is_plural"; // Singular Plural or NA (Not Applicable)
        public static final String COLUMN_GENDER = "gender"; // Masculine, Feminine or NA (Not Applicable)
        public static final String COLUMN_FORMALITY = "formality"; // Casual, Formal, Informal, Polite
        public static final String COLUMN_PERCENT_LEARNED = "percent_learned"; // lowest is well learned, higher is needing to learn better
        public static final String COLUMN_NOTES = "notes";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_AUDIO = "audio";
        public static final String COLUMN_USER_AUDIO = "user_audio";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_TAGS = "tags";
        public static final String COLUMN_MODIFIED_DATE = "modified_Date";
        public static final String COLUMN_ON_QUICKLIST = "on_quicklist";
        public static final String COLUMN_ARCHIVED = "archived";



        // Constants to hold the values for the index name and value of the translations
        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " + INDEX1 + " ON " +
                TABLE_NAME + "(" + _ID + ")";

        // Constant to create the table
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_CATEGORY + " TEXT NOT NULL, " +
                        COLUMN_SUBCATEGORY + " TEXT NOT NULL, " +
                        COLUMN_FIRST_LANGUAGE + " TEXT NOT NULL, " +
                        COLUMN_FIRST_LANGUAGE_ENTRY + " TEXT NOT NULL, " +
                        COLUMN_FIRST_LANGUAGE_ENTRY_ROMANIZED + " TEXT NOT NULL, " +
                        COLUMN_FIRST_LANGUAGE_EXAMPLE + " TEXT NOT NULL, " +
                        COLUMN_SECOND_LANGUAGE + " TEXT NOT NULL, " +
                        COLUMN_SECOND_LANGUAGE_ENTRY + " TEXT NOT NULL, " +
                        COLUMN_SECOND_LANGUAGE_ENTRY_ROMANIZED + " TEXT NOT NULL, " +
                        COLUMN_SECOND_LANGUAGE_EXAMPLE + " TEXT NOT NULL, " +
                        COLUMN_ENTRY_TYPE + " TEXT NOT NULL, " +
                        COLUMN_TENSE + " TEXT NOT NULL, " +
                        COLUMN_IS_PLURAL + " BOOLEAN NOT NULL, " +
                        COLUMN_GENDER + " TEXT NOT NULL, " +
                        COLUMN_FORMALITY + " TEXT NOT NULL, " +
                        COLUMN_PERCENT_LEARNED + " INTEGER NOT NULL, " +
                        COLUMN_NOTES + " TEXT NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL, " +
                        COLUMN_AUDIO + " TEXT NOT NULL, " +
                        COLUMN_USER_AUDIO + " TEXT NOT NULL, " +
                        COLUMN_FAVORITE + " INTEGER NOT NULL, " +
                        COLUMN_TAGS + " TEXT NOT NULL, " +
                        COLUMN_MODIFIED_DATE + " TEXT NOT NULL, " +
                        COLUMN_ON_QUICKLIST + " BOOLEAN NOT NULL, " +
                        COLUMN_ARCHIVED + " BOOLEAN NOT NULL)";
    }

}

