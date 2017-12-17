package com.examle.curexchange.data.repository.exchange;

import java.util.HashMap;

public interface QueryCodeCallback {

    void onSuccess(HashMap<String, String> mapOfCodeAndName);
}
