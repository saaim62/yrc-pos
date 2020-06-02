package com.yrc.pos.core.session

import android.content.Context
import android.content.SharedPreferences
import com.yrc.pos.core.Constants

object Session {

    private lateinit var sessionPreferences: SharedPreferences
    private const val sessionPreferenceName = "SessionPreferences"

    fun initialize(context: Context){
        sessionPreferences = context.getSharedPreferences(sessionPreferenceName, Context.MODE_PRIVATE)
    }

    fun storeSession(
        accessToken: String?,
        secretKey: String?,
        tokenType: String?,
        driver: String?,
        pin: String?,
        dutyNumber: String?
    ) {
        val preferenceEditor = sessionPreferences.edit()
        preferenceEditor.putString(SessionConstants.Key_SecretKey, secretKey)
        preferenceEditor.putString(SessionConstants.Key_TokenType, tokenType)
        preferenceEditor.putString(SessionConstants.Key_AccessToken, tokenType + Constants.SPACE_STRING + accessToken)
        preferenceEditor.apply()
    }

    fun isSessionAvailable() : Boolean {
        return sessionPreferences.getString(SessionConstants.Key_AccessToken, Constants.EMPTY_STRING).isNotEmpty()
    }

    fun getAccessToken() : String {
        return sessionPreferences.getString(SessionConstants.Key_AccessToken, Constants.EMPTY_STRING)
    }

    fun getSecretKey() : String {
        return sessionPreferences.getString(SessionConstants.Key_SecretKey, Constants.EMPTY_STRING)
    }

    fun getTokenType() : String {
        return sessionPreferences.getString(SessionConstants.Key_TokenType, Constants.EMPTY_STRING)
    }

    fun clearSession() {
        val preferenceEditor = sessionPreferences.edit()
        preferenceEditor.remove(SessionConstants.Key_AccessToken)
        preferenceEditor.remove(SessionConstants.Key_SecretKey)
        preferenceEditor.remove(SessionConstants.Key_TokenType)
        preferenceEditor.apply()
        User.wipeUserData()
    }
}