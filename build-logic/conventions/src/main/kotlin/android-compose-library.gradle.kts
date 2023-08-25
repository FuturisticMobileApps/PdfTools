import com.android.build.gradle.LibraryExtension

plugins {
    id("com.android.library")
}

extensions.configure<LibraryExtension>{

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
}

dependencies {
    implementation(getLibrary("activity.compose"))
    implementation(platform(getLibrary("compose.bom")))
    implementation(getBundle("compose.libs"))
    implementation(getLibrary("navigation.compose"))
}
