package com.yrc.pos.core.services

interface ApiCallbacks {

    fun doBeforeApiCall()

    fun doAfterApiCall()

    fun onNoNetworkAvailable()

    fun onApiFailure(errorCode: Int)

    fun onApiSuccess(apiResponse: YrcBaseApiResponse)
}