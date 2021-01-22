package org.sopt.santamanitto.user.source

import junit.framework.TestCase
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.sopt.santamanitto.preference.UserPreferenceManager
import org.sopt.santamanitto.user.User
import org.sopt.santamanitto.util.TimeUtil

class UserLocalDataSourceTest : TestCase() {

    private lateinit var userLocalDataSource: UserLocalDataSource

    @Mock private lateinit var mockUserPreferenceManager: UserPreferenceManager

    private lateinit var mockUser: User

    @Mock private lateinit var mockGetUserCallback: UserDataSource.GetUserCallback

    @Mock private lateinit var mockGetUserIdCallback: UserDataSource.GetUserIdCallback

    @Mock private lateinit var mockSaveUserCallback: UserDataSource.SaveUserCallback

    public override fun setUp() {
        super.setUp()

        MockitoAnnotations.initMocks(this)

        mockUser = User("mockUser", "100")

        userLocalDataSource = UserLocalDataSource(mockUserPreferenceManager)
    }


    fun testGetUser() {
        userLocalDataSource.getUser(mockGetUserCallback)

        verify(mockUserPreferenceManager).getUserId()
        verify(mockUserPreferenceManager).getUserName()
        verify(mockUserPreferenceManager).getSerialNumber()
        verify(mockUserPreferenceManager).getUserUpdateTime()
        verify(mockUserPreferenceManager).getUserCreateTime()
        verify(mockUserPreferenceManager).getAccessToken()
    }

    fun testGetUserId() {
        userLocalDataSource.getUserId(mockGetUserIdCallback)

        verify(mockUserPreferenceManager, only()).getUserId()
    }

    fun testSaveUser() {
        userLocalDataSource.saveUser(mockUser, mockSaveUserCallback)

        verify(mockUserPreferenceManager).setUserId(anyInt())
        verify(mockUserPreferenceManager).setUserName(anyString())
        verify(mockUserPreferenceManager).setSerialNumber(anyString())
        verify(mockUserPreferenceManager).setUserUpdateTime(anyString())
        verify(mockUserPreferenceManager).setUserCreateTime(anyString())
        verify(mockUserPreferenceManager).setAccessToken(anyString())
    }

    fun testSaveUserName() {
        userLocalDataSource.saveUser("mockName", mockSaveUserCallback)

        verify(mockUserPreferenceManager, only()).setUserName(anyString())
    }
}