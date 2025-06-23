package org.sopt.santamanitto.chat.overview

data class ChatItemModel(
    val roomId: String,
    val roomName: String,
    val expirationDate: String,
    val opponentName: String,
    val lastMessageAt: String?,
    val lastContent: String?,
    val conversationId: String?,
    //TODO: 서버 수정 이우 대응 필요
    val isMyManitto: Boolean,
    val unreadMessage: Int
)