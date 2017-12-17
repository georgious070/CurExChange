package com.examle.curexchange.ui.result;

public interface ExchangeCallback {

    void onSuccess(int result);

    void onFailure(String error);
}
