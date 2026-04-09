package com.peakmain.cmp_compose.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.peakmain.cmp_compose.ui.button.LocalPkShapes
import com.peakmain.cmp_compose.ui.button.PkShapes

/**
 * author ：Peakmain
 * createTime：2025/4/10
 * mail:2726449200@qq.com
 * describe：
 */
object PkTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalPkColors.current

    val shapes: PkShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalPkShapes.current
}