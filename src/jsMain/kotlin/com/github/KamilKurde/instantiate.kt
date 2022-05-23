package com.github.KamilKurde

import kotlin.reflect.KClass

actual fun KClass<out Activity>.instantiate() = eval("new ${js.name}()") as Activity