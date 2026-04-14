package com.peakmain.androidapp

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * 通用基础响应包装类
 * data 可以是任意类型：单个实体、列表、或任意其他结构
 */
@Serializable
data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)

/**
 * 分页数据包装类
 * 当接口返回分页列表时，data 为 PageData<T>
 */
@Serializable
data class PageData<T>(
    val list: List<T>,
    val total: Int,
    val page: Int,
    val size: Int
)
/**
 * 文章/消息条目
 */
@Serializable
data class ArticleItem(
    val id: Long,
    val title: String,
    val summary: String,
    val author: Author,
    val category: String,
    val tags: String,
    @SerialName("cover_image")
    val coverImage: String,
    @SerialName("view_count")
    val viewCount: Int,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("is_published")
    val isPublished: Boolean,
    @SerialName("is_pinned")
    val isPinned: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)

/**
 * 作者信息
 */
@Serializable
data class Author(
    @SerialName("account_id")
    val accountId: Long,
    val email: String,
    val nickname: String,
    val avatar: String,
    val bio: String,
    val gender: String,
    val age: Int,
    val location: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)