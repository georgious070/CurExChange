package com.examle.curexchange.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.examle.curexchange.data.database.CurrencyContract.CurrencyEntry;
import com.examle.curexchange.data.database.HistoryContract.HistoryEntry;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "currency.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CURRENCY_TABLE = "CREATE TABLE " + CurrencyEntry.TABLE_NAME + " ("
                + CurrencyEntry.COLUMN_CODE + " TEXT PRIMARY KEY,"
                + CurrencyEntry.COLUMN_CRYPTO_NAME + " TEXT);";
        db.execSQL(SQL_CREATE_CURRENCY_TABLE);
        String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " ("
                + HistoryEntry._ID + " INTEGER PRIMARY KEY,"
                +HistoryEntry.COLUMN_FIRST_CURRENCY + " TEXT,"
                + HistoryEntry.COLUMN_SECOND_CURRENCY + " TEXT,"
                + HistoryEntry.COLUMN_RESULT + " REAL);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //no-op
    }
}
