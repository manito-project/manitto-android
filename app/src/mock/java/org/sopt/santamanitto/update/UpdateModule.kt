package org.sopt.santamanitto.update

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.update.version.MockVersionChecker
import org.sopt.santamanitto.update.version.VersionChecker

@InstallIn(ApplicationComponent::class)
@Module
object UpdateModule {

    @Provides
    fun provideVersionChecker(): VersionChecker = MockVersionChecker()
}