import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<BaseExtension> {
            //    buildFeatures.compose = true

                composeOptions {
               //     kotlinCompilerExtensionVersion = "1.5.14"
                }
            }

            dependencies {
              /*  val composeBom = libs.findLibrary("compose.bom").get()
                add("implementation", platform(composeBom))
                add("androidTestImplementation", platform(composeBom))

                "implementation"(libs.findLibrary("compose.foundation").get())
                "implementation"(libs.findLibrary("compose.foundation.layout").get())
                "implementation"(libs.findLibrary("compose.material").get())
                "implementation"(libs.findLibrary("compose.material3").get())
                "implementation"(libs.findLibrary("compose.runtime").get())
                "implementation"(libs.findLibrary("compose.runtime.livedata").get())
                "implementation"(libs.findLibrary("compose.ui").get())
                "implementation"(libs.findLibrary("compose.ui.tooling").get())
                "implementation"(libs.findLibrary("accompanist.themeadapter.material").get())
                "implementation"(libs.findLibrary("compose.constraint.layout").get())
                "implementation"(libs.findLibrary("compose.view.model").get())
                "implementation"(libs.findLibrary("compose.activity").get())
                "implementation"(libs.findLibrary("coil.compose").get())
                "implementation"(libs.findLibrary("compose.flow.layout").get())*/
            }
        }
    }
}
