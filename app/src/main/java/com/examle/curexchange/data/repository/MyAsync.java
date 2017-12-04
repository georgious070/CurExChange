package com.examle.curexchange.data.repository;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.CurrencyContract;
import com.examle.curexchange.data.database.HistoryContract;

public class MyAsync extends AsyncTask<ContentValues, Void, Void> {

    private WaitForInsertCallback waitForInsertCallback;
    private String tableName;

    public MyAsync(WaitForInsertCallback waitForInsertCallback, String tableName) {
        this.waitForInsertCallback = waitForInsertCallback;
        this.tableName = tableName;
    }

    @Override
    protected Void doInBackground(ContentValues... contentValues) {
        switch (tableName) {
            case CurrencyContract.CurrencyEntry.TABLE_NAME:
                for (ContentValues value : contentValues) {
                    App.getApp().getContentResolver().insert(CurrencyContract.CurrencyEntry.CONTENT_URI, value);
                }
                break;
            case HistoryContract.HistoryEntry.TABLE_NAME:
                App.getApp().getContentResolver().insert(HistoryContract.HistoryEntry.CONTENT_URI, contentValues[0]);
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (waitForInsertCallback != null) {
            waitForInsertCallback.onSuccess();
        }
    }
}
