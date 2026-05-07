package com.peakmain.cmp_compose.expect

import kotlin.js.Console

actual object PkLog {
    actual fun d(tag: String, message: String) {
        console.log("[$tag] $message")
    }

    actual fun e(tag: String, message: String) {
        console.error("[$tag] $message")
    }
}