package com.github.KamilKurde

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

internal actual val splashScreen: @Composable () -> Unit = {
	val icon = Application.icon
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		if (icon() != null) {
			Image(icon()!!, Application.title)
		} else {
			Text(Application.title, color = MaterialTheme.colors.onSurface)
		}
	}
}