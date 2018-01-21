package com.examle.curexchange.ui.base

import com.arellomobile.mvp.MvpPresenter

abstract class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    fun showError(error: String) {
        viewState.showToast(error)
    }

    fun loadProgress(isProgress: Boolean){
        viewState.showProgress(isProgress)
    }
}