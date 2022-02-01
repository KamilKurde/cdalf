import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.10"
	id("org.jetbrains.compose") version "1.0.1"
	`java-library`
	`maven-publish`
}

val groupString = "com.github.KamilKurde"
val versionString = "0.1"

group = groupString
version = versionString

java {
	withJavadocJar()
	withSourcesJar()
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = groupString
			artifactId = "cdalf"
			version = versionString
			
			from(components["java"])
		}
	}
}

repositories {
	mavenCentral()
	google()
}

dependencies {
	implementation(compose.desktop.currentOs)
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	//testImplementation("org.jetbrains.kotlin:kotlin-test")
	//testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	
	// Align versions of all Kotlin components
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	languageVersion = "1.6"
}