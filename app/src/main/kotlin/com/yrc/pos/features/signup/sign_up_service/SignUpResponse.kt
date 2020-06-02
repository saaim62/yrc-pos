package com.yrc.pos.features.signup.sign_up_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class SignUpResponse : YrcBaseApiResponse() {

    @SerializedName("verified_by_email_or_phoneNumber")
    var accountVerificationStatus: Boolean? = false

    @SerializedName("success")
    var success: Boolean? = false

//    @SerializedName("message")
//    var message: String? = Constants.EMPTY_STRING
}