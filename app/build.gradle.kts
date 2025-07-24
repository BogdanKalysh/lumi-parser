import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
}

val localProperties = Properties().apply {
    load(File(rootDir, "local.properties").inputStream())
}
val defaultBaseUrl = "DEFAULT"
val baseUrl = localProperties.getProperty("BASE_URL") ?: defaultBaseUrl

android {
    namespace = "com.challange.lumiparser"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.challange.lumiparser"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose.jvmstubs)
    implementation(libs.core.ktx)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.androidx.arch)
    testImplementation(libs.org.mockito.core)
    testImplementation(libs.org.mockito.inline)
    testImplementation(libs.org.mockito.kotlin)
    testImplementation(libs.io.mockk)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.arch)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.moshi.adapters)
    implementation(libs.squareup.retrofit.converter.moshi)
    implementation(libs.squareup.retrofit.converter.scalars)

    // Koin
    implementation(libs.io.insert.koin.android)
    testImplementation(libs.io.insert.koin.test)

    // Coil
    implementation(libs.io.coil.kt)
    testImplementation(kotlin("test"))
}