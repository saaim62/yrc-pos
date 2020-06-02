package com.yrc.pos.core.services

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.yrc.pos.features.login.LoginActivity

class SessionManagement (context: Context){
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var con: Context = context
    private var PRIVATE_MODE: Int = 0

    init {
        pref = con.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        const val PREF_NAME: String = "KotlinDemo"
        const val IS_LOGIN: String = "isLoggedIn"
        const val KEY_DRIVER: String = "driver"
        const val KEY_PIN: String = "pin"
        const val KEY_DUTYNUMBER: String = "dutyNumber"
    }

    fun createLoginSession(
        driver: String,
        pin: String,
        dutyNumber: String,
        toString: String
    ) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_DRIVER, driver)
        editor.putString(KEY_PIN, pin)
        editor.putString(KEY_DUTYNUMBER, dutyNumber)
        editor.commit()
    }

    fun checkLogin() {
        if (!this.isLoggedIn()) {
            val i = Intent(con, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            con.startActivity(i)
        }
    }

    fun getDriver(): String? {
        return pref.getString(KEY_DRIVER, "")
    }

    fun getPin(): String? {
        return pref.getString(KEY_PIN, "")
    }

    private fun getDutyNumber(): String? {
        return pref.getString(KEY_DUTYNUMBER, null)

    }


    fun LogoutUser() {
        editor.clear()
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        return getDutyNumber()!=null
    }

}