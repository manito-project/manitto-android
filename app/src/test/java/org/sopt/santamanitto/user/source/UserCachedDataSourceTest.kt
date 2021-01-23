package org.sopt.santamanitto.user.source

import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.sopt.santamanitto.data.JoinedRoom
import org.sopt.santamanitto.util.capture
import org.sopt.santamanitto.util.eq

class UserCachedDataSourceTest {

    @Mock private lateinit var userRemoteDataSource: UserDataSource

    @Mock private lateinit var loginCallback: UserDataSource.LoginCallback

    @Mock private lateinit var createAccountCallback: UserDataSource.CreateAccountCallback

    @Mock private lateinit var getJoinedRoomsCallback: UserDataSource.GetJoinedRoomsCallback

    @Mock private lateinit var mockUser: User

    @Mock private lateinit var mockJoinedRooms: List<JoinedRoom>

    @Captor private lateinit var loginCaptor: ArgumentCaptor<UserDataSource.LoginCallback>

    @Captor private lateinit var createAccountCaptor: ArgumentCaptor<UserDataSource.CreateAccountCallback>

    @Captor private lateinit var getJoinedRoomsCaptor: ArgumentCaptor<UserDataSource.GetJoinedRoomsCallback>

    private lateinit var userCachedDataSource: UserCachedDataSource

    private val mockSerialNumber = "M1o2c3k5S67e8r9ialNumber"

    private val mockUserNamne = "mock"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userCachedDataSource = UserCachedDataSource(userRemoteDataSource)
    }

    @Test
    fun loginSuccessTest() {
        //로그인 시도
        userCachedDataSource.login(mockSerialNumber, loginCallback)

        //API 호출하는지 확인
        verify(userRemoteDataSource).login(eq(mockSerialNumber), capture(loginCaptor))

        //성공했을 때
        loginCaptor.value.onLoginSuccess(mockUser)

        //캐시가 null이 아닌지 확인
        assertNotNull(userCachedDataSource.cachedUser)

        //캐시에 든 값이 API가 반환한 값이 맞는지 확인
        assertThat(userCachedDataSource.cachedUser, `is`(mockUser))
    }

    @Test
    fun createAccountTest() {
        userCachedDataSource.createAccount(mockUserNamne, mockSerialNumber, createAccountCallback)

        verify(userRemoteDataSource).createAccount(eq(mockUserNamne), eq(mockSerialNumber), capture(createAccountCaptor))

        createAccountCaptor.value.onCreateAccountSuccess(mockUser)

        assertNotNull(userCachedDataSource.cachedUser)

        assertThat(userCachedDataSource.cachedUser, `is`(mockUser))
    }

    @Test
    fun getJoinedRoomTest() {
        //첫 번째 시도
        userCachedDataSource.getJoinedRoom(getJoinedRoomsCallback)

        verify(userRemoteDataSource).getJoinedRoom(capture(getJoinedRoomsCaptor))

        getJoinedRoomsCaptor.value.onJoinedRoomsLoaded(mockJoinedRooms)

        verify(getJoinedRoomsCallback).onJoinedRoomsLoaded(eq(mockJoinedRooms))

        //두 번째 시도는 캐시에서 가져오는가
        userRemoteDataSource.getJoinedRoom(getJoinedRoomsCallback)

        assertNotNull(userCachedDataSource.cachedJoinedRooms)

        verify(getJoinedRoomsCallback).onJoinedRoomsLoaded(eq(userCachedDataSource.cachedJoinedRooms!!))
    }
}