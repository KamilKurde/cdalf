package com.github.KamilKurde

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlin.system.exitProcess

actual object Application : ApplicationContext() {
	
	var icon: (@Composable () -> Painter?) = { null }
	var defaultTheme by mutableStateOf<Theme?>(null)
	var undecorated by mutableStateOf(false)
	var transparent by mutableStateOf(false)
	var resizable by mutableStateOf(true)
	var enabled by mutableStateOf(true)
	var focusable by mutableStateOf(true)
	var alwaysOnTop by mutableStateOf(false)
	var windowState by mutableStateOf(WindowState(WindowPlacement.Floating, false, WindowPosition.PlatformDefault, DpSize(800.dp, 600.dp)))
	var onPreviewKeyEvent by mutableStateOf<(KeyEvent) -> Boolean>({ false })
	var onKeyEvent by mutableStateOf<(KeyEvent) -> Boolean>({ false })
	
	@Suppress("OPT_IN_IS_NOT_ENABLED")
	@OptIn(ExperimentalAnimationApi::class)
	actual operator fun invoke(exceptionHandler: (Exception) -> Unit, main: Application.() -> Unit) {
		try {
			this.main()
		} catch (e: Exception) {
			exceptionHandler(e)
		}
		application(false) {
			Window(
				onCloseRequest = { onCloseRequest?.invoke() ?: close() },
				state = windowState,
				title = title,
				icon = icon(),
				undecorated = undecorated,
				transparent = transparent,
				resizable = resizable,
				enabled = enabled,
				focusable = focusable,
				alwaysOnTop = alwaysOnTop,
				onPreviewKeyEvent = onPreviewKeyEvent,
				onKeyEvent = onKeyEvent,
				content = {
					AnimatedContent(activityStack.lastOrNull(), modifier = Modifier.background(defaultTheme?.colors?.background ?: MaterialTheme.colors.background))
					{ activity ->
						if (activity in activityStack) {
							activity?.content?.let {
								lastLayout = it
							}
						}
						Crossfade(activity?.theme ?: defaultTheme)
						{
							MaterialTheme(
								it?.colors ?: MaterialTheme.colors,
								it?.typography ?: MaterialTheme.typography,
								it?.shapes ?: MaterialTheme.shapes
							)
							{
								Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
									lastLayout()
								}
							}
						}
					}
				}
			)
		}
	}
	
	internal actual fun closeImpl() {
		exitProcess(0)
	}
}