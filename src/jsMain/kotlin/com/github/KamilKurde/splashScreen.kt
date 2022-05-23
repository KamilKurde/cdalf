package com.github.KamilKurde

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

internal actual val splashScreen: @Composable () -> Unit = {
	Div({
		style {
			margin("auto".unsafeCast<CSSNumeric>())
		}
	})
	{
		H2 {
			Text(Application.title)
		}
	}
}