package com.github.KamilKurde

import androidx.compose.runtime.*

open class ApplicationContext internal constructor() {
	
	var title by mutableStateOf("CDALF application")
	var onCloseRequest by mutableStateOf<(() -> Unit)?>(null)
	
	internal val activityStack = ActivityStack()
	internal var lastLayout: @Composable () -> Unit = splashScreen
	
	fun close() {
		activityStack.reversed().forEach { it.finish() }
		onCloseRequest?.invoke()
		Application.closeImpl()
	}
	
	fun startActivity(intent: Intent) {
		val copiedIntent = intent.copy()
		val activity = copiedIntent.activity()
		activityStack.lastOrNull()?.let {
			it.pause()
			it.stop()
		}
		activity.apply {
			activityStack.push(this)
			this.intent = copiedIntent
			create()
			start()
			resume()
		}
	}
	
	fun back() {
		activityStack.pop()
		activityStack.lastOrNull()?.run {
			start()
			resume()
		}
	}
}