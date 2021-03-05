package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LiveCheckbox : AppCompatCheckBox {
    //@JvmOverloads 로는 터치가 안먹음
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val _isChecked: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()
    val isCheckedLiveData: LiveData<Boolean>
        get() = _isChecked!!

    init {
        //라이브데이터가 늦게 생성되기 때문에 여기서 값 초기화
        _isChecked?.value = isChecked
    }

    //체크될 때마다 라이브데이터 값도 갱신
    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        //attrs로 값 초기화 시 아직 라이브데이터가 생성되지 않을 수 있음
        _isChecked?.value = checked
    }
}