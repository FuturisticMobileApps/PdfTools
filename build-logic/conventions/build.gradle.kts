plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(plugin(libs.plugins.androidApplication))
    implementation(plugin(libs.plugins.kotlinAndroid))
    implementation(plugin(libs.plugins.kotlinJvm))
    implementation(plugin(libs.plugins.dagger.hilt))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

