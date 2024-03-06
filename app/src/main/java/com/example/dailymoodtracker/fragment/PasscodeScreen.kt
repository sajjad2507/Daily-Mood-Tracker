package com.example.dailymoodtracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.databinding.FragmentPasscodeScreenBinding

class PasscodeScreen : Fragment() {

    lateinit var binding: FragmentPasscodeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPasscodeScreenBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}