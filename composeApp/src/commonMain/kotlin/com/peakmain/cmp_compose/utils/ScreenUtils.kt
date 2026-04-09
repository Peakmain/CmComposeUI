package com.peakmain.cmp_compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author ：Peakmain
 * createTime：2026/4/7
 * mail:2726449200@qq.com
 * describe：
 */
@Composable
fun getScreenHeight(): Dp {
    val windowInfo = LocalWindowInfo.current
    return windowInfo.containerSize.height.dp
}

@Composable
fun getScreenHeightPx(): Float {
    val windowInfo = LocalWindowInfo.current
    return with(LocalDensity.current) {
        windowInfo.containerSize.height.dp.toPx()
    }
}

@Composable
fun getScreenWidth(): Dp {
    val windowInfo = LocalWindowInfo.current
    return windowInfo.containerSize.width.dp
}

@Composable
fun getScreenWidthPx(): Float {
    val windowInfo = LocalWindowInfo.current
    return with(LocalDensity.current) {
        windowInfo.containerSize.width.dp.toPx()
    }
}

