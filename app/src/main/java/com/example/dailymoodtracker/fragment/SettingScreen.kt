package com.example.dailymoodtracker.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.databinding.FragmentSettingScreenBinding
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class SettingScreen : Fragment() {

    lateinit var binding: FragmentSettingScreenBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingScreenBinding.inflate(layoutInflater, container, false)

        binding.homeBtnBottom.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_settingScreen_to_homeScreen)

        }

        binding.homeBtn.setOnClickListener {

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_settingScreen_to_homeScreen)

        }

        binding.todayBtn.setOnClickListener {

            val currentDate = LocalDate.now()
            val day = currentDate.dayOfMonth.toString()
            val monthNo = currentDate.monthValue.toString()
            val month = currentDate.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toString()
            val year = currentDate.year.toString()

            val bundle = Bundle()
            bundle.putString("day", day)
            bundle.putString("month", month)
            bundle.putString("monthNo", monthNo)
            bundle.putString("year", year)

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_settingScreen_to_detailScreen, bundle)

        }

        binding.exitBtn.setOnClickListener {

            activity?.finish()

        }

        return binding.root
    }
}