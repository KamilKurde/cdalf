package com.github.KamilKurde

import androidx.compose.runtime.*

@Suppress("unused")
abstract class Activity: Themed() {
	
	internal var content: (@Composable () -> Unit)? by mutableStateOf(null)
	
	lateinit var intent: Intent
	
	fun startActivity(intent: Intent) = Application.startActivity(intent)
	
	// State enum with 'stage' representing activity lifecycle eg. for checking if activity did start you can check if state.stage >= 3
	enum class State(internal val stage: Int) {
		
		Restarting(0),
		Restarted(1),
		Creating(0),
		Created(1),
		Starting(2),
		Started(3),
		Resuming(4),
		Resumed(5),
		Pausing(6),
		Paused(7),
		Stopping(8),
		Stopped(9),
		Destroying(10),
		Destroyed(11)
	}
	
	var state by mutableStateOf(State.Creating)
	
	protected fun setContent(content: @Composable () -> Unit) {
		this.content = content
	}
	
	internal fun finishActivity() {
		if (state.stage < 6) {
			pause()
		}
		if (state.stage < 8) {
			stop()
		}
		if (state.stage < 10) {
			destroy()
		}
		Application.activityStack.remove(this)
	}
	
	fun finish() = Application.activityStack.remove(this)
	
	// Each lifecycle event has corresponding invocation method
	protected open fun onCreate() {}
	internal fun create() {
		state = State.Creating
		onCreate()
		state = State.Created
	}
	
	protected open fun onStart() {}
	internal fun start() {
		state = State.Starting
		onStart()
		state = State.Started
	}
	
	protected open fun onResume() {}
	internal fun resume() {
		state = State.Resuming
		onResume()
		state = State.Resumed
	}
	
	protected open fun onPause() {}
	internal fun pause() {
		state = State.Pausing
		onPause()
		state = State.Paused
	}
	
	protected open fun onStop() {}
	internal fun stop() {
		state = State.Stopping
		onStop()
		state = State.Stopped
	}
	
	protected open fun onDestroy() {}
	internal fun destroy() {
		state = State.Destroying
		onDestroy()
		state = State.Destroyed
	}
	
	protected open fun onBackPressed() {}
	internal fun back() {
		onBackPressed()
		finishActivity()
	}
}