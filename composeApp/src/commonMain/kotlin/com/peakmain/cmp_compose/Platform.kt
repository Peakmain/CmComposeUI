package com.peakmain.cmp_compose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform