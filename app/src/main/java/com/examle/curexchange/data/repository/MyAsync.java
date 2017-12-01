package com.examle.curexchange.data.repository;


import android.content.ContentValues;
import android.os.AsyncTask;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.CurrencyContract;

public class MyAsync extends AsyncTask<ContentValues, Void, Void> {

    private WaitForInsertCallback waitForInsertCallback;

    public MyAsync(WaitForInsertCallback waitForInsertCallback) {
        this.waitForInsertCallback = waitForInsertCallback;
    }

    @Override
    protected Void doInBackground(ContentValues... contentValues) {

        for (ContentValues value : contentValues) {
            App.getApp().getContentResolver().insert(CurrencyContract.CurrencyEntry.CONTENT_URI, value);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        waitForInsertCallback.onSuccess();
    }
}
