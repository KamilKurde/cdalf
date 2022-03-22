package com.github.KamilKurde

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun splashScreen(icon: (@Composable () -> Painter?), label: String) {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		if (icon() != null) {
			Image(icon()!!, label)
		} else {
			Text(label, color = MaterialTheme.colors.onSurface)
		}
	}
}