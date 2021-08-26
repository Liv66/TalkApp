package com.example.talkapp.chatlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.talkapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentListBinding.inflate(inflater, container, false)

        val safeArgs: ListFragmentArgs by navArgs()
        val currentUser = safeArgs.uid
        val loginEmailArg =safeArgs.loginEmail

        binding.apply{
            btn1.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToChattingFragment(loginEmailArg)
                findNavController().navigate(action)
            }
            btnFriend.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToFriendFragment(loginEmailArg, currentUser)
                findNavController().navigate(action)
            }
        }
        return binding.root
    }
}