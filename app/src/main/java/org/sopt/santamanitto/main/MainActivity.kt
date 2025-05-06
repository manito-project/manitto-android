package org.sopt.santamanitto.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeAds()

        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private fun initializeAds() {
        MobileAds.initialize(this)
    }
}
