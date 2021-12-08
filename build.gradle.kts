import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktlint by configurations.creating

plugins {
    kotlin("jvm") version "1.4.32"
}

group = "com.rahulografy.dsalgokotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test-junit"))

    // JUnit 5 / Jupiter
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")

    // Google Guava lib
    testImplementation("com.google.guava:guava:22.0")

    // Google Truth test framework
    // https://mvnrepository.com/artifact/com.google.truth/truth
    testImplementation("com.google.truth:truth:1.0")

    // Test mocking framework
    testImplementation("org.mockito:mockito-core:3.11.1")

    ktlint("com.pinterest:ktlint:0.41.0")
    // ktlint(project(":custom-ktlint-ruleset")) // in case of custom ruleset
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
