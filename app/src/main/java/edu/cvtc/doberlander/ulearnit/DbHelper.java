package edu.cvtc.doberlander.ulearnit;

import static edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ulearnit.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TranslationEntry.SQL_CREATE_TABLE);

        DbWorker worker = new DbWorker(db);
        worker.insertEntries();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
