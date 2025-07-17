plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    // Koin core only — no Android
    implementation(libs.koin.core)
    // MockK
    testImplementation(libs.mockk)
// Kotlin Coroutines Test
    testImplementation(libs.kotlinx.coroutines.test)
// JUnit 4
    testImplementation(libs.junit)

}
