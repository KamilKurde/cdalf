package com.github.KamilKurde

expect object Application: ApplicationContext {
	operator fun invoke(exceptionHandler: (Exception) -> Unit = {}, main: Application.() -> Unit)
	
	internal fun closeImpl()
}