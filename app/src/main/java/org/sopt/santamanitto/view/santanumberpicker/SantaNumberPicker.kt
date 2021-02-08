package org.sopt.santamanitto.view.santanumberpicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.view.SantaEditText
import org.sopt.santamanitto.view.setTextColorById
import kotlin.math.abs

class SantaNumberPicker @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_START_NUMBER = 0
        private const val DEFAULT_END_NUMBER = 10
        // It must be >= 3 and odd number
        private const val DEFAULT_NUMBER_OF_MAX_SHOWING_NUMBER = 5
        private const val DEFAULT_CENTER_POSITION = 0
        private const val DEFAULT_CENTER_NUMBER_SIZE = 26f
        private const val DEFAULT_MINIMUM_NUMBER_SIZE = 9f
        private const val DEFAULT_NUMBER_SIZE_DECREASING_DEGREE = 0.3f
        private const val DEFAULT_NUMBER_MARGIN = 3
    }

    private var startNumber = DEFAULT_START_NUMBER
    private var endNumber = DEFAULT_END_NUMBER
    private var numberOfMaxShowingNumber = DEFAULT_NUMBER_OF_MAX_SHOWING_NUMBER
    private var numberOfEmptySpace = numberOfMaxShowingNumber / 2
    private var centerNumberSize = DEFAULT_CENTER_NUMBER_SIZE
    private var numberSizeDecreasingDegree = DEFAULT_NUMBER_SIZE_DECREASING_DEGREE
    private var initialCenterPosition = DEFAULT_CENTER_POSITION

    private val adapter: SantaNumberPickerAdapter by lazy { SantaNumberPickerAdapter() }

    private val tempTextView = AppCompatTextView(context).apply { text = "555" }

    private var maxHeightOfTextView = 0

    private var maxWidthOfTextView = 0

    init {
        initAttribute(attrs)

        itemAnimator = null
        setHasFixedSize(true)
        overScrollMode = OVER_SCROLL_NEVER
        layoutManager = LinearLayoutManager(context)
        setItemViewCacheSize(5)

        setAdapter(adapter)
        addOnScrollListener(SantaNumberPickerSnapScrollListener(this) { snapPosition ->
            setCenterPositionDelayed(snapPosition)
        })
    }

    private fun initAttribute(attrs: AttributeSet?) {
        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaNumberPicker,
                0, 0)


        if (typeArray.hasValue(R.styleable.SantaNumberPicker_minValue)) {
            startNumber = typeArray.getInt(R.styleable.SantaNumberPicker_minValue, DEFAULT_START_NUMBER)
        }
        if (typeArray.hasValue(R.styleable.SantaNumberPicker_maxValue)) {
            endNumber = typeArray.getInt(R.styleable.SantaNumberPicker_maxValue, DEFAULT_END_NUMBER)
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        setMeasuredDimension(getMaxWidth(), getMaxHeight())
        Log.d("numberPicker", "onMeasure : maxHeight = $maxHeightOfTextView")
    }
    
    private fun setCenterPositionDelayed(position: Int) {
        post {
            adapter.setCenterPosition(position)
        }
    }

    private fun getMaxWidth(): Int {
        if (maxWidthOfTextView == 0) {
            maxWidthOfTextView = getTempTextViewMeasured().measuredWidth
        }
        return maxWidthOfTextView
    }

    private fun getTempTextViewMeasured(): View {
        val wrapSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        return createTempTextViewForMeasuring().apply { measure(wrapSpec, wrapSpec) }
    }

    private fun getMaxHeight(): Int {
        if (maxHeightOfTextView == 0) {
            maxHeightOfTextView = getTempTextViewMeasured().measuredHeight
        }
        return maxHeightOfTextView * DEFAULT_NUMBER_OF_MAX_SHOWING_NUMBER
    }

    private fun createTempTextViewForMeasuring(): AppCompatTextView {
        setTextStyleByPosition(tempTextView, 0)
        return tempTextView
    }

    private fun setTextStyleByPosition(textView: AppCompatTextView, distanceFromCenterPosition: Int) {
        if (distanceFromCenterPosition == 0) {
            setCenterTextSize(textView)
            textView.setTextColorById(R.color.black)
        } else {
            setNonCenterTextStyle(textView, distanceFromCenterPosition)
            textView.setTextColorById(R.color.gray_3)
        }
    }

    private fun setNonCenterTextStyle(textView: AppCompatTextView, distanceFromCenterPosition: Int) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                (centerNumberSize * (1 - numberSizeDecreasingDegree * distanceFromCenterPosition))
                        .coerceAtLeast(DEFAULT_MINIMUM_NUMBER_SIZE))
    }

    private fun setCenterTextSize(textView: AppCompatTextView) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, centerNumberSize)
    }

    private fun initOtherTextStyle(textView: AppCompatTextView) {
        textView.run {
            gravity = Gravity.CENTER
            height = maxHeightOfTextView
        }
    }

    private fun getNumberByPosition(position: Int) : Int {
        return position - numberOfEmptySpace + startNumber
    }

    fun setRange(startNumber: Int, endNumber: Int) {
        this.startNumber = startNumber
        this.endNumber = endNumber
    }

    fun setInitialPosition(position: Int) {
        layoutManager?.let {
            post {
                (it as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
            }
            setCenterPositionDelayed(position + numberOfEmptySpace)
        }
    }

    fun getCurrentNumber(): Int {
        return getNumberByPosition(adapter.currentCenterPosition)
    }

    private inner class SantaNumberPickerAdapter : Adapter<SantaNumberPickerAdapter.SantaNumberPickerViewHolder>() {
        var currentCenterPosition = initialCenterPosition + numberOfEmptySpace

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SantaNumberPickerViewHolder {
            return SantaNumberPickerViewHolder(getTextView(parent.context))
        }

        override fun onBindViewHolder(holder: SantaNumberPickerViewHolder, position: Int) {
            val number = if (isInNumberRange(position)) {
                String.format("%02d", getNumberByPosition(position))
            } else {
                ""
            }

            setTextStyleByPosition(holder.textView, abs(currentCenterPosition - position))

            initOtherTextStyle(holder.textView)

            holder.textView.text = number
        }


        private fun isInNumberRange(position: Int): Boolean {
            return position >= numberOfEmptySpace && (endNumber - startNumber) + numberOfEmptySpace >= position
        }

        override fun getItemCount(): Int {
            return (endNumber - startNumber + 1) + numberOfEmptySpace * 2
        }

        fun setCenterPosition(position: Int) {
            currentCenterPosition = position
            notifyItemRangeChanged(position - numberOfEmptySpace, position + numberOfEmptySpace)
        }

        private fun getTextView(context: Context): AppCompatTextView {
            return AppCompatTextView(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER
            }
        }

        private inner class SantaNumberPickerViewHolder(itemView: View) : ViewHolder(itemView) {
            val textView = itemView as AppCompatTextView
        }
    }
}