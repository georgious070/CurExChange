package com.examle.curexchange.ui.base

import android.app.ProgressDialog
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity

open class BaseActivity : MvpAppCompatActivity(), BaseView {

    var progressDialog: ProgressDialog? = null

    override fun showToast(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            if (progressDialog != null) {
                progressDialog!!.show()
            } else {
                progressDialog = ProgressDialog.show(this, null, "Please wait while download will be success", true, false)
                progressDialog!!.show()
            }
        } else {
            if (progressDialog != null) progressDialog!!.dismiss()
        }
    }
}