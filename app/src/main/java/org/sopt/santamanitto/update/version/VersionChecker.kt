package org.sopt.santamanitto.update.version

interface VersionChecker {

    interface GetLatestVersionCallback {
        fun onLoadLatestVersion(version: String)

        fun onFailure(networkError: Boolean)
    }

    fun getLatestVersion(callback: GetLatestVersionCallback)
}