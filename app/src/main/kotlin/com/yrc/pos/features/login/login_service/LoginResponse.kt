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

    var code = 0
    var user: Users? = null
    class Users {
        var driver: String? = null
        var pin: String? = null
        var dutyNumber: String? = null

    }


}