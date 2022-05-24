plugins {
	kotlin("multiplatform") version "1.6.10"
	id("org.jetbrains.compose") version "1.1.1"
	`java-library`
	`maven-publish`
}

val groupString = "com.github.KamilKurde"
val versionString = "0.3.1"
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
				api(compose.runtime)
				implementation("org.jetbrains.kotlin:kotlin-stdlib")
				// Reflection API required for activity instantiation
				api(kotlin("reflect"))
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