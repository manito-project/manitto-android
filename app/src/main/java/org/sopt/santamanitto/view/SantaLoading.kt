package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaLoadingBinding

class SantaLoading @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = DataBindingUtil.inflate<SantaLoadingBinding>(
            LayoutInflater.from(context),
            R.layout.santa_loading,
            this, true
    )

    private val progressbar = binding.progressbarSantaloading

    private val textView = binding.textviewSantaloading

    fun setDataNotAvailable() {
        progressbar.visibility = View.GONE
        textView.visibility = View.VISIBLE
    }
}