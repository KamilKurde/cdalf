package com.github.KamilKurde

import androidx.compose.runtime.*
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
	icon: Painter? = null,
	undecorated: Boolean = false,
	transparent: Boolean = false,
	resizable: Boolean = true,
	enabled: Boolean = true,
	focusable: Boolean = true,
	alwaysOnTop: Boolean = false,
	windowState: WindowState = WindowState(WindowPlacement.Floating, false, WindowPosition.PlatformDefault, DpSize(800.dp, 600.dp)),
	onCloseRequest: (() -> Unit)? = null,
	onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
	onKeyEvent: (KeyEvent) -> Boolean = { false },
) {
	
	
	var title by mutableStateOf(title)
	var icon by mutableStateOf(icon)
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
	var activityStack = ActivityStack(this)
	var isClosed = false
	
	init {
		Application.windows.add(this)
		startActivity(startingIntent)
	}
	
	fun close() {
		isClosed = true
		activityStack.reversed().forEach {
			it.finish()
		}
		activityStack.clear()
		Application.windows.remove(this)
	}
	
	internal fun startActivity(intent: Intent) {
		if (isClosed) {
			throw IllegalWindowException(intent, this)
		}
		
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
			this.intent = intent.copy()
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