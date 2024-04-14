package org.sopt.santamanitto.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ViewDataBinding

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= BACK_PRESSED_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    Snackbar.make(
                        binding.root,
                        getString(R.string.main_back_pressed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        const val BACK_PRESSED_INTERVAL = 2000
    }
}