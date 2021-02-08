package org.sopt.santamanitto.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import org.sopt.santamanitto.util.toPixel

internal class RoundDialog(
        private val title: String?,
        private val contentLayout: View?,
        private val contentText: String?,
        private val contentTextBold: Boolean,
        private val horizontalButtons: List<RoundDialogButton>?,
        private val verticalButtons: List<RoundDialogButton>?,
        private var fontSize: Float?,
        @ColorRes private val pointColor: Int?,
        @ColorRes private val dividerColor: Int?,
        private val enableCancel: Boolean,
        private val enableDivider: Boolean

) : DialogFragment() {

    companion object {
        private const val TAG = "LTDialog"
        private const val MARGIN_VERTICAL_TITLE_DP = 16
        private const val MARGIN_VERTICAL_CONTENT_DP = 40
        private const val RADIUS_OF_DIALOG_DP = 20
        private const val DIVIDER_COLOR = "#e2e2e2"
        private const val DEFAULT_TEXT_SIZE_SP = 17f
        private const val DIALOG_WITH_RATIO = 0.7
    }

    private var contentView: View? = null

    private var deviceSizeX: Int? = null

    override fun onResume() {
        super.onResume()
        if (deviceSizeX == null) {
            if (context == null) {
                return
            }
            val size = Point()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                requireContext().display
            } else {
                (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            }?.getRealSize(size)
            deviceSizeX = size.x
        }
        if (deviceSizeX == null) {
            return
        }
        val params = dialog?.window?.attributes
        params?.width = (deviceSizeX!! * DIALOG_WITH_RATIO).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        checkIsIllegalAttribute()
        val rootView = FrameLayout(inflater.context)

        if (fontSize == null) {
            fontSize = DEFAULT_TEXT_SIZE_SP
        }

        initView(rootView)

        if (dialog != null) {
            val window: Window? = dialog!!.window
            if (window != null) {
                //다이얼로그의 윤곽선을 둥글게 깎기
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.requestFeature(Window.FEATURE_NO_TITLE)
            } else {
                Log.e(TAG, "Window is null.")
            }
        } else {
            Log.e(TAG, "Dialog is null.")
        }

        if (!enableCancel) {
            isCancelable = false
        }

        return rootView
    }

    private fun initView(rootView: ViewGroup) {
        val cardView = CardView(rootView.context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            radius = RADIUS_OF_DIALOG_DP.toPixel().toFloat()
        }
        val linearLayout = LinearLayout(rootView.context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.VERTICAL
        }

        initTitle(linearLayout)

        initContent(linearLayout)

        initButtons(linearLayout)

        cardView.addView(linearLayout)

        rootView.addView(cardView)
    }

    private fun initTitle(parent: ViewGroup) {
        if (title == null) {
            return
        }
        val titleTextView = getTitleTextView(parent.context)
        parent.addView(titleTextView)
    }

    private fun initContent(parent: ViewGroup) {
        if (contentLayout == null && contentText == null) {
            return
        }
        addDivider(parent, true)
        contentView = contentLayout ?: getContentTextView(parent.context).apply { text = contentText }
        parent.addView(contentView)
    }

    private fun initButtons(parent: ViewGroup) {
        if (horizontalButtons == null && verticalButtons == null) {
            return
        }

        val buttonLayout = LinearLayout(parent.context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.VERTICAL
        }

        verticalButtons?.let {
            addDivider(buttonLayout, true)
            makeButtons(it, buttonLayout, false)
        }

        horizontalButtons?.let {
            addDivider(buttonLayout, true)
            //가로 버튼을 담을 하나의 LinearLayout을 생성
            val verticalButtonLayout = LinearLayout(parent.context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                orientation = LinearLayout.HORIZONTAL
            }

            //생성한 LinearLayout에 버튼들을 추가
            makeButtons(it, verticalButtonLayout, true)

            //버튼이 추가된 LinearLayout을 다이얼로그에 추가
            buttonLayout.addView(verticalButtonLayout)
        }

        parent.addView(buttonLayout)
    }

    private fun makeButtons(buttons: List<RoundDialogButton>, parent: ViewGroup, isHorizontal: Boolean) {

        for (roundDialogButton in buttons) {

            val button = getButtonTextView(parent.context).apply {
                layoutParams = if (isHorizontal) {
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                } else {
                    LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                }

                foreground = getRippleBackground(parent.context)

                //버튼에 문자열 적용
                text = roundDialogButton.text

                //클릭 리스너 적용
                setOnClickListener {
                    roundDialogButton.listener?.let { it(contentView) }
                    if (roundDialogButton.canDismiss) {
                        dismiss()
                    }
                }
            }
            //첫 번째 버튼이 아니라면
            if (roundDialogButton != buttons[0]) {
                //구분선 삽입
                addDivider(parent, !isHorizontal)
            }

            //버튼 추가
            parent.addView(button)
        }
    }

    private fun getRippleBackground(context: Context): Drawable? {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        return ContextCompat.getDrawable(context, outValue.resourceId)
    }

    private fun addDivider(container: ViewGroup, isHorizontal: Boolean) {
        if (!enableDivider) {
            return
        }
        //뷰 하나 생성
        val divider = View(context)

        //간격을 1로 하여 선처럼 보이게 함
        divider.layoutParams = if (isHorizontal) {
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1.toPixel())
        } else {
            LinearLayout.LayoutParams(1.toPixel(), LinearLayout.LayoutParams.MATCH_PARENT)
        }

        //색 지정
        if (dividerColor == null) {
            divider.setBackgroundColor(Color.parseColor(DIVIDER_COLOR))
        } else {
            divider.setBackgroundResource(dividerColor)
        }

        //삽입
        container.addView(divider)
    }

    private fun checkIsIllegalAttribute() {
        if (contentText != null && contentLayout != null) {
            throw IllegalArgumentException("Content text and content view cannot be coexist")
        }
    }

    private fun getTitleTextView(context: Context): TextView {
        return getNewTextView(context, MARGIN_VERTICAL_TITLE_DP, hasColor = true, isBold = true)
    }

    private fun getContentTextView(context: Context): TextView {
        return getNewTextView(context, MARGIN_VERTICAL_CONTENT_DP, false, contentTextBold)
    }

    private fun getButtonTextView(context: Context): TextView {
        return getNewTextView(context, MARGIN_VERTICAL_TITLE_DP, hasColor = true, isBold = false)
    }

    private fun getNewTextView(context: Context, marginVertical: Int, hasColor: Boolean, isBold: Boolean): TextView {
        return TextView(context).apply {
            this.text = title
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            setPadding(
                    0, marginVertical.toPixel(),
                    0, marginVertical.toPixel()
            )
            gravity = Gravity.CENTER
            textSize = fontSize!!
            if (hasColor && pointColor != null) {
                setBackgroundResource(pointColor)
            }
            if (isBold) {
                setTypeface(typeface, Typeface.BOLD)
            }
        }
    }
}