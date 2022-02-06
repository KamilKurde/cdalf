package com.github.KamilKurde

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
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
	placement: WindowPlacement = WindowPlacement.Floating,
	isMinimized: Boolean = false,
	position: WindowPosition = WindowPosition.PlatformDefault,
	size: DpSize = DpSize(800.dp, 600.dp),
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
	var placement by mutableStateOf(placement)
	var isMinimized by mutableStateOf(isMinimized)
	var position by mutableStateOf(position)
	var size by mutableStateOf(size)
	var onCloseRequest by mutableStateOf(onCloseRequest)
	var onPreviewKeyEvent by mutableStateOf(onPreviewKeyEvent)
	var onKeyEvent by mutableStateOf(onKeyEvent)
	var activityStack = mutableStateListOf<Activity>()
	private var windowClosed = false
	
	init {
		Application.windows.add(this)
		startActivity(startingIntent)
	}
	
	fun close() {
		activityStack.reversed().forEach {
			it.finish()
		}
		activityStack.clear()
		Application.windows.remove(this)
		windowClosed = true
	}
	
	internal fun startActivity(intent: Intent) {
		if (windowClosed) {
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
			activityStack.add(this)
			parent = this@Window
			this.intent = intent.copy()
			create()
			start()
			resume()
		}
	}
	
	fun back() {
		activityStack.lastOrNull()?.apply {
			back()
			finish()
		}
		if (activityStack.isEmpty()) {
			close()
		} else {
			activityStack.last().apply {
				restart()
				start()
				resume()
			}
		}
	}
}