import gradle.kotlin.dsl.accessors._68587c2e3c60d3efe02327f9ba7b6c8e.implementation

plugins{
    id("android-kotlin-library")
    id("android-compose-library")
    id("hilt-library")
}
dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:appUtil"))
    implementation(project(":core:itext"))
    implementation(getLibrary("lifecycle.viewmodel"))
}
