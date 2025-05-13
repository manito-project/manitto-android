package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.data.toMyManittoModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomCreator
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomMission
import org.sopt.santamanitto.util.TimeUtil

object FakeRoomItems {

    /**
     * 입력된 인자를 통해 ManittoRoomModel을 반환하는 빌더
     *
     * @param roomId 방 식별자
     * @param createdOffsetDays 생성일을 현재로부터 offset만큼 이동한 일자
     * @param expirationOffsetDays 만료일을 현재로부터 offset만큼 이동한 일자
     * @param matchingOffsetDays 매칭일을 현재로부터 offset만큼 이동한 일자; null이면 매칭되지 않음
     * @param deletedOffsetDays 삭제일을 현재로부터 offset만큼 이동한 일자; null이면 삭제되지 않음
     * @param missionCount 생성할 미션 개수
     * @param memberCount 생성할 멤버 개수
     * @param invitationCode 방 초대 코드
     * @return 생성된 ManittoRoomModel 객체
     */
    private fun buildFakeManittoRoomModel(
        roomId: String,
        createdOffsetDays: Int,
        expirationOffsetDays: Int,
        matchingOffsetDays: Int?,
        deletedOffsetDays: Int?,
        missionCount: Int = 5,
        memberCount: Int = 5,
        invitationCode: String = "oU3lsEo-",
    ): ManittoRoomModel {
        val createdDate = TimeUtil.getDateWithOffsetFromNow(createdOffsetDays)
        val expirationDate = TimeUtil.getDateWithOffsetFromNow(expirationOffsetDays)
        val matchingDate = matchingOffsetDays?.let { TimeUtil.getDateWithOffsetFromNow(it) }
        val deletedDate = deletedOffsetDays?.let { TimeUtil.getDateWithOffsetFromNow(it) }

        return ManittoRoomModel(
            roomId = roomId,
            roomName = "FakeRoom $roomId",
            invitationCode = invitationCode,
            createdAt = createdDate,
            expirationDate = expirationDate,
            matchingDate = matchingDate,
            deletedByCreatorDate = deletedDate,
            creator = ManittoRoomCreator("1", "FakeFirstUser", "12fsfe2"),
            missions = List(missionCount) { index ->
                ManittoRoomMission(index.toString(), "Fake Mission $index")
            },
            members = List(memberCount) { index ->
                val santaId = (index + 1).toString()
                val manittoId = ((index + 1) % memberCount + 1).toString()
                ManittoRoomMember(
                    santa = ManittoRoomMember.SantaRoomInfo(
                        userId = santaId,
                        userName = "Fake User $santaId",
                        missionId = santaId
                    ),
                    manitto = ManittoRoomMember.ManittoRoomInfo(
                        userId = manittoId,
                        userName = "Fake User $manittoId"
                    )
                )
            }
        )
    }

    // 방의 5가지 상태에 해당하는 가짜 객체 매핑
    private val fakeRoomModelMap: Map<String, ManittoRoomModel> = mapOf(
        // 1. 삭제된 방
        "1" to buildFakeManittoRoomModel("1", -3, 4, null, -1),
        // 2. 진행중인 방
        "2" to buildFakeManittoRoomModel("2", -3, 4, -1, null),
        // 3. 대기중인 방
        "3" to buildFakeManittoRoomModel("3", -1, 6, null, null),
        // 4. 종료된 방
        "4" to buildFakeManittoRoomModel("4", -7, -1, -3, null),
        // 5. 만료된 방
        "5" to buildFakeManittoRoomModel("5", -7, -1, null, null)
    )

    /**
     * roomId로 미리 생성된 방 데이터를 반환 (없으면 null)
     */
    fun getFakeManittoRoomData(roomId: String): ManittoRoomModel? =
        fakeRoomModelMap[roomId]

    /**
     * 미리 생성된 모든 방 데이터를 반환
     */
    fun getMyManittoList(): List<MyManittoModel> =
        fakeRoomModelMap.values.map { it.toMyManittoModel() }

    /**
     * roomId에 따라서 매칭된 결과 값을 반환 (없으면 null)
     */
    fun getFakePersonalRoomInfo(roomId: String): PersonalRoomModel? =
        fakeRoomModelMap[roomId]?.let { room ->
            val mission = room.missions.firstOrNull() ?: return null
            val member = room.members.firstOrNull() ?: return null
            PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto(
                    id = member.santa.userId,
                    username = member.santa.userName
                ),
                mission = MyManittoModel.Mission(
                    content = mission.content,
                    id = mission.missionId
                )
            )
        }
}
