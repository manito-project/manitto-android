package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.main.list.RoomState
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.util.TimeUtil

data class MyManittoModel(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Creator")
    val creator: Creator,
    @SerializedName("deletedByCreatorDate")
    val deletedByCreatorDate: String?,
    @SerializedName("expirationDate")
    val expirationDate: String?,
    @SerializedName("id")
    val roomId: String,
    @SerializedName("invitationCode")
    val invitationCode: String,
    @SerializedName("matchingDate")
    val matchingDate: String?,
    @SerializedName("Members")
    val members: List<Member>,
    @SerializedName("Missions")
    val missions: List<Mission>,
    @SerializedName("roomName")
    val roomName: String
) {
    data class Creator(
        @SerializedName("id")
        val id: String,
        @SerializedName("manittoUserId")
        val manittoUserId: String?,
        @SerializedName("username")
        val username: String
    )

    data class Member(
        @SerializedName("manitto")
        val manitto: Manitto?,
        @SerializedName("santa")
        val santa: Santa
    ) {
        data class Manitto(
            @SerializedName("id")
            val id: String?,
            @SerializedName("username")
            val username: String?
        )

        data class Santa(
            @SerializedName("id")
            val id: String?,
            @SerializedName("username")
            val username: String?
        )
    }

    data class Mission(
        @SerializedName("content")
        val content: String?,
        @SerializedName("id")
        val id: String?
    )
}

fun MyManittoModel.getRoomState(): RoomState {
    return when {
        // 삭제된 방
        deletedByCreatorDate != null -> RoomState.DELETED
        // 진행중인 방 (매칭됨 && 만료되지 않음)
        matchingDate != null && expirationDate != null &&
                !TimeUtil.isExpired(expirationDate) -> RoomState.IN_PROGRESS
        // 대기중인 방 (매칭 안됨 && 만료되지 않음)
        matchingDate == null && expirationDate != null &&
                !TimeUtil.isExpired(expirationDate) -> RoomState.WAITING
        // 종료된 방 (매칭됨 && 만료됨)
        matchingDate != null && expirationDate != null &&
                TimeUtil.isExpired(expirationDate) -> RoomState.FINISHED
        // 만료된 방 (매칭 안됨 && 만료됨)
        matchingDate == null && expirationDate != null &&
                TimeUtil.isExpired(expirationDate) -> RoomState.EXPIRED

        else -> RoomState.LEFT
    }
}

fun ManittoRoomModel.toMyManittoModel(): MyManittoModel =
    MyManittoModel(
        createdAt = this.createdAt,
        creator = MyManittoModel.Creator(
            id = this.creator.userId,
            manittoUserId = this.creator.manittoUserId,
            username = this.creator.userName
        ),
        deletedByCreatorDate = this.deletedByCreatorDate,
        expirationDate = this.expirationDate,
        roomId = this.roomId,
        invitationCode = this.invitationCode,
        matchingDate = this.matchingDate,
        members = this.members.map { member ->
            MyManittoModel.Member(
                manitto = MyManittoModel.Member.Manitto(
                    id = member.manitto.userId,
                    username = member.manitto.userName
                ),
                santa = MyManittoModel.Member.Santa(
                    id = member.santa.userId,
                    username = member.santa.userName
                )
            )
        },
        missions = this.missions.map { mission ->
            MyManittoModel.Mission(
                content = mission.content,
                id = mission.missionId
            )
        },
        roomName = this.roomName
    )