package org.sopt.santamanitto

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.*
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.ActivitySplashBinding
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.user.signin.SignInActivity

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    private val splashViewModel: SplashViewModel by viewModels()

    private var isDelayDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        splashViewModel.run {
            loginSuccess.observe(this@SplashActivity) {
                startNextActivity()
            }
            login()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            isDelayDone = true
            startNextActivity()
        }, SPLASH_DELAY)
    }

    private fun startNextActivity() {
        val loginState = splashViewModel.loginSuccess.value
        if (isDelayDone && loginState != SplashViewModel.LoginState.WAITING) {
            if (loginState == SplashViewModel.LoginState.SUCCESS) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else if (loginState == SplashViewModel.LoginState.FAIL) {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                finish()
            }
        }
    }
}