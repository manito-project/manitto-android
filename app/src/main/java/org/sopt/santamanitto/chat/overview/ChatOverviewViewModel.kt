package org.sopt.santamanitto.chat.overview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatOverviewViewModel @Inject constructor() : ViewModel() {
    // TODO: 임시 데이터
    val sampleChatItems = listOf(
        // 1) 방금 전 메시지, 안읽음, 나의 마니또
        ChatItemModel(
            roomId = "room-1",
            roomName = "개발 스터디",
            expirationDate = "2025-07-01T12:00:00.000Z",  // 미래라 만료 아님
            opponentName = "지은",
            lastMessageAt = "2025-06-24T13:09:30.000Z",   // 방금
            lastContent = "참석하실 건가요?",
            conversationId = "conv-1",
            isMyManitto = true,
            unreadMessage = 5
        ),

        // 2) 20분 전 메시지, 읽음, 상대방 마니또
        ChatItemModel(
            roomId = "room-2",
            roomName = "주간 회의",
            expirationDate = "2025-06-24T10:00:00.000Z",  // 오늘 오전, 이미 만료
            opponentName = "팀원들",
            lastMessageAt = "2025-06-24T12:50:00.000Z",   // 20분 전
            lastContent = "준비사항 공유",
            conversationId = "conv-2",
            isMyManitto = false,
            unreadMessage = 0
        ),

        // 3) 22시간 전 메시지(어제), 안읽음, 상대방 마니또
        ChatItemModel(
            roomId = "room-3",
            roomName = "프로젝트 알림",
            expirationDate = "2025-06-20T00:00:00.000Z",  // 4일 전, 만료
            opponentName = "알림봇",
            lastMessageAt = "2025-06-23T15:00:00.000Z",   // 약 22시간 전
            lastContent = "빌드 실패 알림",
            conversationId = "conv-3",
            isMyManitto = false,
            unreadMessage = 2
        ),

        // 4) 7~13일 전 메시지(1주일 전), 읽음, 나의 마니또
        ChatItemModel(
            roomId = "room-4",
            roomName = "주문관리",
            expirationDate = "2025-06-15T00:00:00.000Z",  // 9일 전, 만료
            opponentName = "관리자",
            lastMessageAt = "2025-06-17T08:00:00.000Z",   // 7일 전
            lastContent = "주문이 배송되었습니다",
            conversationId = "conv-4",
            isMyManitto = true,
            unreadMessage = 0
        ),

        // 5) 30일 이상 지난 메시지 → 날짜 표시, lastContent null
        ChatItemModel(
            roomId = "room-5",
            roomName = "개발 토론",
            expirationDate = "2025-05-20T00:00:00.000Z",  // 34일 전, 만료
            opponentName = "토론방",
            lastMessageAt = "2025-05-15T09:30:00.000Z",   // 40일 전 → "2025-05-15"
            lastContent = null,
            conversationId = "conv-5",
            isMyManitto = false,
            unreadMessage = 0
        ),

        // 6) 대화 시작 전 (message, conversationId 모두 null), 읽음 상태
        ChatItemModel(
            roomId = "room-6",
            roomName = "새 채팅",
            expirationDate = "2025-07-10T00:00:00.000Z",  // 미래라 만료 아님
            opponentName = "호스트",
            lastMessageAt = null,
            lastContent = null,
            conversationId = null,
            isMyManitto = true,
            unreadMessage = 0
        )
    )
}