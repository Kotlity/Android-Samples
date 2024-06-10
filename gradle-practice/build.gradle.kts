plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.kotlity.gradle_practice"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
}