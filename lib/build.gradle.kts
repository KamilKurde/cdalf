import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.10"
	id("org.jetbrains.compose") version "1.0.1"
	`java-library`
	`maven-publish`
}

val groupString = "com.github.KamilKurde"
val versionString = "0.2.0"
val artifactString = "cdalf"

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
			artifactId = artifactString
			version = versionString
			
			from(components["java"])
			
			pom {
				packaging = "jar"
				name.set(artifactString)
				description.set("Compose Desktop Android-like Framework")
				url.set("https://github.com/KamilKurde/cdalf")
				scm {
					url.set("https://github.com/KamilKurde/cdalf")
				}
				issueManagement {
					url.set("https://github.com/KamilKurde/cdalf/issues")
				}
				developers {
					developer {
						id.set("KamilKurde")
						name.set("Kamil Bąk")
						url.set("https://github.com/KamilKurde")
					}
				}
			}
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
	
	// Reflection API required for activity instantiation
	api(kotlin("reflect"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	languageVersion = "1.6"
}