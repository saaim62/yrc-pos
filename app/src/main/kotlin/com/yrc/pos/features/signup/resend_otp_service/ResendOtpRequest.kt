package com.yrc.pos.features.signup.resend_otp_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants

class ResendOtpRequest {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("number")
    var number: String? = null

    @SerializedName("mode")
    var userType: String? = Constants.EMPTY_STRING
}