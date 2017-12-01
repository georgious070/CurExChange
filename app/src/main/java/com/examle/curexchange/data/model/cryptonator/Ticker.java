package com.examle.curexchange.data.model.cryptonator;

import com.google.gson.annotations.SerializedName;

public class Ticker {

    @SerializedName("base")
    private String mBase;
    @SerializedName("change")
    private String mChange;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("target")
    private String mTarget;
    @SerializedName("volume")
    private String mVolume;

    public String getBase() {
        return mBase;
    }

    public void setBase(String base) {
        mBase = base;
    }

    public String getChange() {
        return mChange;
    }

    public void setChange(String change) {
        mChange = change;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getTarget() {
        return mTarget;
    }

    public void setTarget(String target) {
        mTarget = target;
    }

    public String getVolume() {
        return mVolume;
    }

    public void setVolume(String volume) {
        mVolume = volume;
    }
}
