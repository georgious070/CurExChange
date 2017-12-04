package com.examle.curexchange.data.repository;

import java.util.HashMap;

public interface QueryCodeCallback {

    void onSuccess(HashMap<String, String> mapOfCodeAndName);
}
