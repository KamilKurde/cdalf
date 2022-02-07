package com.github.KamilKurde

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

object Application {
	
	val windows = mutableStateListOf<Window>()
	
	@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
	@OptIn(ExperimentalAnimationApi::class)
	operator fun invoke(main: suspend CoroutineScope.() -> Unit) {
		runBlocking {
			main()
		}
		application(false) {
			windows.forEach { window ->
				Window(
					onCloseRequest = window.onCloseRequest ?: { window.close() },
					state = window.windowState,
					title = window.title,
					icon = window.icon,
					undecorated = window.undecorated,
					transparent = window.transparent,
					resizable = window.resizable,
					enabled = window.enabled,
					focusable = window.focusable,
					alwaysOnTop = window.alwaysOnTop,
					onPreviewKeyEvent = window.onPreviewKeyEvent,
					onKeyEvent = window.onKeyEvent,
					content = {
						val lastLayout by remember { mutableStateOf(window.activityStack.lastOrNull()?.content ?: {}) }
						AnimatedContent(window.activityStack.lastOrNull())
						{ activity ->
							(activity?.content ?: lastLayout)()
						}
					}
				)
			}
		}
	}
}