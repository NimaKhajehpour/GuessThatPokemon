import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
    id ("com.apollographql.apollo3") version "3.7.3"
}

android {
    namespace = "com.nima.guessthatpokemon"
    compileSdk = 35

    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }

    defaultConfig {
        applicationId = "com.nima.guessthatpokemon"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    apollo {
        service("service") {
            packageName.set("com.nima.guessthatpokemon")
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles( getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //datastore
    implementation (libs.datastore)

    //Coroutines
    implementation (libs.coroutine.core)
    implementation (libs.coroutine.android)

    //apollo graphql
    implementation (libs.apolo.runtime)

    //ViewModel
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.runtime)

    //navigation
    implementation(libs.navigation)

    //Coil
    implementation(libs.coil)

    //Room database
    implementation (libs.room.runtime)
    ksp(libs.room.compiler)
    implementation (libs.room.ktx)

    //koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)

    implementation (libs.core.ktx)
    implementation (libs.activity.compose)
    implementation (libs.compose.ui)
    implementation (libs.compose.ui.tooling.preview)
    implementation (libs.compose.material3)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.espresso.core)
    androidTestImplementation (libs.ui.test.junit4)
    debugImplementation (libs.ui.tooling)
    debugImplementation (libs.ui.test.manifest)
}