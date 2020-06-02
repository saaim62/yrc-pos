package com.yrc.pos.features.login.login_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants

class LoginRequest {

    @SerializedName("driver")
    var driver: String? = Constants.EMPTY_STRING

    @SerializedName("dutyNumber")
    var dutyNumber: String? = Constants.EMPTY_STRING

    @SerializedName("pin")
    var pin: String? = Constants.EMPTY_STRING

}