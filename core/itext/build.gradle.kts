plugins {
    id("android-kotlin-library")
    id("hilt-library")
}

android{
    namespace = "com.futuristicmobileapps.itext"
}

dependencies {
    implementation(libs.itxtG)
    implementation(libs.madgap.spongycastle)
}
