package com.yrc.pos.core.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.yrc.pos.core.Constants
import com.yrc.pos.features.profile.get_profile_service.GetProfileResponse

object User {

    private lateinit var userPreferences: SharedPreferences
    private const val userPreferenceName = "UserPreferences"

    fun initialize(context: Context) {
        userPreferences = context.getSharedPreferences(userPreferenceName, Context.MODE_PRIVATE)
    }


    fun saveUserProfile(userProfile: GetProfileResponse.Users) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putString(UserConstants.Key_User_Profile, Gson().toJson(userProfile))
        preferenceEditor.apply()
    }

//    fun getUserName(): String? {
//    //    return getUserProfile()!!.firstName
//    }

    fun getUserProfile(): GetProfileResponse.Users? {
        val userProfile =userPreferences.getString(UserConstants.Key_DRIVER, Constants.EMPTY_STRING)
        if (userProfile.isNotEmpty()) {
            //return GetProfileResponse.Users()
            return Gson().fromJson(
                userProfile,
                GetProfileResponse.Users::class.java
            )
        }
        else {
            return Gson().fromJson<GetProfileResponse.Users>(
                userProfile,
                GetProfileResponse.Users::class.java
            )
        }
    }

    fun wipeUserData() {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.remove(UserConstants.Key_User_Profile)
        preferenceEditor.remove(UserConstants.Key_Profile_Status)
        preferenceEditor.remove(UserConstants.Key_DRIVER)
        preferenceEditor.remove(UserConstants.Key_PIN)
        preferenceEditor.remove(UserConstants.Key_DUTYNUMBER)
        preferenceEditor.apply()
    }
}