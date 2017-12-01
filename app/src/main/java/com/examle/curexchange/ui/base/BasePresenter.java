package com.examle.curexchange.ui.base;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class BasePresenter<T extends BaseView> extends MvpPresenter<T> {
}
