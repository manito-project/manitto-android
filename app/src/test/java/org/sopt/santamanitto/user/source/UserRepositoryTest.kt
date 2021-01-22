package org.sopt.santamanitto.user.source

import android.provider.Settings
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.nullable
import org.mockito.Mockito.verify
import org.sopt.santamanitto.user.User
import org.sopt.santamanitto.util.TimeUtil
import org.sopt.santamanitto.util.any
import org.sopt.santamanitto.util.capture
import org.sopt.santamanitto.util.eq

class UserRepositoryTest {

    private val mockUser = User("mockUserName", 100, Settings.Secure.ANDROID_ID,
        "fake : ${TimeUtil.getCurrentTime()}",
        "fake : ${TimeUtil.getCurrentTime()}",
        "FAKExxYxMDg5MjgxNCwiaXNzIOdVrK0"
    )

    private val userFromServiceAPI = User(mockUser.userName, 500, mockUser.serialNumber,
        "remote : ${TimeUtil.getCurrentTime()}", "remote : ${TimeUtil.getCurrentTime()}",
        "remote Access Token")

    private lateinit var userRepository: UserRepository

    @Mock private lateinit var getUserCallback: UserDataSource.GetUserCallback

    @Mock private lateinit var getUserIdCallback: UserDataSource.GetUserIdCallback

    @Mock private lateinit var saveUserCallback: UserDataSource.SaveUserCallback

    @Mock private lateinit var userLocalDataSource: UserLocalDataSource

    @Mock private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Captor private lateinit var userCallbackCaptor: ArgumentCaptor<UserDataSource.GetUserCallback>

    @Captor private lateinit var userIdCallbackCaptor: ArgumentCaptor<UserDataSource.GetUserIdCallback>

    @Captor private lateinit var userSaveCallbackCaptor: ArgumentCaptor<UserDataSource.SaveUserCallback>

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepository(userLocalDataSource, userRemoteDataSource)
    }

    @Test
    fun getUser_repositoryCachesAfterFirstApiCall() {
        getUserCallTwiceToRepository(getUserCallback)

        verify(userRemoteDataSource).getUser(any<UserDataSource.GetUserCallback>())
    }

    @Test
    fun getUser_requestUserFromLocalDataSource() {
        userRepository.getUser(getUserCallback)

        verify(userLocalDataSource).getUser(any<UserDataSource.GetUserCallback>())
    }

    @Test
    fun getUserId_requestFromLocalDataSource() {
        userRepository.getUserId(getUserIdCallback)

        verify(userLocalDataSource).getUserId(any<UserDataSource.GetUserIdCallback>())
    }

    @Test
    fun getUserId_requestFromCache() {
        userRepository.cachedUser = userFromServiceAPI

        userRepository.getUserId(getUserIdCallback)

        verify(getUserIdCallback).onUserIdLoaded(eq(userFromServiceAPI.id))

        userRepository.cachedUser = null
    }

    @Test
    fun getUserId_requestFromRemoteDataSource() {
        //유저 아이디 요청
        userRepository.getUserId(getUserIdCallback)

        //로컬에서 확인
        verify(userLocalDataSource).getUserId(capture(userIdCallbackCaptor))

        //로컬에 없을 때
        userIdCallbackCaptor.value.onDataNotAvailable()

        //서버에서 찾음
        verify(userRemoteDataSource).getUser(capture(userCallbackCaptor))

        //서버가 찾음
        userCallbackCaptor.value.onUserLoaded(userFromServiceAPI)

        //로컬에 저장하는지 확인
        verify(userLocalDataSource).saveUser(eq(userFromServiceAPI), nullable(UserDataSource.SaveUserCallback::class.java))

        //처음 콜백으로 서버에서 준 아이디를 반환하는지 확인
        verify(getUserIdCallback).onUserIdLoaded(eq(userFromServiceAPI.id))
    }

    @Test
    fun saveUser_savesUserToServiceAPI() {
        //유저 정보 저장 시
        userRepository.saveUser(mockUser, saveUserCallback)

        //서버에 먼저 저장함
        verify<UserDataSource>(userRemoteDataSource).saveUser(eq(mockUser), capture(userSaveCallbackCaptor))

        //서버에서 이름과 시리얼 번호를 제외한 나머지 정보를 갱신해서 돌려줌
        userSaveCallbackCaptor.value.onUserSaved(userFromServiceAPI)

        //서버에서 돌려준 값이 로컬에 들어가는지 확인
        verify<UserDataSource>(userLocalDataSource).saveUser(eq(userFromServiceAPI), nullable(UserDataSource.SaveUserCallback::class.java))

        //캐시 되었는지 확인
        assertNotNull(userRepository.cachedUser)
    }

    private fun getUserCallTwiceToRepository(callback: UserDataSource.GetUserCallback) {
        //첫 API 호출
        userRepository.getUser(callback)

        //로컬에서 찾음
        verify(userLocalDataSource).getUser(capture(userCallbackCaptor))

        //로컬엔 아직 데이터 없음
        userCallbackCaptor.value.onDataNotAvailable()

        //리모트에서 찾음
        verify(userRemoteDataSource).getUser(capture(userCallbackCaptor))

        //리모트에서 찾아 콜백 트리거
        userCallbackCaptor.value.onUserLoaded(mockUser)

        //두 번째 API 호출
        userRepository.getUser(callback)
    }
}