package com.peakmain.cmp_compose.ui.title

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import cmcomposeui.composeapp.generated.resources.Res
import cmcomposeui.composeapp.generated.resources.compose_icon_retrun
import com.peakmain.cmp_compose.basic.BasicFont
import com.peakmain.cmp_compose.basic.BasicSize
import com.peakmain.cmp_compose.basic.BasicSpace
import com.peakmain.cmp_compose.ui.image.PkImageView
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * author ：Peakmain
 * createTime：2025/6/26
 * mail:2726449200@qq.com
 * describe：自定义顶部标题栏
 */

/**
 * 顶部导航栏组件，支持自定义标题、返回按钮、右侧按钮、沉浸式状态栏等功能。
 *
 * @param title 标题，必填，导航栏标题文本
 * @param backgroundColor 导航栏背景色，默认 #FFFFFEFA
 * @param horizontalContentPadding 水平方向内边距，默认 BasicSpace.space_18
 * @param verticalContentPadding 垂直方向内边距，默认 BasicSpace.space_10
 * @param backResource 返回图标资源 ID，默认 R.drawable.compose_icon_retrun
 * @param backResourceSize 返回图标尺寸，默认 BasicSize.size_24
 * @param showBack 是否显示返回按钮，默认 true
 * @param onBackClick 返回按钮点击事件，默认 null（null 时点击返回关闭当前 Activity）
 * @param titleFontSize 标题文字大小，默认 BasicFont.font_18
 * @param titleColor 标题文字颜色，默认 #1F401B
 * @param isImmersive 是否沉浸式状态栏，默认 true
 * @param darkIcons 状态栏图标是否使用深色，默认 true
 * @param rightResource 右侧图标资源 ID，默认为 null 表示不显示
 * @param onRightClick 右侧图标点击事件，默认 null（null 时不显示或不响应）
 */
@Composable
fun PkNavBar(
    title: String,
    backgroundColor: Color = Color(0xFFFFFEFA),
    horizontalContentPadding: Dp = BasicSpace.space_18,
    backResource: DrawableResource = Res.drawable.compose_icon_retrun,
    backResourceSize: Dp = BasicSize.size_24,
    showBack: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    titleFontSize: TextUnit = BasicFont.font_18,
    titleColor: Color = Color(0xFF1F401B),
    isImmersive: Boolean = true,
    darkIcons: Boolean = true,
    rightResource: DrawableResource? = null,
    onRightClick: (() -> Unit)? = null
) {

    if (isImmersive) {
        SideEffect {

        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .statusBarsPadding()
            .height(44.dp)
            .padding(horizontal = horizontalContentPadding),
        contentAlignment = Alignment.Center
    ) {
        if (showBack) {
            PkImageView(
                painter = painterResource(backResource),
                modifier = Modifier
                    .size(backResourceSize)
                    .align(Alignment.CenterStart)
                    .clickable {
                        onBackClick?.invoke()
                    })
        }

        Text(
            text = title,
            fontSize = titleFontSize,
            fontWeight = FontWeight.W500,
            color = titleColor,
            modifier = Modifier
                .align(Alignment.Center)
        )

        if (rightResource != null) {
            PkImageView(
                painter = painterResource(rightResource),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { onRightClick?.invoke() })
        }
    }
}

