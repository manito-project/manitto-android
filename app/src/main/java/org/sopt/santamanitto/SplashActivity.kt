package org.sopt.santamanitto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil.*
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.ActivitySplashBinding
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.preference.UserPreferenceManager
import org.sopt.santamanitto.signin.fragment.SignInActivity
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startNextActivity()
        }, SPLASH_DELAY)
    }

    private fun startNextActivity() {
        val hasUserName = userPreferenceManager.getUserName() != null
        if (hasUserName) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        finish()
    }
}