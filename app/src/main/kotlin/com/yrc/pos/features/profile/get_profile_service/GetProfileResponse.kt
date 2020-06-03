package com.yrc.pos.features.profile.get_profile_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class GetProfileResponse : YrcBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING



    var code = 0
    var user: Users? = null

    class Users {
        var driver: String? = null
        var pin: String? = null
        var dutyNumber: String? = null

    }

//        @SerializedName("driver")
//        var driver: String? = Constants.EMPTY_STRING
//
//        @SerializedName("pin")
//        var pin: String? = Constants.EMPTY_STRING
//
//        @SerializedName("dutyNumber")
//        var dutyNumber: String? = Constants.EMPTY_STRING
}
