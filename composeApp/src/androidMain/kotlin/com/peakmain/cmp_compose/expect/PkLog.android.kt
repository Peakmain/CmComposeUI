package com.peakmain.cmp_compose.expect

actual object PkLog {
    actual fun d(tag: String, message: String) {
        android.util.Log.d(tag, message)
    }

    actual fun e(tag: String, message: String) {
        android.util.Log.e(tag, message)
    }
}