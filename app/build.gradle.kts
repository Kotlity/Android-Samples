plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.kotlity.allpractice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kotlity.allpractice"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":different-screen-sizes"))
    implementation(project(":services"))
    implementation(project(":gradle-practice"))
    implementation(project(":workmanager"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.work.runtime.ktx)
}