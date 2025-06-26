package org.sopt.santamanitto.chat.single

import androidx.lifecycle.MutableLiveData
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
    var roomId: String = ""
    var conversationId: String = ""
    var isMyManitto: Boolean = false
    var opponentName: String = ""

    val inputText = MutableLiveData("")
    val isExpired = MutableLiveData(false)

    private var lastDate = ""

    fun getChatList(): List<SingleChatUiModel> {
        return if (tempChatList.isEmpty()) {
            createPlaceHolderUiModels()
        } else {
            mapToUiModels(tempChatList)
        }
    }

    fun postNewChat(): List<SingleChatUiModel> {
        if (inputText.value.isNullOrBlank()) return listOf()
        val result = mutableListOf<SingleChatUiModel>()
        val nowUtc = TimeUtil.getDateWithOffsetFromNow(0)
        val date = TimeUtil.convertUtcToKstDate(nowUtc)
        val time = TimeUtil.convertUtcToKstTime(nowUtc)
        if (lastDate != date) {
            result += SingleChatUiModel.createDateChatUiModel(date)
            lastDate = date
        }
        result += SingleChatUiModel(
            content = inputText.value!!,
            createdAt = nowUtc,
            isMine = true,
            isRead = false,
            chatType = TYPE_MINE,
            dateText = date,
            timeText = time,
            isMyManitto = isMyManitto,
            opponentName = opponentName
        )
        return result
    }

    private fun mapToUiModels(raw: List<SingleChatModel>): List<SingleChatUiModel> {
        val result = mutableListOf<SingleChatUiModel>()
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

    private fun createPlaceHolderUiModels(): List<SingleChatUiModel> {
        val nowUtc = TimeUtil.getDateWithOffsetFromNow(0)
        val date = TimeUtil.convertUtcToKstDate(nowUtc)
        val time = TimeUtil.convertUtcToKstTime(nowUtc)
        return listOf(
            SingleChatUiModel.createDateChatUiModel(date),
            SingleChatUiModel(
                content = "마니또에게 응원의 메시지를 보내볼까?\n메세지는 10자 이내로 보낼 수 있어!",
                createdAt = nowUtc,
                isMine = false,
                isRead = true,
                chatType = TYPE_OPPONENT,
                dateText = date,
                timeText = time,
                isMyManitto = isMyManitto,
                opponentName = "산타마니또",
                isPlaceholder = true
            )
        )
    }

    private val tempChatList = listOf(
        SingleChatModel(
            content = "안녕하세요제가누구게",
            createdAt = "2025-05-24T11:00:00.000Z",
            isMine = false,
            isRead = true
        ),
        SingleChatModel(
            content = "그러게나 말이에요",
            createdAt = "2025-05-24T13:10:00.000Z",
            isMine = true,
            isRead = true
        ),
        SingleChatModel(
            content = "앱 잘 동작하나요?",
            createdAt = "2025-05-25T05:31:00.000Z",
            isMine = false,
            isRead = true
        ),
        SingleChatModel(
            content = "네, 아주 잘 돼요 👍",
            createdAt = "2025-05-25T07:52:30.000Z",
            isMine = true,
            isRead = true
        ),
    )
}