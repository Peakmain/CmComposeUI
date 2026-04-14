import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    id("maven-publish")
}
kotlin {

    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    configure(listOf(iosArm64(), iosSimulatorArm64(), iosX64())) {
        binaries.framework {
            baseName = "composeApp" // 必须和依赖名匹配
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            api(libs.compose.uiToolingPreview)
            api(libs.androidx.activity.compose)
            api("io.coil-kt.coil3:coil-network-okhttp:+")
        }
        commonMain.dependencies {
            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.ui)
            api(libs.compose.components.resources)
            api(libs.compose.uiToolingPreview)
            api(libs.androidx.lifecycle.viewmodelCompose)
            api(libs.androidx.lifecycle.runtimeCompose)
            // Coil 核心 + Compose
            api("io.coil-kt.coil3:coil-compose:+")

            // SVG 支持（你代码里用到了，必须加）
            api("io.coil-kt.coil3:coil-svg:+")

            // Ktor 核心
            api("io.ktor:ktor-client-core:+")
            // 可选：JSON 序列化支持
            api("io.ktor:ktor-client-content-negotiation:+")
            api("io.ktor:ktor-serialization-kotlinx-json:+")
            // 可选：日志
            api("io.ktor:ktor-client-logging:+")

        }
        androidMain.dependencies {
            api(libs.compose.uiToolingPreview)
            api(libs.androidx.activity.compose)
            api("io.coil-kt.coil3:coil-network-okhttp:+")
            // Ktor OkHttp 引擎
            api("io.ktor:ktor-client-okhttp:+")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:+")
        }
       /* commonTest.dependencies {
            implementation(libs.kotlin.test)
        }*/

    }
}

android {
    namespace = "com.peakmain.cmp_compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
       /* applicationId = "com.peakmain.cmcomposeui"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"*/
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
