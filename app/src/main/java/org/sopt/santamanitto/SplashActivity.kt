package org.sopt.santamanitto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.ActivitySplashBinding
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.update.version.Version
import org.sopt.santamanitto.user.signin.SignInActivity
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    private val splashViewModel: SplashViewModel by viewModels()

    private var isDelayDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
                .setContentText(getString(R.string.update_dialog_content))
                .addHorizontalButton(getString(R.string.update_dialog_exit)) {
                    finish()
                }
                .addHorizontalButton(getString(R.string.update_dialog_update)) {
                    goToStore()
                }
                .enableCancel(false)
                .build()
                .show(supportFragmentManager, "update")
    }

    private fun goToStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                    "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            setPackage("com.android.vending")
        }
        startActivity(intent)
        finish()
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
            when (loginState) {
                SplashViewModel.LoginState.SUCCESS -> {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
                SplashViewModel.LoginState.FAIL -> {
                    startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                    finish()
                }
                else -> {
                    RoundDialogBuilder()
                        .setContentText(getString(R.string.splash_network_error_dialog), false)
                        .addHorizontalButton(getString(R.string.splash_network_error_dialog_button)) {
                                finish()
                            }
                            .build()
                            .show(supportFragmentManager, "error")
                }
            }
        }
    }
}