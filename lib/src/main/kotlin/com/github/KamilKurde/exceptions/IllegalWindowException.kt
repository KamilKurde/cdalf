package com.github.KamilKurde.exceptions

import com.github.KamilKurde.Intent
import com.github.KamilKurde.Window

internal class IllegalWindowException(intent: Intent, window: Window) : RuntimeException("Tried launching Intent ($intent) on closed Window ($window)")