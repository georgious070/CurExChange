package com.examle.curexchange.data.repository.currency;

import android.os.AsyncTask;

import com.examle.curexchange.data.database.dao.CurrencyDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;

import java.util.Arrays;

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
