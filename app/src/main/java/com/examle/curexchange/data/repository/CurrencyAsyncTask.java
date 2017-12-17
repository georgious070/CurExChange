package com.examle.curexchange.data.repository;

import android.arch.persistence.room.Insert;
import android.os.AsyncTask;

import com.examle.curexchange.data.database.DAOs.CurrencyDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


public class CurrencyAsyncTask extends AsyncTask<CurrencyEntity, Void, Void> {

    CurrencyDao currencyDao;
    private WaitForInsertCallback waitForInsertCallback;

    public CurrencyAsyncTask(WaitForInsertCallback waitForInsertCallback, CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
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
