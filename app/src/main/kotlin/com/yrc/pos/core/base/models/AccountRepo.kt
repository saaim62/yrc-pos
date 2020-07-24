package com.yrc.pos.core.base.models

import com.yrc.pos.core.prefs.YrcPrefManager
import com.yrc.pos.core.services.ApiCallbacks
import com.yrc.pos.core.services.YrcAPIManger
import com.yrc.pos.features.login.login_service.LoginRequest

class AccountRepo(
    private val apiManager: YrcAPIManger,
    private val prefManager: YrcPrefManager
) : BaseRepo(prefManager) {

    fun loginApi(apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        apiManager.loginApi(apiCallbacks, loginRequest)
    }

}