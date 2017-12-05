package com.examle.curexchange.ui.base;

import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

public class BaseActivity extends MvpAppCompatActivity implements BaseView {

    @Override
    public void showToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
