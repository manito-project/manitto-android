package org.sopt.santamanitto.chat.single.network

data class SingleChatUiModel(
    val content: String,
    val createdAt: String,
    val isMine: Boolean,
    val isRead: Boolean,
    val chatType: SingleChatType,
    val dateText: String,
    val timeText: String,
    val isMyManitto: Boolean,
    val opponentName: String,
    val isPlaceholder: Boolean = false
) {
    companion object {
        fun createDateChatUiModel(date: String): SingleChatUiModel =
            SingleChatUiModel(
                content = "",
                createdAt = "",
                isMine = true,
                isRead = true,
                chatType = SingleChatType.TYPE_DATE,
                dateText = date,
                timeText = "",
                isMyManitto = true,
                opponentName = ""
            )
    }
}