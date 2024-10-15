package org.sopt.santamanitto.room.data


import com.google.gson.annotations.SerializedName

data class TempMyManittoModel(
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