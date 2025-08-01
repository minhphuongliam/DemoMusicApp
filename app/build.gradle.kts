plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //Kapt
    id("org.jetbrains.kotlin.kapt")
    //Google services
    id("com.google.gms.google-services")
    //Nav safe args
    id("androidx.navigation.safeargs.kotlin")
    //Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.demo.musicapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.demo.musicapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables{
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Lifecycle ViewModel + Livedata
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // KTX Activity, Fragment
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Firebase Authentication, Firestore (using BOM for management)
    // You must apply the platform first.
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics)

    // Retrofit2, Gson, OkHttp3
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // Coroutine (ViewModel, retrofit suspend )
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Jetpack Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Unit test
    testImplementation(libs.junit)

    //Encrypted SharedPref
    implementation(libs.androidx.security.crypto)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}