package com.examle.curexchange.data.repository.exchange

import com.examle.curexchange.data.database.dao.CurrencyDao
import com.examle.curexchange.data.database.dao.HistoryDao
import com.examle.curexchange.data.database.entities.HistoryEntity
import com.examle.curexchange.data.remote.ApiExchange
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap
import javax.inject.Inject

class ExchangeRepository @Inject constructor(val apiExchange: ApiExchange,
                                             val currencyDao: CurrencyDao,
                                             val historyDao: HistoryDao) {

    fun getResult(firstName: String, secondName: String, value: Int): Flowable<Float> {
        var mapOfCodeAndName = HashMap<String, String>()
        return currencyDao.queryCryptoCodesByCryptoNames(firstName, secondName)
                .subscribeOn(Schedulers.io())
                .map { entities ->
                    for (i in entities.indices) {
                        mapOfCodeAndName.put(entities[i].name, entities[i].code)
                    }
                    mapOfCodeAndName
                }
                .flatMap { hashMap ->
                    apiExchange.getExchange(hashMap[firstName]!!,
                            hashMap[secondName]!!)
                            .map { response ->
                                var rate: String? = null
                                val jsonObject: JSONObject
                                try {
                                    jsonObject = JSONObject(Gson().toJson(response))
                                    if (!jsonObject.getBoolean("success")) {
                                        jsonObject.getString("error")
                                    } else {
                                        val ticker = jsonObject.getJSONObject("ticker")
                                        rate = ticker.getString("price")
                                        historyDao.insertOneRaw(HistoryEntity(firstName,
                                                secondName,
                                                rate.toFloat()))
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                                value * (rate?.toFloat() ?: 0f)
                            }
                }
    }
}