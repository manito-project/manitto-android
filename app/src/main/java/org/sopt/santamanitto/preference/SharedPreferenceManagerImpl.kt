package org.sopt.santamanitto.preference

import android.content.SharedPreferences


class SharedPreferenceManagerImpl(private val sharedPreferences: SharedPreferences) : SharedPreferenceManager {
    companion object {
        const val SHARED_PREFERENCES_FILE_NAME = "manitto_preference"
    }

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun setString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return getString(key, null)
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    override fun setInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun clear(): Boolean {
        return editor.clear().commit()
    }
}