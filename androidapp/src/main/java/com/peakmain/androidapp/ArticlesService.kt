package com.peakmain.androidapp

import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * @author ：Peakmain
 * createTime：2026/4/14
 * mail:2726449200@qq.com
 * describe：
 */
object ArticlesService {

    suspend fun getArticlesList(page: Int = 1, size: Int = 10): Result<BaseResponse<PageData<ArticleItem>>> {
        return runCatching {
            ApiClient.getInstance().client.get("articles") {
                url {
                    parameters.append("page", page.toString())
                    parameters.append("size", size.toString())
                }
            }.body<BaseResponse<PageData<ArticleItem>>>()
        }
    }
}