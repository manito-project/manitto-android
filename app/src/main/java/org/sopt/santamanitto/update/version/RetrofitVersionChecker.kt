package org.sopt.santamanitto.update.version

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.update.store.StoreMetadataService
import retrofit2.await


class RetrofitVersionChecker(
    private val storeMetadataService: StoreMetadataService
) : VersionChecker {

    override suspend fun getLatestVersion(): Version {
        val response = withContext(Dispatchers.IO) {
            storeMetadataService.getStoreInfo(
                applicationId = BuildConfig.APPLICATION_ID,
                platform = "Android",
                what = "\$version"
            ).await()
        }
        return Version.create(response.message)
    }
}