package com.github.KamilKurde

import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.renderComposable

actual object Application : ApplicationContext() {
	
	actual operator fun invoke(exceptionHandler: (Exception) -> Unit, main: Application.() -> Unit) {
		try {
			this.main()
		} catch (e: Exception) {
			exceptionHandler(e)
		}
		renderComposable(root = document.body!!)
		{
			val activity = activityStack.lastOrNull()
			if (activity in activityStack) {
				activity?.content?.let {
					lastLayout = it
				}
			}
			
			Div({
				id("root")
				style {
					width(100.percent)
					height(100.percent)
				}
			})
			{
				lastLayout()
			}
		}
	}
	
	internal actual fun closeImpl() {
		window.close()
	}
}