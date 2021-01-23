package org.sopt.santamanitto.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val adapter = JoinedRoomsAdapter()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            viewModel = this@MainFragment.viewModel
            recyclerviewMainHistory.adapter = adapter
        }

        return binding.root
    }
}