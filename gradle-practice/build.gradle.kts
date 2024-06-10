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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}

abstract class SimpleGradleTask: DefaultTask() {

    private val fileName = "myFile.txt"

    @get:Input
    abstract val fileTextInput: Property<String>

    @OutputFile
    val file = File(fileName)

    @TaskAction
    fun action() {
        file.bufferedWriter().use {
            it.write(fileTextInput.get())
        }
    }
}

tasks.apply {
    register<SimpleGradleTask>(name = "simpleGradleTask")
    named<SimpleGradleTask>("simpleGradleTask") {
        fileTextInput = "It's my first simple gradle task !"
    }
    register("copyApk", Copy::class) {
        val apkDirectory = layout.buildDirectory.dir("intermediates/apk/freeDevelopment/debug/app-free-development-debug.apk")
        val destDirectory = "$projectDir/apk"
        from(apkDirectory)
        into(destDirectory)
        rename("app-free-development-debug.apk", "final.apk")
        doLast {
            val file = File(destDirectory, "final.apk")
            ant.withGroovyBuilder {
                "checksum"("file" to file(file.path))
            }
        }
    }
}

tasks.whenTaskAdded { // execute after every task added to gradle
    if (this.name == "assembleDebug") { // check for the specific task in gradle
        this.finalizedBy("copyApk") // execute copyApk task after assembleDebug task is finished
    }
}