plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.kotlity.services"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += listOf("paidMode", "mode")
    productFlavors {
        create("free") {
            dimension = "paidMode"
        }
        create("paid") {
            dimension = "paidMode"
        }
        create("development") {
            dimension = "mode"
            buildConfigField("String", "ACCESS_SERVER_URL_PARAM", "\"https://dev.api.com\"")
            manifestPlaceholders.replace("appLabel", "Development app")
        }
        create("production") { // will be freeProductionDebug build type in build types(by default)
            dimension = "mode"
            buildConfigField("String", "ACCESS_SERVER_URL_PARAM", "\"https://dev.api.com\"")
            manifestPlaceholders.replace("appLabel", "Production app")
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
        create("beta") {
            initWith(getByName("debug")) // copies configuration from debug build type, then changes manifestPlaceholders variable
            manifestPlaceholders["hostName"] = "internal.example.com"
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

    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}