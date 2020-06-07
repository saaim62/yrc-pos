package com.yrc.pos.features.profile.get_profile_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class GetProfileResponse : YrcBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING


}
