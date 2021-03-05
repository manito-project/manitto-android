package org.sopt.santamanitto.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaSwitchBinding

class SantaSwitch @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val ANIM_DURATION = 500L
    }

    private val binding = DataBindingUtil
            .inflate<SantaSwitchBinding>(
                    LayoutInflater.from(context),
                    R.layout.santa_switch,
                    this, true
            )

    //오른쪽 텍스트
    private val onTextView = binding.textviewSantaswitchOn

    //왼쪽 텍스트
    private val offTextView = binding.textviewSantaswitchOff

    //커서
    private val cursor = binding.santacardviewSantaswitchCursor

    private var _isOn = false
    var isOn: Boolean
        get() = _isOn
        set(value) {
            _isOn = value
            updateSwitchPosition()
        }

    private var onText = ""

    private var offText = ""

    private var switchChangedListener: ((Boolean) -> Unit)? = null

    init {
        clipChildren = false
        cursor.setRippleEffect(false)

        //속성 가져오기
        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaSwitch,
                0, 0)

        if (typeArray.hasValue(R.styleable.SantaSwitch_onText)) {
            onText = typeArray.getString(R.styleable.SantaSwitch_onText) ?: ""
        }

        if (typeArray.hasValue(R.styleable.SantaSwitch_offText)) {
            offText = typeArray.getString(R.styleable.SantaSwitch_offText) ?: ""
        }

        if (typeArray.hasValue(R.styleable.SantaSwitch_isOn)) {
            _isOn = typeArray.getBoolean(R.styleable.SantaSwitch_isOn, _isOn)
        }

        typeArray.recycle()

        //뷰 업데이트
        updateView()

        //클릭 리스너
        setOnClickListener {
            _isOn = !_isOn
            updateSwitchPosition()
            switchChangedListener?.let { it(_isOn) }
        }
    }

    //스위치 위치 이동
    private fun moveSwitch(position: Float) {
        ObjectAnimator.ofFloat(cursor, "translationX", position).apply {
            duration = ANIM_DURATION
            start()
        }
    }

    //스위치 켜기
    private fun moveToOn() {
        moveSwitch(width / 2 - getDimen(R.dimen.margin_santaswitch_cursor) * 2)
    }

    //스위치 끄기
    private fun moveToOff() {
        moveSwitch(0f)
    }

    //스위치 위치 갱신
    private fun updateSwitchPosition() {
        if (_isOn) {
            moveToOn()
        } else {
            moveToOff()
        }
    }

    //뷰 갱신
    private fun updateView() {
        onTextView.text = onText
        offTextView.text = offText
    }

    fun setOnSwitchChangedListener(listener: (Boolean) -> Unit) {
        this.switchChangedListener = listener
    }

    //뷰 크기가 초기화되고 나서야 스위치의 첫 위치를 지정할 수 있음
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateSwitchPosition()
    }
}