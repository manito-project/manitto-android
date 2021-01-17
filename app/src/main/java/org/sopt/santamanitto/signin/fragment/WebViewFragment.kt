package org.sopt.santamanitto.signin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.sopt.santamanitto.databinding.FragmentWebviewBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebviewBinding
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        binding.webViewWebviewfragment.run {
            settings.javaScriptEnabled = false

            loadUrl(args.url)
        }
        return binding.root
    }
}