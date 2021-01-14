package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaBackgroundBinding
import org.sopt.santamanitto.getDimen


class SantaBackground @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val WITH_LOGO_MODE = 0
        private const val LOGO_NO_TEXT = 1
        private const val NO_LOGO = 2
        private const val ONLY_CHARACTER = 3
    }

    private val binding = DataBindingUtil.inflate<SantaBackgroundBinding>(
        LayoutInflater.from(context),
        R.layout.santa_background,
        this, true
    )

    private val logo = binding.imageviewSantabackgroundLogo

    private val textView = binding.textviewSantabackground

    private val snowImage = binding.imageviewSantabackgroundSnow

    private val santaHead = binding.imageviewSantabackgroundSantahead

    private val santa = binding.imageviewSantabackgroundSanta

    private val rudolfAndSnowMan = binding.groupSantabackgroundRudolfnsnowman

    private val backButton = binding.imagebuttonSantabackgroundBack

    private var backgroundStyle = 0

    var isBackKeyEnabled: Boolean
        get() = backButton.visibility == View.VISIBLE
        set(value) {
            if (value) {
                backButton.visibility = View.VISIBLE
            } else {
                backButton.visibility = View.GONE
            }
        }

    var text: String
        get() = textView.text.toString()
        set(value) {
            textView.text = value
        }

    init {
        clipChildren = false
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SantaBackground,
            0, 0
        )

        if (typeArray.hasValue(R.styleable.SantaBackground_backgroundStyle)) {
            backgroundStyle =
                typeArray.getInt(R.styleable.SantaBackground_backgroundStyle, WITH_LOGO_MODE)
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_backgroundText)) {
            text = typeArray.getString(R.styleable.SantaBackground_backgroundText) ?: ""
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_backKey)) {
            isBackKeyEnabled = typeArray.getBoolean(R.styleable.SantaBackground_backKey, false)
        }

        typeArray.recycle()

        updateView()
    }

    fun setOnBackKeyClickListener(listener: () -> Unit) {
        backButton.setOnClickListener {
            listener()
        }
    }

    private fun updateView() {
        when (backgroundStyle) {
            LOGO_NO_TEXT -> {
                textView.visibility = View.GONE
                santa.visibility = View.GONE
                santaHead.visibility = View.GONE
                rudolfAndSnowMan.visibility = View.GONE
            }

            NO_LOGO -> {
                textView.visibility = View.GONE
                logo.visibility = View.GONE
                snowImage.visibility = View.GONE
                santaHead.visibility = View.GONE
                rudolfAndSnowMan.visibility = View.GONE
            }

            ONLY_CHARACTER -> {
                logo.visibility = View.GONE
                textView.visibility = View.GONE
                santaHead.visibility = View.GONE
                snowImage.visibility = View.GONE
            }

            else -> {
                santa.visibility = View.GONE
                rudolfAndSnowMan.visibility = View.GONE
            }
        }
    }

    //실제 뷰 크기가 측정되었을 때 특정 길이보다 길면 거기에 맞춤
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        layoutParams = layoutParams.apply {
            height =
                heightMeasureSpec.coerceAtMost(getDimen(R.dimen.height_santabackground_max).toInt())
        }
    }
}