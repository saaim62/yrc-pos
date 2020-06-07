package com.yrc.pos.core.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.yrc.pos.core.Constants
import com.yrc.pos.features.login.login_service.LoginResponse
import com.yrc.pos.features.profile.get_profile_service.GetProfileResponse

object User {

    private lateinit var userPreferences: SharedPreferences
    private const val userPreferenceName = "UserPreferences"

    fun initialize(context: Context) {
        userPreferences = context.getSharedPreferences(userPreferenceName, Context.MODE_PRIVATE)
    }
    fun saveUserPrice(userPrice: LoginResponse.Price) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putString(SessionConstants.Key_Price, Gson().toJson(userPrice))
        preferenceEditor.apply()
    }

    fun getUserPrice(): LoginResponse.Price {
        val userProfile = userPreferences.getString(SessionConstants.Key_Price, Constants.EMPTY_STRING)
        if (userProfile.isEmpty()) {
            return LoginResponse.Price()

        } else {
            return Gson().fromJson(
                userProfile,
                LoginResponse.Price::class.java
            )
        }
    }

    fun saveUserProfile(userProfile: LoginResponse.Users) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putString(UserConstants.Key_User_Profile, Gson().toJson(userProfile))
        preferenceEditor.apply()
    }

//    fun getUserPrice(): String? {
//        return getUserPrice()!!.
//    }

    fun getUserProfile(): LoginResponse.Users? {
        val userProfile = userPreferences.getString(UserConstants.Key_User_Profile, Constants.EMPTY_STRING)
        if (userProfile.isEmpty()) {
              return LoginResponse.Users()

        } else {
            return Gson().fromJson(
                userProfile,
                LoginResponse.Users::class.java
            )
        }
    }

    fun wipeUserData() {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.remove(UserConstants.Key_User_Profile)
        preferenceEditor.apply()
    }
}