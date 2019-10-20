package com.yrc.pos.features.signup.code_verification_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants

open class VerifyOtpRequest {

    @SerializedName("otp_code_email")
    var otpCodeEmail: String? = null

    @SerializedName("otp_code")
    var otpCode: String? = null

    @SerializedName("mode")
    var userType: String? = Constants.EMPTY_STRING
}