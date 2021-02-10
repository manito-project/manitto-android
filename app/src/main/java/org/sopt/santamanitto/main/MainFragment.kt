package org.sopt.santamanitto.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.SecretString
import org.sopt.santamanitto.databinding.FragmentMainBinding
import org.sopt.santamanitto.room.create.CreateRoomActivity
import org.sopt.santamanitto.user.signin.fragment.ConditionFragmentDirections
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var adapter: JoinedRoomsAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MainFragment
            viewModel = this@MainFragment.viewModel
            recyclerviewMainHistory.adapter = adapter
        }

        initJoinedRooms()

        binding.santaimageroundbuttonMainMakeroom.setOnClickListener {
            startCreateRoomActivity()
        }

        return binding.root
    }

    private fun initJoinedRooms() {
        viewModel.getJoinedRooms()
    }

    private fun startCreateRoomActivity() {
        requireActivity().run {
            startActivity(Intent(this, CreateRoomActivity::class.java))
        }
    }
}