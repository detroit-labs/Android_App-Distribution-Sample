import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.detroitlabs.appdistributionsample"
    compileSdk = 35

    defaultConfig { // Here we specify the base configuration for all flavors defined below.
        // The application ID uniquely identifies your app on the device and in the Google Play
        // Store.
        applicationId = "com.detroitlabs.appdistributionsample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("keystore.jks")
            storePassword = System.getenv("SIGNING_KEY_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_STORE_PASSWORD")
        }
    }

    /*
    There should be a "local.properties" file in the project root folder. It should be saved in
    1Password and not checked into version control.
    For this sample app, it should include the following lines:
    debug.api.key=debug_api_key_here
    production.api.key=production_api_key_here
    */

    // Load API keys from "local.properties".
    // ("gradleLocalProperties" is a built-in function of the Android Gradle plugin.)
    val properties = gradleLocalProperties(rootDir, providers)
    val debugApiKey = properties.getProperty("debug.api.key") ?: ""
    val productionApiKey = properties.getProperty("production.api.key") ?: ""

    buildTypes {
        debug {
            // Enable debug features.
            isDebuggable = true
            // Unique suffixes allow installation of multiple variants on the same device.
            // Note that we specify additional suffixes where we define flavors below - those
            // suffixes are applied first.
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }

        release {
            isDebuggable = false
            // Enable R8 (the code optimizer and obfuscator).
            isMinifyEnabled = true
            // Remove unused resources and layouts.
            isShrinkResources = true
            // R8 became the default optimizer and obfuscator in 2019, replacing Proguard. It still
            // uses Proguard configuration files.
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    // You must specify a flavor dimension. By default, the flavors defined below will belong to
    // this dimension.
    flavorDimensions += "environment"
    productFlavors {
        create("development") {
            // These suffixes are applied before the suffixes specified in the "buildTypes" block
            // above.
            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
            // The "buildConfigField" function adds constants to the "BuildConfig" class.
            // Note that the third argument has inner (escaped) double quotes. The inner quotes
            // correspond to the quotes that will appear around the string in the generated code for
            // the "BuildConfig" class.
            buildConfigField("String", "API_BASE_URL", "\"https://my-api-debug\"")
            buildConfigField("String", "API_KEY", "\"${debugApiKey}\"")
        }
        create("production") {
            buildConfigField("String", "API_BASE_URL", "\"https://my-api\"")
            buildConfigField("String", "API_KEY", "\"${productionApiKey}\"")
            firebaseAppDistribution {
                artifactType = "APK"
                appId = System.getenv("FIREBASE_APP_ID")
                serviceCredentialsFile = System.getenv("GOOGLE_SERVICES_JSON")
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
    buildFeatures {
        compose = true
        // Indicates that the "BuildConfig" class should be generated, including the custom fields
        // defined above with "buildConfigField":
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}