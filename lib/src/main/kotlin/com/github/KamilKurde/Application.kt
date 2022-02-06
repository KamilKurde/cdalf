package com.github.KamilKurde

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.window.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

object Application {
	
	val windows = mutableStateListOf<Window>()
	
	// Turns true if main function ended executing
	private var canClose = false
	
	@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
	@OptIn(ExperimentalAnimationApi::class)
	operator fun invoke(main: suspend CoroutineScope.() -> Unit) = application(false) {
		if (!canClose) {
			runBlocking {
				main()
				canClose = true
			}
		}
		val partitioned = windows.partition { it.activityStack.isNotEmpty() }
		partitioned.first.forEach { window ->
			val windowState = WindowState(
				width = window.size.width,
				height = window.size.height,
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
						size = windowState.size
						placement = windowState.placement
						position = windowState.position
					}
					AnimatedContent(window.activityStack.last())
					{ activity ->
						activity.content()
					}
				}
			)
		}
		partitioned.second.forEach {
			it.close()
		}
	}
}