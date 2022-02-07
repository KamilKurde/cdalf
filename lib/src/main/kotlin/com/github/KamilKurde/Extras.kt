package com.github.KamilKurde

import kotlin.reflect.KClass

data class Extras(private val classToEntry: MutableMap<KClass<*>, MutableMap<String, Any?>> = mutableMapOf()) {
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
	
	fun <T> put(kclass: KClass<*>, key: String, value: T) {
		require(kclass.java.isPrimitive || kclass in wrappedClasses) { "value is required to be of primitive type, it is of ${kclass.qualifiedName} instead" }
		val current = classToEntry[kclass] ?: mutableMapOf()
		current[key] = value
		classToEntry[kclass] = current
	}
	
	inline fun <reified T> put(key: String, value: T) {
		put(T::class, key, value)
	}
	
	@Suppress("UNCHECKED_CAST")
	fun <T> get(kclass: KClass<*>, key: String): T? = classToEntry[kclass]?.get(key) as T?
	
	inline fun <reified T> get(key: String): T? = get(T::class, key)
	
	fun copy(): Extras = Extras(
		classToEntry.map { entry ->
			Pair(entry.key, entry.value.toMutableMap())
		}.toMap().toMutableMap()
	)
}