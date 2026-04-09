package com.peakmain.cmp_compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import cmcomposeui.composeapp.generated.resources.Res
import cmcomposeui.composeapp.generated.resources.background_laundry_delivery
import cmcomposeui.composeapp.generated.resources.compose_multiplatform
import cmcomposeui.composeapp.generated.resources.ic_online_check_out_disable
import com.peakmain.cmp_compose.basic.BasicFont
import com.peakmain.cmp_compose.basic.BasicRadius
import com.peakmain.cmp_compose.basic.BasicSize
import com.peakmain.cmp_compose.basic.BasicSpace
import com.peakmain.cmp_compose.constants.PkColor
import com.peakmain.cmp_compose.space.PkSpacer
import com.peakmain.cmp_compose.theme.PkTheme
import com.peakmain.cmp_compose.ui.button.PkButton
import com.peakmain.cmp_compose.ui.button.PkButtonDefault
import com.peakmain.cmp_compose.ui.cell.PkCell
import com.peakmain.cmp_compose.ui.divier.PkDivider
import com.peakmain.cmp_compose.ui.flow.PkFlowRow
import com.peakmain.cmp_compose.ui.image.PkImageView
import com.peakmain.cmp_compose.ui.progress.RoundedLinearProgressIndicator
import com.peakmain.cmp_compose.ui.text.PkHighlightText
import com.peakmain.cmp_compose.ui.title.PkTitleType
import com.peakmain.cmp_compose.utils.ImagePainterUtils
import com.peakmain.cmp_compose.utils.getScreenWidth
import com.peakmain.cmp_compose.ui.banner.PkBanner

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                PkDivider(modifier = Modifier.padding(top = 10.dp), isHorizontal = true)
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
                BannerPage()
                PkImageView(ImagePainterUtils.getPainter("https://img2.baidu.com/it/u=292395973,2170347184&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800"))
                PkHighlightTextPage()
                ButtonPage()
                FlowRowPage()
            }
        }
    }
}
/*@Composable
fun BannerPage() {
    val lists = ArrayList<String>().apply {
        add("https://img2.baidu.com/it/u=292395973,2170347184&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800")
        add("https://img0.baidu.com/it/u=3492687357,1203050466&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500")
        add("https://img2.baidu.com/it/u=2843793126,682473204&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800")
        add("https://img1.baidu.com/it/u=3907217777,761642486&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800")
        add("https://img1.baidu.com/it/u=1082651511,4058105193&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800")
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        PkBanner(
            lists,
            isAutoPlay = true,
            initialPage = 3,
            onBannerClick = {index,item->
                PkLog.e("TAG", "获取到点击后的数据：${item}")
            }) {index,item->
            Image(
                painter = ImagePainterUtils.getPainter(item),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
        }
    }
}*/

@Composable
fun BannerPage() {
    val vLists = arrayListOf("广告", "我是垂直轮播", "生活好滋味，就要上四休三")
    val itemWidth = getScreenWidth()
    PkBanner(
        vLists,
        isAutoPlay = true,
        isVertical = true,
        pagerHeight = 88.dp,
        pagerWidth = itemWidth
    ) { index, item ->
        Box() {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(BasicRadius.radius_8))
                    .background(PkColor.color_fill2)
                    .padding(bottom = BasicSpace.space_16)
                    .fillMaxSize(),
            ) {
                Box() {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(
                                    top = BasicSpace.space_16,
                                    start = BasicSpace.space_12,
                                    end = BasicSpace.space_12
                                ),
                        ) {
                            // 左侧 圆形背景 + 图标
                            Box(
                                modifier = Modifier
                                    .size(BasicSize.size_40)
                                    .clip(CircleShape)
                                    .background(PkColor.color_fill1),
                                contentAlignment = Alignment.Center
                            ) {
                                PkImageView(
                                    painter = painterResource(
                                        Res.drawable.ic_online_check_out_disable
                                    ),
                                    modifier = Modifier.size(BasicSize.size_24),
                                )
                            }

                            Spacer(modifier = Modifier.width(BasicSpace.space_8))

                            // 中间 内容
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "已入住",
                                    color = PkColor.color_text1,
                                    fontSize = BasicFont.font_14,
                                )
                                Spacer(modifier = Modifier.height(BasicSize.size_2))
                                Text(
                                    text = "2025年06月27日" + "  |  " + "金会员",
                                    color = PkColor.color_text2,
                                    fontSize = BasicFont.font_11,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }
                        // 底部进度条
                        RoundedLinearProgressIndicator(
                            progress = 0.5f,
                            modifier = Modifier
                                .padding(top = BasicSize.size_12)
                                .fillMaxWidth()
                                .height(BasicSize.size_4)
                                .padding(horizontal = BasicSpace.space_16),
                            color = PkColor.color_brand1,
                            cornerRadius = BasicRadius.radius_2
                        )
                        PkSpacer(height = BasicSize.size_16)
                    }
                    //洗衣取送
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(end = BasicSpace.space_8)
                            .align(Alignment.TopEnd)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.background_laundry_delivery),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                        )
                        Text(
                            text = "垂直轮播",
                            color = PkColor.color_text5,
                            fontSize = BasicFont.font_11,
                            modifier = Modifier
                                .padding(
                                    horizontal = BasicSpace.space_7,
                                    vertical = BasicSpace.space_4
                                )
                        )


                    }

                }
            }
        }
    }
}

@Composable
fun ButtonPage() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 20.dp, start = 20.dp)
    ) {
        PkButton(
            onClick = {

            }, elevation = null,
            colors = PkButtonDefault.transparentColor(),
            shape = PkTheme.shapes.medium,
            border = BorderStroke(0.5.dp, Color(0xFFD4D4D5))
        ) {
            Text(
                "继续挑战", fontWeight = FontWeight.W500,
                fontSize = BasicFont.font_12
            )
        }
        PkButton(
            onClick = {

            },
            elevation = null,
            colors = PkButtonDefault.buttonColors(),
            shape = PkTheme.shapes.medium,
        ) {
            Text(
                "领取任务", fontWeight = FontWeight.W500,
                fontSize = BasicFont.font_12
            )
        }
    }
}

@Composable
fun PkHighlightTextPage() {
    // 示例1：基本用法（多个关键字）
    PkHighlightText(
        text = "Jetpack Compose 是 Android 的现代 UI 工具包",
        keywords = listOf("compose", "android"),
        highlightColor = Color.Blue,
        style = MaterialTheme.typography.bodyLarge
    )

// 示例2：含特殊字符的关键字
    PkHighlightText(
        text = "价格：$199 (限时优惠)",
        keywords = listOf("$199", "（限时优惠）"),
        highlightColor = Color(0xFF4CAF50)
    )

// 示例3：无关键字/空列表
    PkHighlightText(
        text = "Hello World",
        keywords = emptyList() // 显示普通文本
    )
}

@Composable
fun FlowRowPage() {
    val tags = listOf(
        "Android",
        "Kotlin",
        "Jetpack Compose",
        "KMP",
        "Material Design",
        "UI",
        "Development"
    )
    var currentLine by remember {
        mutableStateOf(0)
    }
    var isExpand by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.padding(top = 40.dp)) {
            if (currentLine > 2) {
                PkCell(
                    "超过2行显示展开/更多",
                    type = PkTitleType.TextBold1(),
                    rightText = if (!isExpand) "展开" else "收起",
                    //rightIcon= if (isExpand) Res.drawable.icon_loading else Res.drawable.icon_loading,
                    rightClick = {
                        isExpand = !isExpand
                    }
                )
            }
            PkFlowRow(
                modifier = Modifier.padding(top = BasicSize.size_12),
                horizontalSpacing = 8.dp,
                verticalSpacing = 12.dp,
                maxLine = if (isExpand) Int.MAX_VALUE else 2,
                onLineCountChanged = {
                    currentLine = it
                }) {
                tags.forEach { tag ->
                    Text(
                        text = tag,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(Color.LightGray, RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
