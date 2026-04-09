package com.peakmain.cmp_compose.ui.button

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.peakmain.cmp_compose.theme.PkTheme


/**
 * author ：Peakmain
 * createTime：2025/4/10
 * mail:2726449200@qq.com
 * describe：
 */
object PkButtonDefault {
    private val ButtonBigHorizontalPadding = 24.dp
    private val ButtonBigVerticalPadding = 11.dp

    private val ButtonSmallHorizontalPadding = 12.dp
    private val ButtonSmallVerticalPadding = 5.dp

    /**
     * The default content padding used by [Button]
     */
    val BigContentPadding = PaddingValues(
        start = ButtonBigHorizontalPadding,
        top = ButtonBigVerticalPadding,
        end = ButtonBigHorizontalPadding,
        bottom = ButtonBigVerticalPadding
    )

    val smallContentPadding = PaddingValues(
        start = ButtonSmallHorizontalPadding,
        top = ButtonSmallVerticalPadding,
        end = ButtonSmallHorizontalPadding,
        bottom = ButtonSmallVerticalPadding
    )

    /**
     *  默认
     */
    @Composable
    fun buttonColors(
        interactionSource: InteractionSource = remember { MutableInteractionSource() },
        backgroundColor: Color = PkTheme.colors.primary,
        contentColor: Color = PkTheme.colors.contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = Color(0xFFD4D4D5),
        disabledContentColor: Color = PkTheme.colors.onPrimary,
        pressedBackgroundColor: Color = backgroundColor,
        pressedContentColor: Color = contentColor
    ): ButtonColors = pkButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        interactionSource = interactionSource,
        pressedBackgroundColor = pressedBackgroundColor,
        pressedContentColor = pressedContentColor
    )

    /**
     * 幽灵按钮
     */
    @Composable
    fun transparentColor(
        interactionSource: InteractionSource = remember { MutableInteractionSource() },
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = Color(0xFF1F4D1B),
        disabledBackgroundColor: Color = Color(0xFFD4D4D5),
        disabledContentColor: Color = Color(0xFF677C64),
        pressedBackgroundColor: Color = backgroundColor,
        pressedContentColor: Color = contentColor
    ): ButtonColors = pkButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        interactionSource = interactionSource,
        pressedBackgroundColor = pressedBackgroundColor,
        pressedContentColor = pressedContentColor
    )

}

@Composable
fun pkButtonColors(
    interactionSource: InteractionSource,
    backgroundColor: Color,
    contentColor: Color,
    disabledBackgroundColor: Color,
    disabledContentColor: Color,
    pressedBackgroundColor: Color,
    pressedContentColor: Color
): ButtonColors {

    val isPressed by interactionSource.collectIsPressedAsState()
    val finalContainerColor = when {
        isPressed -> pressedBackgroundColor
        else -> backgroundColor
    }

    val finalContentColor = when {
        isPressed -> pressedContentColor
        else -> contentColor
    }

    return ButtonDefaults.buttonColors(
        containerColor = finalContainerColor,
        contentColor = finalContentColor,
        disabledContainerColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor
    )
}