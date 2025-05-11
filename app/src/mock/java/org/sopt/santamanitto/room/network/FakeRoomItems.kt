package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.data.toMyManittoModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomCreator
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomMission

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
                ManittoRoomMission("4", "Fake Mission 4")
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

            else -> null
        }

    fun getMyManittoList(): List<MyManittoModel> = listOf(
        getFakeManittoRoomData("1").toMyManittoModel(),
        getFakeManittoRoomData("2").toMyManittoModel(),
        getFakeManittoRoomData("3").toMyManittoModel(),
        getFakeManittoRoomData("4").toMyManittoModel(),
    )
}