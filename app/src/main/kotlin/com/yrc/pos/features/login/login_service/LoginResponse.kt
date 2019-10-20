package com.yrc.pos.features.login.login_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class LoginResponse : YrcBaseApiResponse() {

    @SerializedName("status")
    var status: Boolean? = false

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING

    @SerializedName("accessToken")
    var accessToken: String? = Constants.EMPTY_STRING

    @SerializedName("tokenType")
    var tokenType: String? = Constants.EMPTY_STRING

    @SerializedName("secretKey")
    var secretKey: String? = Constants.EMPTY_STRING
}