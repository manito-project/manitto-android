package org.sopt.santamanitto.user.signin.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


class WebLinkFragment : Fragment() {

    private val args: WebLinkFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(args.url))
        startActivity(browserIntent)
        findNavController().navigateUp()
    }
}