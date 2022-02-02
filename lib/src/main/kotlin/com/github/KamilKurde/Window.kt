package com.github.KamilKurde

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition

class Window(
	startingActivity: Activity,
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
	width: Dp = 800.dp,
	height: Dp = 600.dp,
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
	var width by mutableStateOf(width)
	var height by mutableStateOf(height)
	var onCloseRequest by mutableStateOf(onCloseRequest)
	var onPreviewKeyEvent by mutableStateOf(onPreviewKeyEvent)
	var onKeyEvent by mutableStateOf(onKeyEvent)
	var activityStack = mutableStateListOf<Activity>()
	
	init {
		Application.windows.add(this)
		startActivity(startingActivity)
	}
	
	fun close() {
		activityStack.reversed().forEach {
			it.finish()
		}
		activityStack.clear()
		Application.windows.remove(this)
	}
	
	internal fun startActivity(activity: Activity) {
		activityStack.lastOrNull()?.let {
			it.pause()
			it.stop()
		}
		activity.apply {
			activityStack.add(this)
			parent = this@Window
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
		when {
			activityStack.isEmpty() -> close()
			else -> activityStack.last().start()
		}
	}
}