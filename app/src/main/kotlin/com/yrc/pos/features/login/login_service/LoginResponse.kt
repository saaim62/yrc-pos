package com.yrc.pos.features.login.login_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class LoginResponse : YrcBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING

    @SerializedName("accessToken")
    var accessToken: String? = Constants.EMPTY_STRING
    var code = 0

    var price: Price? = null

    class Price {
        var code = 0
        var adultPrice = 0
        var over65Price = 0
        var youngPrice = 0
        var racePrice = 0
    }
    var user: Users? = null

    class Users {
        var site: String? = null
        var code = 0
        var driver: String? = null
        var pin: String? = null
        var dutyNumber: String? = null
    }
}