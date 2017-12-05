package com.examle.curexchange.ui.base;

import android.app.ProgressDialog;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

public class BaseActivity extends MvpAppCompatActivity implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void showToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean inProgress) {
        if (inProgress) {
            if (progressDialog != null) {
                progressDialog.show();
            } else {
                progressDialog = ProgressDialog.show(this, null, "Please wait while download will be success", true, false);
                progressDialog.show();
            }
        } else {
            if (progressDialog != null) progressDialog.dismiss();
        }
    }
}
