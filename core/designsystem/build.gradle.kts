plugins {
    id("android-kotlin-library")
    id("android-compose-library")
}
android {
    namespace = "com.futuristicmobileapps.designsystem"
}

dependencies {
    api(libs.coil.compose)
}
