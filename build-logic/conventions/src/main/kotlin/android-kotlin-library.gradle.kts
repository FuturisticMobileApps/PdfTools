import com.android.build.gradle.LibraryExtension

plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
}

extensions.configure<LibraryExtension>{

    compileSdk = 33

    defaultConfig{
        targetSdk = 33
        minSdk = 23
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
    implementation(getLibrary("core.ktx"))
    implementation(getLibrary("lifecycle.runtime.ktx"))
}
