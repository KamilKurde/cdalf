package com.github.KamilKurde

@Suppress("unused")
data class Intent(val activity: () -> Activity, val extras: Extras = Extras()) {
	inline fun <reified T> putExtra(key: String, value: T): Intent {
		extras.put(key, value)
		return this
	}
	
	inline fun <reified T : Any> getExtra(key: String): T? = extras.get(key)
	
	fun copy(): Intent = Intent(activity, extras.copy())
}