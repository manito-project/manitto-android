package org.sopt.santamanitto.util

import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment

object FragmentUtil {

    fun Fragment.hideKeyboardOnOutsideEditText() {
        view?.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val currentFocusView = activity?.currentFocus
                if (currentFocusView is EditText) {
                    val imm =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
                    currentFocusView.clearFocus()
                }
                v.performClick()
            }
            false
        }
    }
}