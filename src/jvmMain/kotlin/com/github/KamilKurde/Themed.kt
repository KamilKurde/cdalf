package com.github.KamilKurde

import androidx.compose.runtime.*

actual open class Themed {
	internal var theme: Theme? by mutableStateOf(null)
	
	protected fun setTheme(theme: Theme?) {
		this.theme = theme
	}
}