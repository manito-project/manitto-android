package org.sopt.santamanitto.update.version

class MockVersionChecker : VersionChecker {

    override suspend fun getLatestVersion(): Version {
        return Version.create("1.1.1")
    }
}
