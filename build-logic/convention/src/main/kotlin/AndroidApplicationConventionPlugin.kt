import com.android.build.api.dsl.ApplicationExtension
import com.insurtech.kanguro.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                apply(from = project.rootProject.file("androidCoreVersions.gradle"))
                val buildConfig = project.extra.get("buildConfig") as LinkedHashMap<*, *>

                configureKotlinAndroid(this)
                defaultConfig.targetSdk = buildConfig["targetSdk"] as Int
            }
        }
    }
}
