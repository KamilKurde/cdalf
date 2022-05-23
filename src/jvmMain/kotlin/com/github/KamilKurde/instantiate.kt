package com.github.KamilKurde

import kotlin.reflect.KClass

actual fun KClass<out Activity>.instantiate(): Activity {
	val constructor = constructors.first()
	require(constructor.parameters.isEmpty()) { "Activity $simpleName doesn't have no argument constructor" }
	return constructor.call()
}