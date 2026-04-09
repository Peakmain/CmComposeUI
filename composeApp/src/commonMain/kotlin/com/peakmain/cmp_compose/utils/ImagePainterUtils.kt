package com.peakmain.cmp_compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import cmcomposeui.composeapp.generated.resources.Res
import cmcomposeui.composeapp.generated.resources.icon_loading
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.AsyncImagePainter
import coil3.compose.AsyncImagePainter.State.Empty.painter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.peakmain.cmp_compose.expect.PkLog
import org.jetbrains.compose.resources.DrawableResource

/**
 * author ：Peakmain
 * createTime：2025/3/6
 * mail:2726449200@qq.com
 * describe：图片加载工具类
 */
object ImagePainterUtils {
    /**
     * 根据图片 URL 获取 AsyncImagePainter 对象。
     *
     * @param imageUrl 图片的 URL，如果为空则显示占位图。
     * @param placeDrawableResId 图片加载过程中显示的占位图 Drawable 资源 ID，默认为 R.drawable.icon_loading。
     * @param targetWidth   目标解码宽度（像素）。
     *                      - 当不为 `0` 时，会在解码阶段下采样图片，减少内存占用。
     *                      - 建议设置为与容器宽度相近的像素值，避免解码原图造成高内存开销。
     * @param targetHeight  目标解码高度（像素）。
     *                       - 与 [targetWidth] 配合使用，控制图片的最终解码尺寸。
     *                       - 如果容器为固定比例（如 `ContentScale.Crop`），建议解码尺寸略大于显示区域，防止缩放模糊。
     *
     * @param filterQuality     绘制过滤质量（影响缩放清晰度与性能）。
     *                          - 默认值：`DefaultFilterQuality`（即 [FilterQuality.Low]）
     *                          - 可选值：
     *                               - `FilterQuality.None`：最快速，但缩放后可能出现锯齿。
     *                               - `FilterQuality.Low`：平衡性能与画质。
     *                               - `FilterQuality.Medium`：更平滑的缩放效果，性能中等。
     *                               - `FilterQuality.High`：最高画质，适合缩放后仍需高清展示的场景（代价是性能略低）。
     * @return 返回一个 AsyncImagePainter 对象。
     */
    @Composable
    fun getPainter(
        imageUrl: String?,
         placeDrawableResId: DrawableResource = Res.drawable.icon_loading,
         targetWidth: Int = 0,
         targetHeight: Int = 0,
        filterQuality: FilterQuality = DefaultFilterQuality,
        imageLoader: ImageLoader = getImageLoader(LocalPlatformContext.current),
    ): AsyncImagePainter {
        if (imageUrl.isNullOrEmpty()) {
            return rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(placeDrawableResId)
                    .build(),
                imageLoader = imageLoader
            )
        }

        val request = ImageRequest.Builder(LocalPlatformContext.current)
            .data(imageUrl)
            .build()

        val painter = rememberAsyncImagePainter(
            model = request,
            imageLoader = imageLoader,
            filterQuality = filterQuality,
            onError = {
                PkLog.e("TAG","加载失败：${it}")
            }
        )
        return painter

    }

    private fun getImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context).components {
            add(SvgDecoder.Factory())
        }.build()
    }
}