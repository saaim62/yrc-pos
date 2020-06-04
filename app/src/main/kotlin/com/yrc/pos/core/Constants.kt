package com.yrc.pos.core

import com.yrc.pos.core.session.Session.getPrice
import com.yrc.pos.core.session.User.getUserPrice
import com.yrc.pos.features.profile.get_profile_service.GetProfileResponse

object Constants {
    const val EMPTY_STRING = ""
    const val EMPTY_INT = 0
    const val SPACE_STRING = " "
    const val LOGGER_TAG = "Yrc App"
    const val DOB_FORMAT = "MMM/dd/yyyy"
    const val NUMBER_REGULAR_EXPRESSION = "^[\\+\\d]?(?:[\\d-.\\s()]*)\$"
}

object Fonts {
    const val titillium_web_BOLD = "fonts/titillium-web_bold.ttf"
    const val titillium_web_LIGHT = "fonts/titillium-web_light.ttf"
    const val titillium_web_REGULAR = "fonts/titillium-web_regular.ttf"
}

object EndPoints {

    const val API_LOGIN = "7ab8ba49"
    const val API_FORGET_PASSWORD = "/api/auth/forget_password"
    const val API_RESET_PASSWORD = "/api/auth/reset_password"
    const val API_SIGN_UP = "/api/auth/signup"
    const val API_VERIFY_OTP = "/api/auth/verify_otp"
    const val API_RESEND_OTP = "/api/auth/resend_otp"
    const val API_GET_PROFILE = "7ab8ba49/a"
    const val API_EDIT_INDIVIDUAL_PROFILE = "/api/auth/edit_iprofile"
}

object Key {
    const val AUTHORIZATION = "Authorization"
}

object Tags {
    const val SupportDialogFragment = "SupportDialogFragment"
}

object Prices {
   // val PRICE_ADULT = getUserPrice()?.adultPrice.toString()
    const val PRICE_OVER65 = 3000
    const val PRICE_1822 = 4000
    const val PRICE_RACEGOER = 5000
}