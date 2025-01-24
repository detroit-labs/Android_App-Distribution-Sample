import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.detroitlabs.appdistributionsample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.detroitlabs.appdistributionsample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    /*
    There should be a "local.properties" file in the project root folder. It should be saved in
    1Password and not checked into version control.
    For this sample app, it should include the following lines:
    debug.api.key=debug_api_key_here
    production.api.key=production_api_key_here
    */

    // Load API keys from "local.properties":
    // ("gradleLocalProperties" is a built-in function of the Android Gradle plugin.)
    val properties = gradleLocalProperties(rootDir, providers)
    val debugApiKey = properties.getProperty("debug.api.key") ?: ""
    val productionApiKey = properties.getProperty("production.api.key") ?: ""

    buildTypes {
        // 1. Debug build pointing to the debug API:
        debug {
            // Unique suffixes allow installation of multiple variants on the same device.
            // (These may be decided by the team; what we present here is a suggestion.)
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            // Enable debug features:
            isDebuggable = true
            // The "buildConfigField" function adds constants to the "BuildConfig" class.
            // Note that the third argument has inner (escaped) double quotes. The inner quotes
            // correspond to the quotes that will appear around the string in the generated code for
            // the "BuildConfig" class.
            buildConfigField("String", "BUILD_LABEL", "\"Debug build pointing to debug\"")
            buildConfigField("String", "API_BASE_URL", "\"https://my-api-debug\"")
            buildConfigField("String", "API_KEY", "\"${debugApiKey}\"")
        }

        // 2. Debug build pointing to the production API:
        create("debugProd") {
            initWith(getByName("debug")) // Inherit the properties of the "debug" build variant defined above.
            applicationIdSuffix = ".debugprod"
            versionNameSuffix = "-debugprod"
            buildConfigField("String", "BUILD_LABEL", "\"Debug build pointing to prod\"")
            buildConfigField("String", "API_BASE_URL", "\"https://my-api\"")
            buildConfigField("String", "API_KEY", "\"${productionApiKey}\"")
        }

        // 3. QA build pointing to the debug API:
        create("qa") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            buildConfigField("String", "BUILD_LABEL", "\"QA build pointing to debug\"")
            buildConfigField("String", "API_BASE_URL", "\"https://my-api-debug\"")
            buildConfigField("String", "API_KEY", "\"${debugApiKey}\"")
        }

        // 4. QA build pointing to production API:
        create("qaProd") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".qaprod"
            versionNameSuffix = "-qaprod"
            buildConfigField("String", "BUILD_LABEL", "\"QA build pointing to prod\"")
            buildConfigField("String", "API_BASE_URL", "\"https://my-api\"")
            buildConfigField("String", "API_KEY", "\"${productionApiKey}\"")
        }

        // 5. Release build:
        release {
            // Enable R8 (the code optimizer and obfuscator):
            isMinifyEnabled = true
            // Remove unused resources and layouts:
            isShrinkResources = true
            // R8 became the default optimizer and obfuscator in 2019, replacing Proguard. It still
            // uses Proguard configuration files.
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BUILD_LABEL", "\"Release build\"")
            buildConfigField("String", "API_BASE_URL", "\"https://my-api\"")
            buildConfigField("String", "API_KEY", "\"${productionApiKey}\"")
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