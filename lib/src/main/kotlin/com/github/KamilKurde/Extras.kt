package com.github.KamilKurde

import kotlin.reflect.KClass

data class Extras(val classToEntry: MutableMap<KClass<*>, MutableMap<String, Any?>> = mutableMapOf()) {
	companion object {
		
		val wrappedClasses = setOf<KClass<*>>(
			Byte::class,
			Short::class,
			Int::class,
			Long::class,
			Float::class,
			Double::class,
			Boolean::class,
			Char::class,
			String::class
		)
	}
	
	inline fun <reified T> put(key: String, value: T) {
		require(T::class.java.isPrimitive || T::class in wrappedClasses) { "value is required to be of primitive type, it is of ${T::class.qualifiedName} instead" }
		val current = classToEntry[T::class] ?: mutableMapOf()
		current[key] = value
		classToEntry[T::class] = current
	}
	
	inline fun <reified T> get(key: String): T? {
		return classToEntry[T::class]?.get(key) as T?
	}
	
	fun copy(): Extras = Extras(
		classToEntry.map { entry ->
			Pair(entry.key, entry.value.toMutableMap())
		}.toMap().toMutableMap()
	)
}