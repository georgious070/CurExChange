package com.examle.curexchange.data.repository.exchange;

import com.examle.curexchange.data.database.dao.CurrencyDao;
import com.examle.curexchange.data.database.dao.HistoryDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.HistoryEntity;
import com.examle.curexchange.data.remote.ApiExchange;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ExchangeRepository {

    private int value;
    private String firstName;
    private String secondName;
    private ApiExchange apiExchange;
    private HashMap<String, String> mapOfCodeAndName;
    private HistoryDao historyDao;
    private CurrencyDao currencyDao;

    @Inject
    public ExchangeRepository(ApiExchange apiExchange, HistoryDao historyDao, CurrencyDao currencyDao) {
        this.apiExchange = apiExchange;
        this.mapOfCodeAndName = new HashMap<>();
        this.historyDao = historyDao;
        this.currencyDao = currencyDao;
    }

    public Flowable<Float> getResult(String firstName,
                                       String secondName,
                                       int value) {
        setFirstName(firstName);
        setSecondName(secondName);
        setValue(value);
        return loadExchangeValue();
    }

    public Flowable<Float> loadExchangeValue() {
        return queryCodesFromDB()
                .subscribeOn(Schedulers.io())
                .flatMap(stringStringHashMap -> apiExchange
                        .getExchange(stringStringHashMap.get(getFirstName().toLowerCase()),
                                stringStringHashMap.get(getSecondName()).toLowerCase())
                        .map(response -> multiplyResult(currencyRateFromJson(response))))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Flowable<HashMap<String, String>> queryCodesFromDB() {
        Function<List<CurrencyEntity>, HashMap<String, String>> function = entities -> {
            for (int i = 0; i < entities.size(); i++) {
                mapOfCodeAndName.put(entities.get(i).getName(), entities.get(i).getCode());
            }
            return mapOfCodeAndName;
        };
        return currencyDao.queryCryptoCodesByCryptoNames(getFirstName(), getSecondName())
                .observeOn(AndroidSchedulers.mainThread())
                .map(function);
    }

    private String currencyRateFromJson(Object response) {
        String rate = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new Gson().toJson(response));
            if (!jsonObject.getBoolean("success")) {
                jsonObject.getString("error");
            } else {
                JSONObject ticker = jsonObject.getJSONObject("ticker");
                rate = ticker.getString("price");
                insertToHistoryTable(getFirstName(), getSecondName(), Float.parseFloat(rate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rate;
    }

    private Float multiplyResult(String rate) {
        float multiplier = (int) Float.parseFloat(rate);
        return getValue() * multiplier;
    }

    private void insertToHistoryTable(String firstName, String secondName, float result) {
        HistoryEntity historyEntity = new HistoryEntity(firstName, secondName, result);
        historyDao.insertOneRaw(historyEntity);
    }

    private int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    private String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String getSecondName() {
        return secondName;
    }

    private void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
