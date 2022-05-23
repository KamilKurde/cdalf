# Compose Desktop Android-like Framework

###### Simple framework/library designed to make desktop app development easier for Android developers

###### It is possible to use this both in compose desktop and compose for web, however web support is experimental and may not work at all.

## Installation

###### In build.gradle.kts

* apply following plugins:

```kotlin
plugins {
	// other plugins
	kotlin(PLATFORM) version "1.6.10" // replace platform with jvm or js
	id("org.jetbrains.compose") version "1.1.1"
	application // Only for desktop
}
 ```

* add jitpack to project repositories:

```kotlin
repositories {
	// other repositories
	maven { url = uri("https://jitpack.io") }
	jetbrainsCompose() // Only for web
}
```

* add these dependencies (replace "TAG" with the newest release, alternatively replace with "master-SNAPSHOT" to get the latest commit)

```kotlin
dependencies {
	// other dependencies
	implementation(compose.PLATFORM) // replace platform with desktop.currentOs for desktop and with "web.core" for web
	implementation("com.github.KamilKurde:cdalf:TAG")
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
	* Instantiate Intent class and pass your Activity class to it
	* Instantiate Window class and pass Intent to it
	* example:

```kotlin
val intent = Intent(YourActivity::class)
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