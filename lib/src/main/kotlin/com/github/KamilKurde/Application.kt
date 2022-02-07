package com.github.KamilKurde

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.window.application

object Application {
	
	val windows = mutableStateListOf<Window>()
	
	operator fun invoke(main: () -> Unit) {
		main()
		application(false) {
			windows.forEach { window ->
				window.generate()
			}
		}
	}
}