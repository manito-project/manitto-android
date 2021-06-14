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
import org.sopt.santamanitto.dialog.RoundDialogBuilder
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.update.version.Version
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
            latestVersion.observe(this@SplashActivity) { latestVersion ->
                if (latestVersion == null) {
                    return@observe
                }
                if (latestVersion.compare(BuildConfig.VERSION_NAME, Version.MAJOR) > 0) {
                    showUpdateDialog()
                } else {
                    tryLogin()
                }
            }

            versionCheckFail.observe(this@SplashActivity) {
                if (it) {
                    // 버전 체크에 실패하더라도 로그인 시도는 해본다. (API 안정성이 보장되지 않음)
                    tryLogin()
                }
            }

            loginSuccess.observe(this@SplashActivity) {
                startNextActivity()
            }

            checkUpdate()
        }
    }

    private fun showUpdateDialog() {
        RoundDialogBuilder()
            .setContentText("원활한 산타마니또를 위해 업데이트를 해야해~!")
            .addHorizontalButton("마니또 종료") {
                finish()
            }
            .addHorizontalButton("업데이트") {
                //Todo: 마켓으로 이동
            }
            .enableCancel(false)
            .build()
            .show(supportFragmentManager, "update")
    }

    private fun tryLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            isDelayDone = true
            startNextActivity()
        }, SPLASH_DELAY)

        splashViewModel.login()
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