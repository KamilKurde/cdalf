package com.github.KamilKurde

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.window.*
import kotlinx.coroutines.*

object Application {
	
	val windows = mutableStateListOf<Window>()
	
	// Turns true if main function ended executing
	private var canClose = false
	
	@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
	@OptIn(ExperimentalAnimationApi::class)
	operator fun invoke(main: suspend () -> Unit) {
		CoroutineScope(Job()).launch {
			main()
			canClose = true
		}
		
		CoroutineScope(Job()).launch {
			application(false) {
				windows.forEach { window ->
					val windowState = WindowState(
						width = window.width,
						height = window.height,
						isMinimized = window.isMinimized,
						placement = window.placement,
						position = window.position)
					Window(
						onCloseRequest = window.onCloseRequest ?: { window.close() },
						state = windowState,
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
							window.apply {
								height = windowState.size.height
								width = windowState.size.width
								placement = windowState.placement
								position = windowState.position
							}
							AnimatedContent(window.activityStack.last().content)
							{ content ->
								content()
							}
						}
					)
				}
			}
		}
		
		runBlocking {
			while (!canClose) {
				delay(100L)
			}
		}
	}
}