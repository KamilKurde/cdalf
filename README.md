# Compose Desktop Android-like Framework

###### Simple framework/library designed to make desktop app development easier for Android developers

###### It is possible to use this both in compose desktop and compose for web, however web support is experimental and may not work at all.

## Installation

###### In build.gradle.kts

* apply following plugins:

```kotlin
plugins {
	// other plugins
	kotlin(PLATFORM) version "1.7.20" // replace platform with jvm or js
	id("org.jetbrains.compose") version "1.2.2"
	application // Only for desktop
}
 ```

* add compose dev repo to plugin repositories in settings.gradle.kts
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
```

* add jitpack and compose to project repositories:

```kotlin
repositories {
	// other repositories
	maven { url = uri("https://jitpack.io") }
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}
```

* add these dependencies (replace "TAG" with the newest release, alternatively replace with "master-SNAPSHOT" to get the latest commit)

```kotlin
dependencies {
	// other dependencies
	implementation(compose.PLATFORM) // replace platform with desktop.currentOs for desktop and with "web.core" for web
	implementation("com.github.KamilKurde.cdalf:cdalf-PLATFORM:TAG") // replace platform with jvm for desktop, and with js for web
}
```

## Usage

* Starting an app
	* Use invoke method on Application object provided with this framework as a result for main function
	* example:

```kotlin
fun main() = Application {
	// Put yours program code here
}
```

* Creating Activity
	* Create class that inherits from Activity
	* Use setContent method to define UI
	* example:

```kotlin
class YourActivity : Activity() {
	override fun onCreate() {
		super.onCreate()
		setContent {
			Text("Your activity")
		}
	}
}
```

* Starting Activity
	* Instantiate Intent class and pass no-argument factory/constructor of your Activity class to it
	* Pass Intent to startActivity method
	* example:

```kotlin
val intent = Intent(::YourActivity)
startActivity(intent)
```

##### EXAMPLE OF SIMPLE APP

```kotlin
import androidx.compose.material.Text
import com.github.KamilKurde.*

class YourActivity : Activity() {
	override fun onCreate() {
		super.onCreate()
		setContent {
			Text("Your activity")
		}
	}
}

fun main() = Application {
	val intent = Intent(YourActivity::class)
	startActivity(intent)
}
```