import gradle.kotlin.dsl.accessors._68587c2e3c60d3efe02327f9ba7b6c8e.implementation

plugins {
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}
dependencies {
    implementation(getLibrary("hilt.android"))
    implementation(getLibrary("hilt.navigation.compose"))
    "kapt"(getLibrary("hilt.android.compiler"))
}

kapt {
    correctErrorTypes = true
}


