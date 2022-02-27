package com.github.KamilKurde

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.window.application

object Application {
	
	val windows = mutableStateListOf<Window>()
	
	internal fun exitApplication() = windows.toList().forEach { it.close() }
	
	operator fun invoke(exceptionHandler: (Exception) -> Unit = {}, main: () -> Unit) {
		try {
			main()
		} catch (e: Exception) {
			exceptionHandler(e)
		}
		application(false) {
			windows.forEach { window ->
				window.generate()
			}
		}
	}
}