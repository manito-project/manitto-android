import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

val localProps = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

val keystoreProps = Properties().apply {
    load(rootProject.file("keystore.properties").inputStream())
}

android {
    namespace = "org.sopt.santamanitto"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.sopt.santamanitto"
        minSdk = 23
        targetSdk = 34
        versionCode = 17
        versionName = "2.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", localProps["baseUrl"] as String)
        buildConfigField("String", "TOS_URL", localProps["tosUrl"] as String)
        buildConfigField("String", "PRIVACY_POLICY_RUL", localProps["privacyPolicyUrl"] as String)
        buildConfigField("String", "INQUIRY_URL", localProps["inquiryUrl"] as String)

        sourceSets {
            val sharedTestDir = "src/sharedTest/java"
            getByName("test") {
                java.srcDir(sharedTestDir)
            }
            getByName("androidTest") {
                java.srcDir(sharedTestDir)
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProps["storeFile"] as String)
            storePassword = keystoreProps["storePassword"] as String
            keyAlias = keystoreProps["keyAlias"] as String
            keyPassword = keystoreProps["keyPassword"] as String
        }
    }

    buildTypes {
        debug {
            manifestPlaceholders["appName"] = "@string/dev_app_name"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_dev"
            buildConfigField(
                "String",
                "AMPLITUDE_KEY",
                localProps["amplitudeDebugKey"] as String
            )
        }

        release {
            manifestPlaceholders["appName"] = "@string/app_name"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            buildConfigField(
                "String",
                "AMPLITUDE_KEY",
                localProps["amplitudeProdKey"] as String
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += "default"
    productFlavors {
        create("mock") {
            dimension = "default"
            applicationIdSuffix = ".mock"
        }
        create("prod") {
            dimension = "default"
        }
    }

    variantFilter {
        if (buildType.name == "release" && flavors[0].name == "mock") {
            ignore = true
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

hilt {
    enableTransformForLocalTests = true
}


dependencies {
    implementation(libs.bundles.androidx)
    implementation(libs.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(platform(libs.retrofit.bom))
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.network)

    implementation(libs.bundles.kotlin)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.amplitude)
    implementation(libs.timber)
    implementation(libs.lottie)
}