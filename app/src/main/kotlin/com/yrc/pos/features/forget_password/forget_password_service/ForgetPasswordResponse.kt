package com.yrc.pos.features.forget_password.forget_password_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class ForgetPasswordResponse : YrcBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING
}