package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaPeriodPickerBinding

class SantaPeriodPicker @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_PERIOD = 7
        private const val MINIMUM_PERIOD = 3
        private const val MAXIMUM_PERIOD = 14
    }

    private val binding = DataBindingUtil.inflate<SantaPeriodPickerBinding>(
            LayoutInflater.from(context),
            R.layout.santa_period_picker,
            this, true
    )

    private val minusButton = binding.viewSantaperiodpickerMinusarea

    private val plusButton = binding.viewSantaperiodpickerPlusarea

    private val text = binding.textviewSantaperiodpickerText

    private var _period = DEFAULT_PERIOD

    private var periodChangedListener: ((period: Int) -> Unit)? = null

    init {
        updateTextView()
        minusButton.setOnClickListener {
            period--
        }
        plusButton.setOnClickListener {
            period++

        }
    }

    var period: Int
        get() = _period
        set(value) {
            if (value in MINIMUM_PERIOD..MAXIMUM_PERIOD) {
                _period = value
                updateTextView()
                periodChangedListener?.let { it(period) }
            }
        }

    private fun updateTextView() {
        text.text = String.format(context.getString(R.string.santaperiodpicker_period), _period)
    }

    fun setOnPeriodChangedListener(listener: (Int) -> Unit) {
        this.periodChangedListener = listener
    }
}