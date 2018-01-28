package com.examle.curexchange.data.repository.currency

import com.examle.curexchange.data.database.dao.CurrencyDao
import com.examle.curexchange.data.database.entities.CurrencyEntity
import com.examle.curexchange.data.model.pojo.CryptoCode
import com.examle.curexchange.data.model.pojo.Row
import com.examle.curexchange.data.remote.ApiCryptoCode
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyRepository @Inject constructor(val apiCryptoCode: ApiCryptoCode,
                                             val currencyDao: CurrencyDao) {

    var rows: MutableList<Row> = ArrayList()
    var currencyEntities: MutableList<CurrencyEntity> = ArrayList()

    fun getNames(): Flowable<MutableList<String>> =
            currencyDao.queryOneLine()
                    .map { ignore -> false }
                    .switchIfEmpty(Maybe.just(true))
                    .toFlowable()
                    .subscribeOn(Schedulers.io())
                    .flatMap { isEmpty ->
                        if (isEmpty) {
                            apiCryptoCode.getCryptoCodes()
                                    .flatMap { code ->
                                        insertToDbFromNetwork(code)
                                        currencyDao.queryCryptoNames()
                                    }
                        } else {
                            currencyDao.queryCryptoNames()
                        }
                    }

    fun insertToDbFromNetwork(code: CryptoCode) {
        rows.addAll(code.rows)
        for (i in rows.indices) {
            currencyEntities.add(CurrencyEntity(rows[i].code, rows[i].name))
        }
        currencyDao.insertAll(currencyEntities)
    }
}