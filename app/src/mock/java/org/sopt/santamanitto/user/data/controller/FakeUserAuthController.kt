package org.sopt.santamanitto.user.data.controller

class FakeUserAuthController: UserAuthController {
    override fun changeUserName(
        userId: Int,
        newName: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        callback.invoke(newName != "fail")
    }
}