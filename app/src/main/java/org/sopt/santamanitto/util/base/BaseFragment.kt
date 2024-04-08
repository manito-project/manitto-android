package org.sopt.santamanitto.util.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment<B : ViewDataBinding>(
        @LayoutRes private val layoutRes: Int,
        private val adjustPan: Boolean
) : Fragment() {
    protected lateinit var binding: B

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val window = requireActivity().window

        if (adjustPan) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                binding.root.setOnApplyWindowInsetsListener { view, windowInsets ->
                    val insets = windowInsets.getInsets(WindowInsets.Type.systemBars())
                    view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
                    windowInsets
                }
            } else {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }
}