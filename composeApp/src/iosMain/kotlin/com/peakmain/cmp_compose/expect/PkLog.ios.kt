package com.peakmain.cmp_compose.expect

import platform.Foundation.NSLog

actual object PkLog {
    actual fun d(tag: String, message: String) {
        NSLog("$tag: $message")
    }

    actual fun e(tag: String, message: String) {
        NSLog("❌ $tag: $message")
    }
}