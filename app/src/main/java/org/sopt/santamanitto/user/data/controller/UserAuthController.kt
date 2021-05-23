package org.sopt.santamanitto.user.data.controller

interface UserAuthController {
    fun changeUserName(userId: Int, newName: String, callback: (isSuccess: Boolean) -> Unit)
}