package com.yrc.pos.features.login.login_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants

class LoginRequest {

    @SerializedName("email")
    var email: String? = Constants.EMPTY_STRING

    @SerializedName("number")
    var number: String? = Constants.EMPTY_STRING

    @SerializedName("password")
    var password: String? = Constants.EMPTY_STRING

    var driver: String? = null
    var pin: String? = null
    var dutyNumber: String? = null
}