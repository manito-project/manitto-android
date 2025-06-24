package org.sopt.santamanitto.chat.single.network

data class SingleChatModel(
    val content: String,
    val createdAt: String,
    val isMine: Boolean,
    val isRead: Boolean
)