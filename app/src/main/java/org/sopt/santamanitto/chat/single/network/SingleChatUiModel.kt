package org.sopt.santamanitto.chat.single.network

data class SingleChatUiModel(
    val content: String,
    val createdAt: String,
    val isMine: Boolean,
    val isRead: Boolean,
    val chatType: SingleChatType
)