package com.yrc.pos.core.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.ArrayMap
import android.util.SparseIntArray
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yrc.pos.core.Constants
import org.json.JSONObject


/**
 *
 * Class to read/write data in SharedPreferences with all possible input type values
 * i.e. String, Int, Boolean, Double, Float, Long
 */

class YrcPrefManager(private val context: Context) {

    private val mPref: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null
    private val PREFERENCE_NAME = "PREF"

    init {
        mPref = context.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }


    fun put(key: String, value: String) {
        doEdit()
        mEditor!!.putString(key, value)
        doCommit()
    }

    fun put(key: String, value: Int) {
        doEdit()
        mEditor!!.putInt(key, value)
        doCommit()
    }

    fun put(key: String, value: Boolean) {
        doEdit()
        mEditor!!.putBoolean(key, value)
        doCommit()
    }


    fun put(key: String, value: Float) {
        doEdit()
        mEditor!!.putFloat(key, value)
        doCommit()
    }

    fun put(key: String, value: Double) {
        doEdit()
        mEditor!!.putString(key, value.toString())
        doCommit()
    }


    fun put(key: String, value: Long) {
        doEdit()
        mEditor!!.putLong(key, value)
        doCommit()
    }

    fun put(key: String, value: MutableSet<String>) {
        doEdit()
        mEditor!!.putStringSet(key, value)
        doCommit()
    }

    fun getSet(key: String): MutableSet<String> {
        return mPref.getStringSet(key, null) ?: mutableSetOf()
    }

    fun getString(key: String, defaultValue: String): String? {
        return mPref.getString(key, defaultValue)
    }

    fun getString(key: String): String? {
        return mPref.getString(key, null)
    }

    fun getStringWithoutNull(key: String): String {
        return mPref.getString(key, "") ?: ""
    }


    fun getInt(key: String): Int {
        return mPref.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mPref.getInt(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return mPref.getLong(key, 0)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return mPref.getLong(key, defaultValue)
    }

    fun getFloat(key: String): Float {
        return mPref.getFloat(key, 0f)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return mPref.getFloat(key, defaultValue)
    }

    fun putArrayMap(key: String, value: ArrayMap<String, SparseIntArray>) {
        val stringValue = Gson().toJson(value)
        doEdit()
        mEditor!!.putString(key, stringValue)
        doCommit()
    }

    fun getArrayMap(key: String): ArrayMap<String, SparseIntArray>? {
        val sparseArrayString = mPref.getString(key, null) ?: return null
        val sparseArrayType = object : TypeToken<ArrayMap<String, SparseIntArray>>() {}.type
        return Gson().fromJson(sparseArrayString, sparseArrayType)
    }

    @JvmOverloads
    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return try {
            java.lang.Double.valueOf(mPref.getString(key, defaultValue.toString()) ?: "")
        } catch (nfe: NumberFormatException) {
            defaultValue
        }

    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mPref.getBoolean(key, defaultValue)
    }

    fun getBoolean(key: String): Boolean {
        return mPref.getBoolean(key, false)
    }

    fun remove(vararg keys: String) {
        doEdit()
        for (key in keys) {
            mEditor!!.remove(key)
        }
        doCommit()
    }

    fun clear() {
        doEdit()
        mEditor!!.clear()
        doCommit()
    }

    @SuppressLint("CommitPrefEdits")
    fun edit() {
        mEditor = mPref.edit()
    }

    fun commit() {
        mEditor!!.commit()
        mEditor = null
    }

    @SuppressLint("CommitPrefEdits")
    private fun doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit()
        }
    }

    private fun doCommit() {
        if (mEditor != null) {
            mEditor!!.commit()
            mEditor = null
        }
    }

    object Keys {

        object User {
            const val USER_ID = "user_id"
            const val CITY_ID = "city_id"
            const val ADJUST_ID = "adjust_id"
            const val NOTIFICATION_TOKEN = "notification_token"
            const val LOCATION_PERMISSION = "location_permission"
            const val REFERRAL_LINK = "referral_link"
            const val REFERRAL_GIVER_AMOUNT = "referral_giver_amount"
            const val REFERRAL_RECEIVER_AMOUNT = "referral_receiver_amount"
            const val FIRST_NAME = "first_name"
            const val LAST_NAME = "last_name"
            const val PHONE_NUMBER = "phone_number"
            const val TOKEN = "token"
            const val BALANCE = "balance"
            const val EMAIL = "email"
        }

        object City {
            const val KEY_CITY_ID = "pref_city_id"
            const val KEY_CITY_CHANGED = "pref_city_changed"
            const val KEY_CITY_ABBREVIATION = "pref_city_abbreviation"
            const val KEY_NEXT_SLOT = "next_slot"
            const val KEY_CITY_LAT = "pref_city_lat"
            const val KEY_CITY_LONG = "pref_city_long"
        }

        object Address {
            const val KEY_SELECTED_ADDRESS = "selected_address"
            const val KEY_RECENTLY_SEARCHED_ADDRESSES = "recently_searched_addresses"
        }

        object PickerKeyConstants {
            const val KEY_PARTY_SIZE = "party_size"
            const val KEY_DATE = "date"
            const val KEY_TIME = "time"
        }

        object DDTSelections {
            const val KEY_DDT_USER_SELECTION = "ddt_user_seletion"
            const val KEY_DDT_FIREBASE_CONFIG_SELECTION = "ddt_firebase_config_selection"
        }
    }


    /**
     * Save a map in shared pref
     */
    fun saveMap(key: String, value: HashMap<String, String>) {
        val jsonObject = JSONObject(value as Map<*, *>)
        val jsonString = jsonObject.toString()
        val editor = mPref.edit()
        editor.remove(key).apply()
        editor.putString(key, jsonString)
        editor.commit()
    }

    /**
     * load a map that was saved in shared pref
     */
    fun loadMap(key: String): HashMap<String, String> {
        val outputMap = HashMap<String, String>()
        try {
            val jsonString = mPref.getString(key, JSONObject().toString())
            val jsonObject = JSONObject(jsonString!!)
            val keysItr = jsonObject.keys()
            while (keysItr.hasNext()) {
                val name = keysItr.next()
                val value = jsonObject.getString(name)
                outputMap[name] = value
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return outputMap
        }

        return outputMap
    }


    /**
     * Save ArrayList in SharedPreference
     */

    fun saveArrayList(key: String, list: ArrayList<String>) {
        val editor = mPref.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    /**
     * Get ArrayList in SharedPreference
     */
    fun getArrayList(key: String): ArrayList<String> {
        val gson = Gson()
        val json = mPref.getString(key, null)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        if (json.isNullOrEmpty()) {
            return ArrayList()
        } else {
            return gson.fromJson(json, type)
        }

    }

    fun createSession(accessToken: String?) {
        val preferenceEditor = mPref.edit()
        preferenceEditor.putString(Keys.User.TOKEN, accessToken)
        preferenceEditor.apply()
    }

    fun isSessionAvailable(): Boolean {
        return mPref.getString(Keys.User.TOKEN, Constants.EMPTY_STRING)!!.isNotEmpty()
    }

    fun getAccessToken(): String {
        return mPref.getString(
            Keys.User.TOKEN, Constants.EMPTY_STRING
        ).toString()
    }

    fun clearSession() {
        clear()
    }

    fun getBaseUrl():String {
        return "https://api.mocki.io/v1/"
    }
}
