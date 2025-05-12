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
    // 날짜 오프셋 값을 보관할 데이터 클래스
    private data class FakeRoomDates(
        val createdOffsetDays: Int,
        val expirationOffsetDays: Int,
        val matchingOffsetDays: Int?,
        val deletedOffsetDays: Int?
    )

    // roomId별 날짜 오프셋 매핑
    private val fakeRoomDatesMap = mapOf(
        // 1. 삭제된 방
        "1" to FakeRoomDates(-3, 4, null, -1),
        // 2. 진행중인 방
        "2" to FakeRoomDates(-3, 4, -1, null),
        // 3. 대기중인 방
        "3" to FakeRoomDates(-1, 6, null, null),
        // 4. 종료된 방
        "4" to FakeRoomDates(-7, -1, -3, null),
        // 5. 만료된 방
        "5" to FakeRoomDates(-7, -1, null, null)
    )

    // 날짜 오프셋과 모델 데이터를 조합해 미리 생성해두는 맵
    private val fakeRoomModelMap: Map<String, ManittoRoomModel> =
        fakeRoomDatesMap.mapValues { (roomId, dates) ->
            val createdAt = TimeUtil.getDateWithOffsetFromNow(dates.createdOffsetDays)
            val expirationDate = TimeUtil.getDateWithOffsetFromNow(dates.expirationOffsetDays)
            val matchingDate =
                dates.matchingOffsetDays?.let { TimeUtil.getDateWithOffsetFromNow(it) }
            val deletedByCreatorDate =
                dates.deletedOffsetDays?.let { TimeUtil.getDateWithOffsetFromNow(it) }

            ManittoRoomModel(
                roomId = roomId,
                roomName = "FakeRoom $roomId",
                invitationCode = "oU3lsEo-",
                createdAt = createdAt,
                expirationDate = expirationDate,
                matchingDate = matchingDate,
                deletedByCreatorDate = deletedByCreatorDate,
                creator = ManittoRoomCreator(
                    userId = "1",
                    userName = "FakeFirstUser",
                    manittoUserId = "12fsfe2"
                ),
                missions = listOf(
                    ManittoRoomMission("1", "Fake Mission 1"),
                    ManittoRoomMission("2", "Fake Mission 2"),
                    ManittoRoomMission("3", "Fake Mission 3"),
                    ManittoRoomMission("4", "Fake Mission 4"),
                    ManittoRoomMission("5", "Fake Mission 5")
                ),
                members = listOf(
                    ManittoRoomMember(
                        santa = ManittoRoomMember.SantaRoomInfo("1", "FakeFirstUser", "1"),
                        manitto = ManittoRoomMember.ManittoRoomInfo("2", "FakeSecondUser")
                    ),
                    ManittoRoomMember(
                        santa = ManittoRoomMember.SantaRoomInfo("2", "FakeSecondUser", "1"),
                        manitto = ManittoRoomMember.ManittoRoomInfo("3", "FakeThirdUser")
                    ),
                    ManittoRoomMember(
                        santa = ManittoRoomMember.SantaRoomInfo("3", "FakeThirdUser", "1"),
                        manitto = ManittoRoomMember.ManittoRoomInfo("4", "FakeFourthUser")
                    ),
                    ManittoRoomMember(
                        santa = ManittoRoomMember.SantaRoomInfo("4", "FakeFourthUser", "1"),
                        manitto = ManittoRoomMember.ManittoRoomInfo("5", "FakeFifthUser")
                    ),
                    ManittoRoomMember(
                        santa = ManittoRoomMember.SantaRoomInfo("5", "FakeFifthUser", "1"),
                        manitto = ManittoRoomMember.ManittoRoomInfo("1", "FakeFirstUser")
                    )
                )
            )
        }

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
        when (roomId) {
            "1" -> PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("1", "FakeFirstUser"),
                mission = MyManittoModel.Mission("Fake Mission 1", "1")
            )

            "2" -> PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("2", "FakeSecondUser"),
                mission = MyManittoModel.Mission("Fake Mission 2", "2")
            )

            "3" -> PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("3", "FakeThirdUser"),
                mission = MyManittoModel.Mission("Fake Mission 3", "3")
            )

            "4" -> PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("4", "FakeFourthUser"),
                mission = MyManittoModel.Mission("Fake Mission 4", "4")
            )

            "5" -> PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("5", "FakeFifthUser"),
                mission = MyManittoModel.Mission("Fake Mission 4", "5")
            )

            else -> null
        }
}