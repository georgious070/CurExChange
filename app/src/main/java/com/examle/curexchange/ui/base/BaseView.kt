package com.examle.curexchange.ui.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView{

    fun showToast(error: String)

    fun showProgress(isShow: Boolean)
}