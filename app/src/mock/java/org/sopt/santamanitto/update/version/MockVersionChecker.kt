package org.sopt.santamanitto.update.version

class MockVersionChecker : VersionChecker {
    override fun getLatestVersion(callback: VersionChecker.GetLatestVersionCallback) {
        callback.onLoadLatestVersion("1.1.1")
    }
}
