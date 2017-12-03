package com.examle.curexchange.data.repository;


import java.util.Map;

public interface QueryCodeCallback {

    void onSuccess(Map<String, String> mapOfCodeAndName);
}
