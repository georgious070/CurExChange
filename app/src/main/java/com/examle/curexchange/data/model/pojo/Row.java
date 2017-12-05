package com.examle.curexchange.data.model.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Row {

    @SerializedName("code")
    private String Code;
    @SerializedName("name")
    private String Name;
    @SerializedName("statuses")
    private List<String> Statuses;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getStatuses() {
        return Statuses;
    }

    public void setStatuses(List<String> statuses) {
        Statuses = statuses;
    }
}
