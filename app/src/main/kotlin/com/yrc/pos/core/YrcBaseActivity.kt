package com.yrc.pos.core

import androidx.appcompat.app.AppCompatActivity
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.providers.ProgressDialogProvider
import com.yrc.pos.core.services.ApiCallbacks
import com.yrc.pos.core.services.YrcBaseApiResponse

abstract class YrcBaseActivity : AppCompatActivity(), ApiCallbacks {

    override fun doBeforeApiCall() {
    //    ProgressDialogProvider.show(this)
    }

    override fun doAfterApiCall() {
   //     ProgressDialogProvider.dismiss()
    }

    override fun onApiFailure(errorCode: Int) {
        AlertDialogProvider.showFailureDialog(this, DialogTheme.ThemeWhite)
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {}
}