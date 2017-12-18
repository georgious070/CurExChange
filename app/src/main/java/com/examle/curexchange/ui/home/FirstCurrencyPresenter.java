package com.examle.curexchange.ui.home;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

@InjectViewState
public class FirstCurrencyPresenter extends BasePresenter<FirstCurrencyView> {

    private List<String> names;

    @Inject
    CurrencyInteractor currencyInteractor;
    private FirstCurrencyCallback firstCurrencyCallback = new FirstCurrencyCallback() {
        @Override
        public void onSuccess(final List<String> names) {
            getViewState().showData(names);
            setNames(names);
            loadProgress(false);

        }
    };

    public FirstCurrencyPresenter() {
        names = new ArrayList<>();
        App.getApp().getAppComponent().inject(this);
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadProgress(true);
        currencyInteractor.loadData().subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> strings) {
                getViewState().showData(strings);
                loadProgress(false);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void buttonClicked(EditText editText) {
        int value = Integer.parseInt(editText.getText().toString());
        getViewState().handleFloatButton(getNames(), value);
    }

    @Override
    public void loadProgress(boolean isProgress) {
        super.loadProgress(isProgress);
    }
}
