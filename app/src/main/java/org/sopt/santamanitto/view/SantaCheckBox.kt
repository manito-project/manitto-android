package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaCheckBoxBinding

class SantaCheckBox @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        @BindingAdapter("setChecked")
        @JvmStatic
        fun setChecked(view: SantaCheckBox, isChecked: Boolean) {
            if (view.isChecked != isChecked) {
                view.isChecked = isChecked
            }
        }

        @InverseBindingAdapter(attribute = "setChecked", event = "checkedAttrChanged")
        @JvmStatic
        fun getChecked(view: SantaCheckBox) : Boolean {
            return view.findViewById<CheckBox>(R.id.checkbox_santacheckbox).isChecked
        }

        @BindingAdapter("checkedAttrChanged")
        @JvmStatic
        fun setListener(view: SantaCheckBox, listener: InverseBindingListener) {
            val checkBox = view.findViewById<CheckBox>(R.id.checkbox_santacheckbox)
            checkBox.setOnCheckedChangeListener { _, _ ->
                listener.onChange()
            }
        }
    }

    private val binding = DataBindingUtil.inflate<SantaCheckBoxBinding>(
            LayoutInflater.from(context),
            R.layout.santa_check_box,
            this, true
    )

    private val checkBox = binding.checkboxSantacheckbox

    private val button = binding.buttonSantacheckbox

    private val childList: MutableList<SantaCheckBox> by lazy { mutableListOf() }

    private var hasChild = false

    private var childCheckCount = 0

    //체크 여부를 갖고 있는 라이브데이터
    val isCheckedLiveData: LiveData<Boolean>
        get() = checkBox.isCheckedLiveData

    var isChecked: Boolean
        get() = checkBox.isChecked
        set(value) {
            checkBox.isChecked = value
        }

    var text: String
        get() = button.text.toString()
        set(value) {
            button.text = value
        }

    init {
        //사용자 속성
        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaCheckBox,
                0, 0)

        //버튼 문구
        if (typeArray.hasValue(R.styleable.SantaCheckBox_text)) {
            text = typeArray.getString(R.styleable.SantaCheckBox_text) ?: ""
        }

        //체크 여부
        if (typeArray.hasValue(R.styleable.SantaCheckBox_isChecked)) {
            isChecked = typeArray.getBoolean(R.styleable.SantaCheckBox_isChecked, false)
        }

        typeArray.recycle()
    }

    fun addChildSantaCheckBox(child: SantaCheckBox) {
        childList.add(child)
        child.setOnCheckedChangedListener {
            if (it) {
                childCheckCount++
            } else {
                childCheckCount--
            }
            val allAgree = childCheckCount == childList.size
            if (allAgree) {
                if (!isChecked) {
                    isChecked = true
                }
            } else {
                if (isChecked) {
                    isChecked = false
                }
            }
        }
        if (!hasChild) {
            checkBox.setOnClickListener {
                val c = isChecked
                for (_child in childList) {
                    if (_child.isChecked != c) {
                        _child.isChecked = c
                    }
                }
            }
        }
        hasChild = true
    }

    fun setOnCheckedChangedListener(listener: (isChecked: Boolean) -> Unit) {
        binding.checkboxSantacheckbox.setOnCheckedChangeListener { _, isChecked ->
            listener(isChecked)
        }
    }

    //클릭 리스너는 버튼으로 위임
    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }
}