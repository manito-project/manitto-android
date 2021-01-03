package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaImageRoundButtonBinding

class SantaImageRoundButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: SantaImageRoundButtonBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.santa_image_round_button,
            this, true
    )

    //터치 영역이 되는 카드뷰
    private val cardView = binding.cardviewSantaimageroundbutton

    //타이틀
    private val titleTextView = binding.textviewSantaimageroundbuttonTitle

    //설명
    private val descriptionTextView = binding.textviewSantaimageroundbuttonDescription

    var title: String
        get() = titleTextView.text.toString()
        set(value) {
            titleTextView.text = value
        }

    var description: String
        get() = descriptionTextView.text.toString()
        set(value) {
            descriptionTextView.text = value
        }

    init {
        //카드뷰 그림자 잘림 방지
        this.clipChildren = false

        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaImageRoundButton,
                0, 0)

        //타이틀 속성 적용
        if (typeArray.hasValue(R.styleable.SantaImageRoundButton_title)) {
            title = typeArray.getString(R.styleable.SantaImageRoundButton_title)?: ""
        }

        //설명 속성 적용
        if (typeArray.hasValue(R.styleable.SantaImageRoundButton_description)) {
            description = typeArray.getString(R.styleable.SantaImageRoundButton_description)?: ""
        }
    }

    //클릭 리스너는 카드뷰에 전달
    override fun setOnClickListener(l: OnClickListener?) {
        cardView.setOnClickListener(l)
    }
}