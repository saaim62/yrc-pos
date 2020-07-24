package com.yrc.pos.core.base.viewmodels

import androidx.lifecycle.ViewModel
import com.yrc.pos.core.base.models.BaseRepo
import com.yrc.pos.core.services.ApiCallbacks
import com.yrc.pos.core.services.YrcBaseApiResponse

open class BaseViewModel(private val baseRepo: BaseRepo) : ViewModel(), ApiCallbacks {

    override fun doBeforeApiCall() {

    }

    override fun doAfterApiCall() {
    }

    override fun onNoNetworkAvailable() {
    }

    override fun onApiFailure(errorCode: Int) {
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
    }
}