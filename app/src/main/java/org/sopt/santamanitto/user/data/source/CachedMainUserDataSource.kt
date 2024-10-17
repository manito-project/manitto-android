package org.sopt.santamanitto.user.data.source

//class CachedMainUserDataSource @Inject constructor(
//    private val userMetadataSource: UserMetadataSource,
//    private val userAuthController: UserAuthController
//) : MainUserDataSource {
//
//    var isMyManittoDirty = true
//
//    private var myManittoModels: List<MyManittoModel>? = null
//
//    override suspend fun getMyManittoList(): List<MyManittoModel> {
//        if (isMyManittoDirty || myManittoModels == null) {
//            val rooms =
//
//                userAuthController.getUserInfo(
//                    userMetadataSource.getUserId(),
//                    object : UserAuthController.GetUserInfoCallback {
//                        override fun onUserInfoLoaded(userInfoModel: UserInfoModel) {
//                            isMyManittoDirty = false
////                        myManittoModels = userInfoModel.myManittoModels.reversed()
//                            callback.onMyManittoListLoaded(myManittoModels!!)
//                        }
//
//                        override fun onDataNotAvailable() {
//                            callback.onDataNotAvailable()
//                        }
//                    })
//        } else {
//            callback.onMyManittoListLoaded(myManittoModels!!)
//        }
//    }
//}