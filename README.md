# CmComposeUI

[![](https://jitpack.io/v/Peakmain/CmComposeUI.svg)](https://jitpack.io/#Peakmain/CmComposeUI)

CmComposeUI 是一个基于 Kotlin Multiplatform Compose 的 UI 组件库，提供丰富的通用组件，支持 Android 和 iOS 双平台。

## 引入使用

在项目根目录 `settings.gradle.kts` 添加 JitPack 仓库：

```kotlin
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

在模块 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("com.github.Peakmain:CmComposeUI:latest-version")
}
```

## 组件文档

### 📦 基础常量

#### BasicColor - 预定义颜色常量
常用颜色常量集合，方便快速使用：

| 常量 | 颜色值 | 说明 |
|------|--------|------|
| `BasicColor.color_1F401B` | `#1F401B` | 深绿色主题色 |
| `BasicColor.color_EBEBF0` | `#EBEBF0` | 浅灰背景色 |
| `BasicColor.color_333333` | `#333333` | 深文本色 |
| `BasicColor.color_666666` | `#666666` | 次要文本色 |

**示例：**
```kotlin
Text(color = BasicColor.color_1F401B, text = "Hello World")
```

#### BasicFont - 预定义字体大小
从 1.sp 到 100.sp 完整尺寸常量。

**示例：**
```kotlin
Text(fontSize = BasicFont.font_14, text = "普通文本")
```

#### BasicSize - 预定义尺寸常量
从 0.dp 到 100.dp 完整尺寸常量，用于间距、大小设置。

**示例：**
```kotlin
Spacer(modifier = Modifier.height(BasicSize.size_16))
```

---

### 🔄 轮播/横幅 - PkBanner

支持水平/垂直轮播，支持自动播放、无限轮播。

```kotlin
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> PkBanner(
    lists: List<T?>,                    // 轮播数据源（必填）
    pagerWidth: Dp = 290.dp,           // 单个轮播项宽度
    pagerHeight: Dp = 116.dp,          // 轮播容器高度
    pageSpacing: Dp = 12.dp,           // 轮播项间距
    contentPadding: Dp = 18.dp,        // 首尾边距（居中效果）
    duration: Long = 3000,              // 自动轮播间隔（毫秒）
    isAutoPlay: Boolean = false,        // 是否开启自动轮播
    initialPage: Int = 0,               // 初始显示页面索引
    onBannerClick: ((Int, T?) -> Unit)? = null,  // 点击回调
    isVertical: Boolean = false,       // 是否纵向轮播
    userScrollEnabled: Boolean = true,  // 是否允许手动滑动
    content: @Composable (Int, T?) -> Unit        // 自定义内容（必填）
)
```

**示例：**
```kotlin
val banners = listOf("https://example.com/banner1.jpg", "https://example.com/banner2.jpg")
PkBanner(
    lists = banners,
    isAutoPlay = true,
    pagerHeight = 180.dp,
    onBannerClick = { index, item ->
        // 处理点击事件
    }
) { index, item ->
    // 自定义每个轮播项的内容
    CoilImage(data = item, contentDescription = null)
}
```

**特点：**
- 支持水平和垂直两种方向
- 自动播放时，当轮播离开屏幕会自动暂停
- 用户触摸时自动暂停自动播放，释放后恢复
- 垂直方向支持无限轮播

---

### 🔘 按钮 - PkButton

基于 Material3 Button 封装的通用按钮，支持默认样式和幽灵按钮。

```kotlin
@Composable
fun PkButton(
    onClick: () -> Unit,               // 点击回调（必填）
    modifier: Modifier = Modifier,     // 布局修饰符
    enabled: Boolean = true,           // 是否启用
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    shape: Shape = PkTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = PkButtonDefault.buttonColors(interactionSource),
    contentPadding: PaddingValues = PkButtonDefault.BigContentPadding,
    content: @Composable RowScope.() -> Unit    // 按钮内容（必填）
)
```

**预定义颜色配置：**

| 方法 | 说明 |
|------|------|
| `PkButtonDefault.buttonColors()` | 默认实心按钮配色 |
| `PkButtonDefault.transparentColor()` | 幽灵/透明背景按钮配色 |
| `PkButtonDefault.BigContentPadding` | 大按钮内边距 |
| `PkButtonDefault.smallContentPadding` | 小按钮内边距 |

**示例：**
```kotlin
// 默认按钮
PkButton(onClick = { /* 处理点击 */ }) {
    Text("确认提交")
}

// 幽灵按钮
PkButton(
    onClick = { /* 处理点击 */ },
    colors = PkButtonDefault.transparentColor()
) {
    Text("取消")
}
```

---

### 📱 导航栏 - PkNavBar

自定义顶部导航栏，支持返回按钮、沉浸式状态栏。

```kotlin
@Composable
fun PkNavBar(
    title: String,                     // 标题文本（必填）
    backgroundColor: Color = Color(0xFFFFFEFA),  // 背景色
    horizontalContentPadding: Dp = BasicSpace.space_18,  // 水平内边距
    verticalContentPadding: Dp = BasicSpace.space_10,    // 垂直内边距
    backResource: DrawableResource = Res.drawable.compose_icon_retrun,  // 返回图标
    backResourceSize: Dp = BasicSize.size_24,  // 返回图标尺寸
    showBack: Boolean = true,           // 是否显示返回按钮
    onBackClick: (() -> Unit)? = null,  // 返回点击回调
    titleFontSize: TextUnit = BasicFont.font_18,  // 标题字号
    titleColor: Color = Color(0xFF1F401B),  // 标题颜色
    isImmersive: Boolean = true,        // 是否沉浸式状态栏
    darkIcons: Boolean = true,           // 状态栏图标是否深色
    rightResource: DrawableResource? = null,  // 右侧图标资源
    onRightClick: (() -> Unit)? = null  // 右侧点击回调
)
```

**示例：**
```kotlin
PkNavBar(
    title = "首页",
    onBackClick = { 
        // 处理返回
    },
    rightResource = Res.drawable.ic_share,
    onRightClick = { 
        // 处理分享
    }
)
```

---

### 📋 单元格 - PkCell

设置单元格，支持左侧标题 + 右侧自定义内容。

```kotlin
// 重载1：右侧完全自定义
@Composable
fun PkCell(
    text: String,                       // 左侧标题（必填）
    type: PkTitleType = PkTitleType.BigTitle1(),  // 标题样式
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF333333),   // 标题颜色
    rightContent: @Composable RowScope.() -> Unit  // 右侧内容（必填）
)

// 重载2：右侧文字 + 箭头
@Composable
fun PkCell(
    text: String,                       // 左侧标题（必填）
    type: PkTitleType = PkTitleType.BigTitle1(),
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF333333),
    rightText: String = "",             // 右侧文本
    rightTextColor: Color = Color(0xFF666666),
    rightTextSize: TextUnit = BasicFont.font_12,
    rightIcon: DrawableResource? = Res.drawable.ic_right_arrow,
    rightIconSize: Dp = BasicSize.size_12,
    isRightIconRotated: Boolean = false,  // 是否启用展开收起旋转动画
    rightClick: (() -> Unit)? = null    // 右侧点击回调
)
```

支持的标题样式 `PkTitleType`：

| 样式 | 字号 | 字重 |
|------|------|------|
| `BigTitle1` | 24.sp | W500 |
| `BigTitle2` | 22.sp | W500 |
| `BigTitle3` | 18.sp | W500 |
| `TitleBold1` | 16.sp | W500 |
| `TitleNormal1` | 16.sp | W400 |
| `TitleBold2` | 15.sp | W500 |
| `TitleNormal2` | 15.sp | W400 |
| `SmallTitleBold` | 14.sp | W500 |
| `SmallTitleNormal` | 14.sp | W400 |
| `TextBold1` | 12.sp | W500 |
| `TextNormal1` | 12.sp | W400 |

**示例：**
```kotlin
// 最简单用法：文字 + 箭头
PkCell(
    text = "关于我们",
    rightText = "版本 1.0.0"
) {
    // 点击跳转
}

// 自定义右侧内容
PkCell(
    text = "开启推送",
    rightContent = {
        Switch(checked = true, onCheckedChange = {})
    }
)
```

---

### 🔍 高亮文本 - PkHighlightText

搜索结果高亮关键字组件，支持多关键字、重叠匹配。

```kotlin
@Composable
fun PkHighlightText(
    text: String,                       // 原始文本
    keywords: List<String>,              // 关键字列表
    highlightColor: Color = Color.Red,  // 高亮颜色
    style: TextStyle = LocalTextStyle.current  // 基础文本样式
)
```

**示例：**
```kotlin
PkHighlightText(
    text = "Jetpack Compose 是 Android 的现代 UI 工具包",
    keywords = listOf("compose", "android"),
    highlightColor = Color.Blue
)
```

**特点：**
- 自动处理正则特殊字符转义
- 不区分大小写匹配
- 自动合并重叠区间
- 关键字为空列表时直接显示普通文本

---

### 📊 进度条 - RoundedLinearProgressIndicator

圆角线性进度条。

```kotlin
@Composable
fun RoundedLinearProgressIndicator(
    progress: Float,                    // 进度 0f ~ 1f
    modifier: Modifier = Modifier,
    color: Color = BasicColor.color_1F401B,  // 进度颜色
    backgroundColor: Color = BasicColor.color_EBEBF0,  // 背景色
    height: Dp = 4.dp,                 // 进度条高度
    cornerRadius: Dp = 2.dp            // 圆角半径
)
```

**示例：**
```kotlin
RoundedLinearProgressIndicator(
    progress = 0.7f,
    height = 8.dp,
    cornerRadius = 4.dp
)
```

---

### 🌊 瀑布流 - PkStaggeredVerticalGrid

非懒加载瀑布流布局，适合少量数据（< 50 项）使用。

```kotlin
@Composable
fun PkStaggeredVerticalGrid(
    columns: Int,                       // 列数（必须 >0）
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),  // 内容内边距
    verticalItemSpacing: Dp = 8.dp,     // 垂直间距
    horizontalItemSpacing: Dp = 8.dp,   // 水平间距
    content: @Composable () -> Unit      // 子项内容
)
```

**示例：**
```kotlin
PkStaggeredVerticalGrid(
    columns = 2,
    verticalItemSpacing = 8.dp,
    horizontalItemSpacing = 8.dp
) {
    items.forEach { item ->
        // 每个网格项
        ItemCard(item)
    }
}
```

> 提示：如果数据量超过 50 项，推荐使用 `LazyVerticalStaggeredGrid`。

---

### 🔤 流式布局 - PkFlowRow

限制最大行数的自动换行流式布局。

```kotlin
@Composable
fun PkFlowRow(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 0.dp,       // 子项水平间距
    verticalSpacing: Dp = 0.dp,         // 行间垂直间距
    maxLine: Int = 2,                   // 最大显示行数，超出截断
    onLineCountChanged: ((Int) -> Unit)? = null,  // 行数变化回调
    content: @Composable () -> Unit      // 子组件内容
)
```

**示例：**
```kotlin
val tags = listOf("Android", "Kotlin", "Jetpack Compose", "KMP", "Material Design")
PkFlowRow(
    horizontalSpacing = 8.dp,
    verticalSpacing = 12.dp,
    maxLine = 2
) {
    tags.forEach { tag ->
        Text(
            text = tag,
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
```

---

### 📐 网格布局 - PkGridLayout

简单网格布局，按行均分宽度。

```kotlin
@Composable
fun <E> PkGridLayout(
    columns: Int,                       // 每行几列（必填，>=1）
    data: MutableList<E>,               // 数据源（必填）
    isShowHorizontalDivider: Boolean = false,  // 是否显示水平分割线
    divider: @Composable (() -> Unit)? = null, // 自定义分割线
    columnSpacing: Dp = 12.dp,          // 列间距
    content: @Composable (Int, E) -> Unit     // 子项内容（必填，参数：索引、数据）
)
```

**示例：**
```kotlin
val data = listOf("选项1", "选项2", "选项3", "选项4")
PkGridLayout(
    columns = 2,
    data = data.toMutableList(),
    isShowHorizontalDivider = true
) { index, item ->
    Text(item)
}
```

---

### 🖼 图片 - PkImageView

简单图片组件封装。

```kotlin
@Composable
fun PkImageView(
    painter: Painter,                    // 图片画家（必填）
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    tintColor: Color? = null            // 着色颜色
)
```

---

### 🪧 分割线 - PkDivider

统一分割线组件，支持实线/虚线、水平/垂直方向。

```kotlin
@Composable
fun PkDivider(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFF1EFE9),   // 分割线颜色
    height: Dp = 28.dp,                 // 高度（垂直分割线生效）
    thickness: Dp = 1.dp,               // 粗细（实线生效）
    startIndent: Dp = 0.dp,             // 起始缩进（实线生效）
    isHorizontal: Boolean = false,      // 是否水平分割线
    isDash: Boolean = false,             // 是否虚线
    strokeWidth: Dp = 0.5.dp,           // 虚线宽度
    dashLength: Dp = 2.dp,              // 虚线线段长度
    gapLength: Dp = 2.dp                // 虚线间隔长度
)
```

**示例：**
```kotlin
// 水平实线
PkDivider(isHorizontal = true)

// 水平虚线
PkDivider(isHorizontal = true, isDash = true, dashLength = 4.dp, gapLength = 4.dp)
```

---

### ↔️ 间距 - PkSpacer

快捷间距组件。

```kotlin
@Composable
fun PkSpacer(
    modifier: Modifier = Modifier,
    width: Dp = 10.dp,
    height: Dp = 10.dp,
    isHorizontal: Boolean = true
)
```

**示例：**
```kotlin
// 水平间距 16dp
PkSpacer(width = 16.dp, height = 1.dp, isHorizontal = true)

// 垂直间距 8dp
PkSpacer(width = 1.dp, height = 8.dp, isHorizontal = false)
```

---

### 🔤 标题 - PkTitle

标准化标题文本组件，内置多种预定义样式。

```kotlin
@Composable
fun PkTitle(
    text: String,                       // 文本内容
    type: PkTitleType = PkTitleType.BigTitle1(),  // 样式类型
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF333333),
    fontStyle: FontStyle? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current
)
```

详见 `PkTitleType` 表格。

---

## 项目结构

```
composeApp/
├── src/
│   ├── commonMain/
│   │   └── kotlin/com/peakmain/cmp_compose/
│   │       ├── basic/               # 基础常量
│   │       │   ├── BasicColor.kt    # 颜色常量
│   │       │   ├── BasicFont.kt     # 字体大小常量
│   │       │   └── BasicSize.kt     # 尺寸常量
│   │       ├── space/               # 间距
│   │       │   └── PkSpacer.kt      # 间距组件
│   │       ├── theme/               # 主题
│   │       ├── ui/                  # UI 组件
│   │       │   ├── banner/          # 轮播
│   │       │   │   └── PkBanner.kt  # 轮播组件
│   │       │   ├── button/          # 按钮
│   │       │   │   ├── PkButton.kt  # 按钮组件
│   │       │   │   └── PkButtonDefault.kt  # 默认配置
│   │       │   ├── cell/           # 单元格
│   │       │   │   └── PkCell.kt    # 单元格组件
│   │       │   ├── divier/         # 分割线
│   │       │   │   ├── PkDivider.kt # 分割线组件
│   │       │   │   ├── PkDashDivider.kt
│   │       │   │   └── PkFullDivider.kt
│   │       │   ├── flow/           # 流式布局
│   │       │   │   └── PkFlowRow.kt
│   │       │   ├── grid/           # 瀑布流
│   │       │   │   └── PkStaggeredVerticalGrid.kt
│   │       │   ├── image/          # 图片
│   │       │   │   └── PkImageView.kt
│   │       │   ├── progress/       # 进度条
│   │       │   │   └── RoundedLinearProgressIndicator.kt
│   │       │   ├── text/           # 文本
│   │       │   │   └── PkHighlightText.kt
│   │       │   └── title/          # 标题/导航栏
│   │       │       ├── PkNavBar.kt # 顶部导航栏
│   │       │       └── PkTitle.kt  # 标题文本
│   │       └── utils/               # 工具类
│   ├── androidMain/                # Android 平台代码
│   └── iosMain/                    # iOS 平台代码
└── ...
```

## 示例项目

项目提供完整的 Android 示例，运行 `androidapp` 模块即可查看所有组件效果。

## 许可证

```
Copyright 2025 Peakmain

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
