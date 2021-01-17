package org.sopt.santamanitto.preference

interface SharedPreferenceManager {
    fun setString(key: String, value: String)

    fun getString(key: String): String?

    fun getString(key: String, defaultValue: String?): String?

    fun setInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Int

    fun setBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun clear(): Boolean
}