package org.sopt.santamanitto.update.version

interface VersionChecker {
    suspend fun getLatestVersion(): Version
}