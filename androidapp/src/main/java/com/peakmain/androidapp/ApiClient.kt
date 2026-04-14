package com.peakmain.androidapp

import android.R.attr.level
import android.net.http.HttpResponseCache.install
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
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
            json(Json {
                ignoreUnknownKeys = true // 忽略后端多余的字段，防止崩溃
                isLenient = true        // 允许非标准 JSON 格式（如宽容的引号）
                prettyPrint = true      // 格式化输出，便于开发查看
            })
        }

        // 2. 日志打印：监控请求与响应内容
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO // 生产环境建议设为 LogLevel.NONE
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