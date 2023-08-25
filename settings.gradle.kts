pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PdfTools"
include(":app")
include(":feature:tools")
include(":feature:files")
include(":feature:settings")

include(":core:itext")
include(":core:designsystem")
include(":core:ui")
include(":core:appUtil")
