package com.examle.curexchange.data.repository;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.DAOs.CurrencyDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.HistoryEntity;

import java.util.Arrays;

import javax.inject.Inject;

public class CurrencyAsyncTask extends AsyncTask<CurrencyEntity, Void, Void> {

    @Inject
    CurrencyDao currencyDao;
    private WaitForInsertCallback waitForInsertCallback;

    public CurrencyAsyncTask(WaitForInsertCallback waitForInsertCallback) {
        this.waitForInsertCallback = waitForInsertCallback;
    }

    @Override
    protected Void doInBackground(CurrencyEntity... currencyEntities) {
        currencyDao.insertAll(Arrays.asList(currencyEntities));
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
