# Compose Desktop Android-like Framework

###### Simple framework/library designed to make desktop app development easier for Android developers

## Installation

###### In build.gradle.kts

* apply following plugins:

```kotlin
plugins {
	// other plugins
	kotlin("jvm") version "1.6.10"
	id("org.jetbrains.compose") version "1.0.1"
	application
}
 ```

* add jitpack to project repositories:

```kotlin
repositories {
	// other repositories
	maven { url = uri("https://jitpack.io") }
}
```

* add these dependencies (replace "TAG" with the newest release, alternatively replace with "master-SNAPSHOT" to get the latest commit)

```kotlin
dependencies {
	// other dependencies
	implementation(compose.desktop.currentOs)
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
    * Implement copy method
    * Use setContent method to define UI
    * example:

```kotlin
class YourActivity: Activity()
{
	override fun copy() = YourActivity()
	
	override fun onCreate() {
		super.onCreate()
		setContent {
			Text("Your activity")
		}
	}
}
```

* Starting Activity
  * Instantiate Window class and pass Activity to it
  * example:
```kotlin
Window(YourActivity())
```

##### EXAMPLE OF SIMPLE APP

```kotlin
import androidx.compose.material.*
import com.github.KamilKurde.*

class YourActivity: Activity()
{
	override fun copy() = YourActivity()

	override fun onCreate() {
		super.onCreate()
		setContent {
			Text("Your activity")
		}
	}
}

fun main() = Application {
	Window(YourActivity())
}
```