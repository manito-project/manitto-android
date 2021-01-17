package org.sopt.santamanitto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil.*
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.ActivitySplashBinding
import org.sopt.santamanitto.preference.UserPreferenceManager
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
            //Todo 메인 화면으로 이동
            Log.d("SplashActivity", "startNextActivity() : It has user name. ${userPreferenceManager.getUserName()}")
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}