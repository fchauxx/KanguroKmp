import com.android.build.gradle.LibraryExtension
import com.insurtech.kanguro.configureBuildTypes
import com.insurtech.kanguro.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                apply(from = project.rootProject.file("androidCoreVersions.gradle"))
                val buildConfig = project.extra.get("buildConfig") as LinkedHashMap<*, *>

                configureKotlinAndroid(this)

                defaultConfig.targetSdk = buildConfig["targetSdk"] as Int

                configureBuildTypes(this)
            }
        }
    }
}
