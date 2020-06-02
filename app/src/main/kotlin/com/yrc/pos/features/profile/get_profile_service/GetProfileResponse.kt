package com.yrc.pos.features.profile.get_profile_service

import com.google.gson.annotations.SerializedName
import com.yrc.pos.core.Constants
import com.yrc.pos.core.services.YrcBaseApiResponse

class GetProfileResponse : YrcBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING

    @SerializedName("details")
    var details: Details? = null

    var user: Users? = null

    class Users {
        var driver: String? = null
        var pin: String? = null
        var dutyNumber: String? = null

    }


    class Details {

        @SerializedName("id")
        var id: Int? = 0

        @SerializedName("customerType")
        var customerType: String? = Constants.EMPTY_STRING

        @SerializedName("personType")
        var personType: String? = Constants.EMPTY_STRING

        @SerializedName("nationality")
        var nationality: String? = Constants.EMPTY_STRING

        @SerializedName("birthDate")
        var birthDate: String? = Constants.EMPTY_STRING

        @SerializedName("prefix")
        var prefix: String? = Constants.EMPTY_STRING

        @SerializedName("gender")
        var gender: String? = Constants.EMPTY_STRING

        @SerializedName("registrationNo")
        var registrationNo: String? = Constants.EMPTY_STRING

        @SerializedName("registrationType")
        var registrationType: String? = Constants.EMPTY_STRING

        @SerializedName("number")
        var number: String? = Constants.EMPTY_STRING

        @SerializedName("addressType")
        var addressType: String? = Constants.EMPTY_STRING

        @SerializedName("address")
        var address: String? = Constants.EMPTY_STRING

        @SerializedName("cityName")
        var cityName: String? = Constants.EMPTY_STRING

        @SerializedName("country")
        var country: String? = Constants.EMPTY_STRING

        @SerializedName("zipCode")
        var zipCode: String? = Constants.EMPTY_STRING

        @SerializedName("companyName")
        var companyName: String? = Constants.EMPTY_STRING

        @SerializedName("enableNotification")
        var enableNotification: Boolean? = false

        @SerializedName("enableNumberVisibility")
        var enableNumberVisibility: Boolean? = false

        @SerializedName("enableAgeVisibility")
        var enableAgeVisibility: Boolean? = false

        @SerializedName("email")
        var email: String? = Constants.EMPTY_STRING

        @SerializedName("firstName")
        var firstName: String? = Constants.EMPTY_STRING

        @SerializedName("middleName")
        var middleName: String? = Constants.EMPTY_STRING

        @SerializedName("lastName")
        var lastName: String? = Constants.EMPTY_STRING

        @SerializedName("_interests")
        var interests: Array<String>? = null

        @SerializedName("avatar")
        var avatar: String? = Constants.EMPTY_STRING

        @SerializedName("employee")
        var employee: Boolean? = false

//        @SerializedName("driver")
//        var driver: String? = Constants.EMPTY_STRING
//
//        @SerializedName("pin")
//        var pin: String? = Constants.EMPTY_STRING
//
//        @SerializedName("dutyNumber")
//        var dutyNumber: String? = Constants.EMPTY_STRING
    }
}