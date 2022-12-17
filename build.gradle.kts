plugins {
	kotlin("multiplatform") version "1.7.20"
	id("org.jetbrains.compose") version "1.2.2"
	`java-library`
	`maven-publish`
}

val groupString = "com.github.KamilKurde"
val versionString = "0.4.0"
val artifactString = "cdalf"

group = groupString
version = versionString

java {
	withSourcesJar()
	withJavadocJar()
}

publishing.publications.forEach { it ->
	if (it is MavenPublication) {
		it.pom {
			description.set("Compose Desktop Android-like Framework")
			url.set("https://github.com/KamilKurde/$artifactString")
			scm {
				url.set("https://github.com/KamilKurde/$artifactString")
			}
			issueManagement {
				url.set("https://github.com/KamilKurde/$artifactString/issues")
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

repositories {
	google()
	mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
	jvm {
		compilations.all {
			kotlinOptions.jvmTarget = "1.8"
		}
		withJava()
	}
	js(IR) {
		browser {
		
		}
	}
	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
				api(compose.runtime)
			}
		}
		val jvmMain by getting {
			dependencies {
				api(compose.desktop.common)
				api(compose.material)
			}
		}
		val jsMain by getting {
			dependencies {
				api(compose.web.core)
			}
		}
	}
}