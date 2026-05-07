package com.peakmain.cmp_compose.expect

actual object PkLog {
    actual fun d(tag: String, message: String) {
        println("D/$tag: $message")
    }

    actual fun e(tag: String, message: String) {
        println("E/$tag: $message")
    }
}