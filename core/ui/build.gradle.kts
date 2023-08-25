plugins {
    id("android-kotlin-library")
    id("android-compose-library")
}

android {
    namespace = "com.futuristicmobileapps.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
}
