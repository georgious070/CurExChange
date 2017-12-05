package com.examle.curexchange.ui.base;

import com.arellomobile.mvp.MvpPresenter;

public abstract class BasePresenter<T extends BaseView> extends MvpPresenter<T> {

    void showError(String error) {
        getViewState().showToast(error);
    }

    public void loadProgress(boolean isProgress){
        getViewState().showProgress(isProgress);
    }
}
