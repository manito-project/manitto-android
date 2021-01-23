package org.sopt.santamanitto

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.*
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.ActivitySplashBinding
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.signin.fragment.SignInActivity
import org.sopt.santamanitto.user.source.User
import org.sopt.santamanitto.user.source.UserDataSource
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    @Inject
    @Named("cached")
    lateinit var userCachedDataSource: UserDataSource

    @Inject
    @Named("serialNumber")
    lateinit var serialNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startNextActivity()
        }, SPLASH_DELAY)
    }

    private fun startNextActivity() {
        userCachedDataSource.login(serialNumber, object: UserDataSource.LoginCallback {
            override fun onLoginSuccess(user: User) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onLoginFailed() {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                finish()
            }
        })
    }
}