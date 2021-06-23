package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaBackgroundBinding


class SantaBackground @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_LENGTH_PER_A_LINE_TITLE = 10
    }

    private val binding = DataBindingUtil.inflate<SantaBackgroundBinding>(
        LayoutInflater.from(context),
        R.layout.santa_background,
        this, true
    )

    private val logo = binding.imageviewSantabackgroundLogo

    private val bigTitleTextView = binding.textviewSantabackground

    private val snowflake = binding.imageviewSantabackgroundSnow

    private val santaHead = binding.imageviewSantabackgroundSantahead

    private val santa = binding.imageviewSantabackgroundSanta

    private val rudolf = binding.imageviewSantabackgroundRudolf

    private val snowMan = binding.imageviewSantabackgroundSnowman

    private val backButton = binding.imagebuttonSantabackgroundBack

    private val titleTextView = binding.textviewSantabackgroundTitle

    private val descriptionTextView = binding.textviewSantabackgroundDescription

    var isBackKeyEnabled: Boolean
        get() = backButton.visibility == View.VISIBLE
        set(value) {
            if (value) {
                backButton.visibility = View.VISIBLE
            } else {
                backButton.visibility = View.GONE
            }
        }

    var bigTitle: String
        get() = bigTitleTextView.text.toString()
        set(value) {
            bigTitleTextView.text = value
        }

    var title: String
        get() = titleTextView.text.toString()
        set(value) {
            setVisible(titleTextView)
            titleTextView.text = if (value.length > MAX_LENGTH_PER_A_LINE_TITLE) {
                value.substring(0, MAX_LENGTH_PER_A_LINE_TITLE) + "\n" + value.substring(MAX_LENGTH_PER_A_LINE_TITLE)
            } else {
                value
            }
        }

    var description: String
        get() = descriptionTextView.text.toString()
        set(value) {
            setVisible(descriptionTextView)
            descriptionTextView.text = value
        }

    init {
        clipChildren = false
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SantaBackground,
            0, 0
        )

        if (typeArray.hasValue(R.styleable.SantaBackground_backKey)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_backKey, false)) {
                setVisible(backButton)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_backgroundLogo)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_backgroundLogo, false)) {
                setVisible(logo)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_snowflake)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_snowflake, false)) {
                setVisible(snowflake)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_santa)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_santa, false)) {
                setVisible(santa)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_santaHead)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_santaHead, false)) {
                setVisible(santaHead)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_rudolf)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_rudolf, false)) {
                setVisible(rudolf)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_snowMan)) {
            if (typeArray.getBoolean(R.styleable.SantaBackground_snowMan, false)) {
                setVisible(snowMan)
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_bigTitle)) {
            val str = typeArray.getString(R.styleable.SantaBackground_santa)
            if (!str.isNullOrEmpty()) {
                bigTitle = str
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_santaTitle)) {
            val str = typeArray.getString(R.styleable.SantaBackground_santaTitle)
            if (!str.isNullOrEmpty()) {
                title = str
            }
        }

        if (typeArray.hasValue(R.styleable.SantaBackground_santaDescription)) {
            val str = typeArray.getString(R.styleable.SantaBackground_santaDescription)
            if (!str.isNullOrEmpty()) {
                setVisible(descriptionTextView)
                description = str
            }
        }

        typeArray.recycle()
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun setOnBackKeyClickListener(listener: () -> Unit) {
        setVisible(backButton)
        backButton.setOnClickListener {
            listener()
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

    fun hideDescription() {
        descriptionTextView.visibility = View.GONE
    }
}