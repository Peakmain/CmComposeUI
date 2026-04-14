package com.peakmain.androidapp

import android.R.attr.level
import android.net.http.HttpResponseCache.install
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * @author ：Peakmain
 * createTime：2026/4/13
 * mail:2726449200@qq.com
 * describe：Api客户端单例，支持配置BaseUrl和默认Header
 */
class ApiClient private constructor(
    val baseUrl: String,
    val defaultHeaders: Map<String, String> = emptyMap()
) {
    val client: HttpClient = HttpClient {
        // 1. JSON 序列化配置：将网络 Byte 流转换为对象
        install(ContentNegotiation) {
            val json = Json {
                ignoreUnknownKeys = true    // 忽略后端多余的字段，防止崩溃
                isLenient = true            // 允许非标准 JSON 格式（如宽容的引号）
                coerceInputValues = true    // 无效输入强制转为 null，避免解析失败
                prettyPrint = true          // 格式化输出，便于开发查看
            }
            json(json, ContentType.Application.Json)    // 标准 JSON
            json(json, ContentType.parse("text/json")) // 兼容服务端返回 text/json
        }
        // 2. 日志打印：监控请求与响应内容，使用漂亮的方框格式
        install(Logging) {
            logger = PrettyHttpLogger(defaultHeaders)
            level = LogLevel.ALL // 打印完整请求响应
        }

        // 3. 默认请求配置：配置 BaseURL 和通用 Header
        defaultRequest {
            url(baseUrl)
            header("Content-Type", "application/json")
            // 添加自定义headers
            defaultHeaders.forEach { (key, value) ->
                header(key, value)
            }
        }


        // 4. 超时与重试配置：针对不同引擎可以进行细致调优
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }
    }

    companion object {
        @Volatile
        private var instance: ApiClient? = null

        /**
         * 获取单例实例，如果未初始化则使用默认配置
         */
        fun getInstance(): ApiClient {
            if (instance == null) {
                synchronized(ApiClient::class.java) {
                    if (instance == null) {
                        instance = ApiClient(
                            baseUrl = "https://api.apiopen.top/api/"
                        )
                    }
                }
            }
            return instance!!
        }

        /**
         * 初始化单例，可自定义BaseUrl和headers
         * 建议在Application中调用
         */
        fun init(baseUrl: String, defaultHeaders: Map<String, String> = emptyMap()): ApiClient {
            synchronized(ApiClient::class.java) {
                instance = ApiClient(baseUrl, defaultHeaders)
                return instance!!
            }
        }
    }
}

/**
 * 漂亮的 HTTP 日志格式化输出，完全匹配你展示的格式：
 *
 * ╔═══════════════════════════════════════════════════════════════════════════════════
 *  ║ 请求发起
 *  ╟───────────────────────────────────────────────────────────────────────────────────────
 *  ║ request method  : POST
 *  ║ request url     : https://xxx
 *  ║ request body    : {...}
 *  ║ request headers : User-Agent: Android
 *  ║                   At-Platform-Type: 1
 *  ║                   ...
 *  ║
 *  ║ 请求响应
 *  ╟───────────────────────────────────────────────────────────────────────────────────────
 *  ║ 耗时 (不准)       : 960 毫秒
 *  ║ response body    : {...}
 *  ╚═══════════════════════════════════════════════════════════════════════════════════
 */
class PrettyHttpLogger(private val defaultHeaders: Map<String, String> = emptyMap()) : Logger {
    private val buffer = mutableListOf<String>()
    private var isRequestBlock = false
    private var requestStartTime = 0L

    companion object {
        private const val TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════"
        private const val MIDDLE_BORDER = "╟───────────────────────────────────────────────────────────────────────────────────────"
        private const val BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════"
        private const val LINE_PREFIX = "                 ║ "
    }

    override fun log(message: String) {
        val lines = message.lines()
        for (rawLine in lines) {
            val line = rawLine.trimEnd()

            // 新请求开始
            if (line.startsWith("REQUEST")) {
                // 输出上一个块（如果有）
                if (buffer.isNotEmpty()) {
                    flush()
                }
                buffer.clear()
                requestStartTime = System.currentTimeMillis()
                buffer.add("请求发起")
                buffer.add(MIDDLE_BORDER)
                isRequestBlock = true
                continue
            }

            // 提取 method (下一行就是 METHOD: POST)
            if (isRequestBlock && line.startsWith("METHOD:")) {
                val method = line.substringAfter("METHOD:").trim()
                buffer.add("request method  : $method")
                continue
            }

            // 提取 url FROM 行，FROM 就是 url
            if (isRequestBlock && line.startsWith("FROM:")) {
                val url = line.substringAfter("FROM:").trim()
                buffer.add("request url     : $url")
                continue
            }

            // 新响应开始，添加分隔线继续放在同一个方框里
            if (line.startsWith("RESPONSE")) {
                buffer.add("")
                buffer.add("请求响应")
                buffer.add(MIDDLE_BORDER)
                isRequestBlock = false
                buffer.add(line)
                continue
            }

            // 跳过 COMMON HEADERS 和 CONTENT HEADERS 标题
            if (line == "COMMON HEADERS" || line == "CONTENT HEADERS") {
                continue
            }

            // 响应部分：跳过所有 -> 开头的 header 行，只保留 BODY 内容
            if (!isRequestBlock && line.startsWith("-> ")) {
                continue
            }

            // 请求块：在 BODY START 之前插入我们的公共 headers
            if (isRequestBlock && line.startsWith("BODY START") && defaultHeaders.isNotEmpty()) {
                buffer.add("request body    : ")
                buffer.add("request headers : User-Agent: Android")
                defaultHeaders.forEach { (key, value) ->
                    buffer.add("                   $key: $value")
                }
                buffer.add("")
                buffer.add(line)
                continue
            }

            // 请求部分：跳过我们已经打印过的 headers（避免重复）
            if (isRequestBlock && line.startsWith("-> ") && defaultHeaders.any { line.contains(it.key) }) {
                continue
            }

            // 整个请求+响应一起结束输出，先插入耗时
            if (line.endsWith("BODY END") && !isRequestBlock) {
                // 计算耗时并插入
                if (requestStartTime > 0) {
                    val duration = System.currentTimeMillis() - requestStartTime
                    buffer.add("")
                    buffer.add("耗时 (不准)       : $duration 毫秒")
                }
                buffer.add(line)
                flush()
                buffer.clear()
            } else {
                buffer.add(line)
            }
        }
    }

    private fun flush() {
        if (buffer.isEmpty()) return
        println()
        println(TOP_BORDER)
        for (line in buffer) {
            when {
                line == MIDDLE_BORDER -> println("$LINE_PREFIX$MIDDLE_BORDER")
                line.isBlank() -> println(LINE_PREFIX)
                else -> println("$LINE_PREFIX$line")
            }
        }
        println(BOTTOM_BORDER)
    }
}