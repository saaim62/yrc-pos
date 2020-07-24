package com.yrc.pos.features.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yrc.pos.core.base.models.AccountRepo
import com.yrc.pos.core.base.viewmodels.BaseViewModel
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import io.reactivex.rxjava3.core.Notification

class LoginViewModel(private val accountRepo: AccountRepo) : BaseViewModel(accountRepo){
    val observable: MutableLiveData<Notification<Boolean>> = MutableLiveData()

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)
        if (apiResponse is LoginResponse) {
            User.saveUserPrice(apiResponse.price!!)
            User.saveUserProfile(apiResponse.user!!)
            observable.postValue(Notification.createOnNext(true))
        }
    }

    override fun onApiFailure(errorCode: Int) {
            super.onApiFailure(errorCode)
        observable.postValue(Notification.createOnNext(false))
    }

    fun fetchData(emailOrNumber: String, password: String, dutyNumber: String) {
        val loginRequest = LoginRequest()
        loginRequest.driver = emailOrNumber
        loginRequest.pin = password
        loginRequest.dutyNumber = dutyNumber
        accountRepo.loginApi(this, loginRequest)
        Session.clearSession()
    }
}