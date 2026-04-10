import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("maven-publish")
}
kotlin {

    androidTarget {
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
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation("io.coil-kt.coil3:coil-network-okhttp:+")
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // Coil 核心 + Compose
            implementation("io.coil-kt.coil3:coil-compose:+")

            // SVG 支持（你代码里用到了，必须加）
            implementation("io.coil-kt.coil3:coil-svg:+")


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

// 让 Kotlin Multiplatform 发布所有模块到 Maven
publishing {
    repositories {
        maven {
            url = uri("${rootProject.buildDir}/local-repo")
        }
    }
}