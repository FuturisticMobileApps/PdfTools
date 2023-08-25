
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.use.PluginDependency

private val Project.libs
    get() = the<VersionCatalogsExtension>().named("libs")

fun Project.getLibrary(library: String) = libs.findLibrary(library).get()

fun Project.getBundle(library: String) = libs.findBundle(library).get()

fun DependencyHandlerScope.plugin(plugin: org.gradle.api.provider.Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
