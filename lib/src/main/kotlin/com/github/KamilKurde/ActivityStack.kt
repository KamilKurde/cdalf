package com.github.KamilKurde

import androidx.compose.runtime.mutableStateListOf

class ActivityStack(private val parent: Window) : Collection<Activity> {
	
	private var stack = mutableStateListOf<Activity>()
	override val size: Int
		get() = stack.size
	
	override fun contains(element: Activity): Boolean = stack.contains(element)
	
	override fun containsAll(elements: Collection<Activity>): Boolean = stack.containsAll(elements)
	
	override fun isEmpty(): Boolean = stack.isEmpty()
	
	override fun iterator(): Iterator<Activity> = stack.iterator()
	
	fun clear() = stack.clear()
	
	fun push(activity: Activity) {
		stack.add(activity)
	}
	
	private fun checkStackSize() {
		if (stack.isEmpty()) {
			parent.close()
		}
	}
	
	fun pop() = stack.removeLast().apply {
		back()
		finishActivity()
	}.also {
		checkStackSize()
		stack.lastOrNull()?.apply {
			resume()
			start()
		}
	}
	
	fun remove(activity: Activity) {
		if (activity == stack.lastOrNull()) {
			pop()
		} else {
			stack.remove(activity)
			checkStackSize()
		}
	}
}