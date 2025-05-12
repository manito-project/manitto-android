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
    fun getFakeManittoRoomData(
        roomId: String = "1",
        createdAt: String = "2025-05-11T11:01:00.000Z",
        expirationDate: String = "2025-05-14T11:01:00.000Z",
        matchingDate: String? = null,
        deletedByCreatorDate: String? = null,
    ): ManittoRoomModel =
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
            missions = mutableListOf(
                ManittoRoomMission("1", "Fake Mission 1"),
                ManittoRoomMission("2", "Fake Mission 2"),
                ManittoRoomMission("3", "Fake Mission 3"),
                ManittoRoomMission("4", "Fake Mission 4"),
                ManittoRoomMission("5", "Fake Mission 5")
            ),
            members = mutableListOf(
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

    fun getMyManittoList(): List<MyManittoModel> = listOf(
        // 1. 삭제된 방: createdAt = 3일 전, expirationDate = 4일 뒤, deletedByCreatorDate = 어제
        getFakeManittoRoomData(
            roomId = "1",
            createdAt = TimeUtil.getDateWithOffsetFromNow(-3),
            expirationDate = TimeUtil.getDateWithOffsetFromNow(4),
            matchingDate = null,
            deletedByCreatorDate = TimeUtil.getDateWithOffsetFromNow(-1)
        ).toMyManittoModel(),

        // 2. 진행중인 방: createdAt = 3일 전, expirationDate = 4일 뒤, matchingDate = 어제
        getFakeManittoRoomData(
            roomId = "2",
            createdAt = TimeUtil.getDateWithOffsetFromNow(-3),
            expirationDate = TimeUtil.getDateWithOffsetFromNow(4),
            matchingDate = TimeUtil.getDateWithOffsetFromNow(-1),
            deletedByCreatorDate = null
        ).toMyManittoModel(),

        // 3. 대기중인 방: createdAt = 어제, expirationDate = 6일 뒤
        getFakeManittoRoomData(
            roomId = "3",
            createdAt = TimeUtil.getDateWithOffsetFromNow(-1),
            expirationDate = TimeUtil.getDateWithOffsetFromNow(6),
            matchingDate = null,
            deletedByCreatorDate = null
        ).toMyManittoModel(),

        // 4. 종료된 방: createdAt = 7일 전, expirationDate = 어제, matchingDate = 3일 전
        getFakeManittoRoomData(
            roomId = "4",
            createdAt = TimeUtil.getDateWithOffsetFromNow(-7),
            expirationDate = TimeUtil.getDateWithOffsetFromNow(-1),
            matchingDate = TimeUtil.getDateWithOffsetFromNow(-3),
            deletedByCreatorDate = null
        ).toMyManittoModel(),

        // 5. 만료된 방: createdAt = 7일 전, expirationDate = 어제
        getFakeManittoRoomData(
            roomId = "5",
            createdAt = TimeUtil.getDateWithOffsetFromNow(-7),
            expirationDate = TimeUtil.getDateWithOffsetFromNow(-1),
            matchingDate = null,
            deletedByCreatorDate = null
        ).toMyManittoModel()
    )
}