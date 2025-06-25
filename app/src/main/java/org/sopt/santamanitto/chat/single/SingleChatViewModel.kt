package org.sopt.santamanitto.chat.single

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.santamanitto.chat.single.network.SingleChatModel
import org.sopt.santamanitto.chat.single.network.SingleChatType.TYPE_MINE
import org.sopt.santamanitto.chat.single.network.SingleChatType.TYPE_OPPONENT
import org.sopt.santamanitto.chat.single.network.SingleChatUiModel
import org.sopt.santamanitto.util.TimeUtil
import javax.inject.Inject

@HiltViewModel
class SingleChatViewModel @Inject constructor() : ViewModel() {
    var isMyManitto: Boolean = false
    var opponentName: String = ""

    val chatUiModelList: List<SingleChatUiModel> = emptyList()

    fun mapToUiModels(raw: List<SingleChatModel>): List<SingleChatUiModel> {
        val result = mutableListOf<SingleChatUiModel>()
        var lastDate: String? = null
        raw.forEach { model ->
            val date = TimeUtil.convertUtcToKstDate(model.createdAt)
            val time = TimeUtil.convertUtcToKstTime(model.createdAt)
            if (lastDate != date) {
                result += SingleChatUiModel.createDateChatUiModel(date)
                lastDate = date
            }
            result += SingleChatUiModel(
                content = model.content,
                createdAt = model.createdAt,
                isMine = model.isMine,
                isRead = model.isRead,
                chatType = if (model.isMine) TYPE_MINE else TYPE_OPPONENT,
                dateText = date,
                timeText = time,
                isMyManitto = isMyManitto,
                opponentName = opponentName
            )
        }
        return result
    }

    val tempChatList = listOf(
        // 상대가 보낸 메시지, 읽음
        SingleChatModel(
            content = "안녕하세요제가누구게",
            createdAt = "2025-05-24T11:00:00.000Z",
            isMine = false,
            isRead = true
        ),
        // 추가 더미 데이터 예시
        SingleChatModel(
            content = "앱 잘 동작하나요?",
            createdAt = "2025-05-25T11:05:00.000Z",
            isMine = true,
            isRead = true
        ),
        SingleChatModel(
            content = "네, 아주 잘 돼요 👍",
            createdAt = "2025-05-25T11:06:30.000Z",
            isMine = false,
            isRead = true
        ),
        SingleChatModel(
            content = "오늘 회의는 몇 시에?",
            createdAt = "2025-05-26T11:07:00.000Z",
            isMine = true,
            isRead = false
        ),
        SingleChatModel(
            content = "오후 2시로 잡혀있습니다.",
            createdAt = "2025-05-26T11:08:45.000Z",
            isMine = false,
            isRead = false
        )
    )
}