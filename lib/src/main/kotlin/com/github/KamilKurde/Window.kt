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
import com.github.KamilKurde.exceptions.IllegalWindowException

@Suppress("unused")
class Window(
	startingIntent: Intent,
	title: String = "Untitled",
	icon: (@Composable () -> Painter?) = { null },
	splashScreen: (@Composable () -> Unit) = { splashScreen(icon, title) },
	defaultTheme: Theme? = null,
	undecorated: Boolean = false,
	transparent: Boolean = false,
	resizable: Boolean = true,
	enabled: Boolean = true,
	focusable: Boolean = true,
	alwaysOnTop: Boolean = false,
	windowState: WindowState = WindowState(WindowPlacement.Floating, false, WindowPosition.PlatformDefault, DpSize(800.dp, 600.dp)),
	onCloseRequest: (Window.() -> Unit)? = null,
	onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
	onKeyEvent: (KeyEvent) -> Boolean = { false },
) {
	
	
	var title by mutableStateOf(title)
	var icon by mutableStateOf(icon)
	var defaultTheme by mutableStateOf(defaultTheme)
	var undecorated by mutableStateOf(undecorated)
	var transparent by mutableStateOf(transparent)
	var resizable by mutableStateOf(resizable)
	var enabled by mutableStateOf(enabled)
	var focusable by mutableStateOf(focusable)
	var alwaysOnTop by mutableStateOf(alwaysOnTop)
	var windowState by mutableStateOf(windowState)
	var onCloseRequest by mutableStateOf(onCloseRequest)
	var onPreviewKeyEvent by mutableStateOf(onPreviewKeyEvent)
	var onKeyEvent by mutableStateOf(onKeyEvent)
	val activityStack = ActivityStack(this)
	val isClosed get() = this !in Application.windows
	
	internal var lastLayout: @Composable () -> Unit = splashScreen
	
	init {
		Application.windows.add(this)
		startActivity(startingIntent)
	}
	
	@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
	@OptIn(ExperimentalAnimationApi::class)
	@Composable
	internal fun generate() {
		Window(
			onCloseRequest = { onCloseRequest?.invoke(this) ?: close() },
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
	
	fun close() {
		Application.windows.remove(this)
		activityStack.reversed().forEach {
			it.finish()
		}
	}
	
	internal fun startActivity(intent: Intent) {
		if (isClosed) {
			throw IllegalWindowException(intent, this)
		}
		@Suppress("NAME_SHADOWING") val intent = intent.copy()
		
		val activity = intent.activity
		val primaryConstructor = intent.activity.constructors.first()
		require(primaryConstructor.parameters.isEmpty()) { "Activity ${activity.simpleName} doesn't have no argument constructor" }
		
		activityStack.lastOrNull()?.let {
			it.pause()
			it.stop()
		}
		primaryConstructor.call().apply {
			activityStack.push(this)
			parent = this@Window
			this.intent = intent
			create()
			start()
			resume()
		}
	}
	
	fun back() {
		activityStack.pop()
		activityStack.lastOrNull()?.apply {
			restart()
			start()
			resume()
		}
	}
}