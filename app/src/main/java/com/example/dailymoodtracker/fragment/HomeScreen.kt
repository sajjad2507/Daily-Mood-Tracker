package com.example.dailymoodtracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.databinding.FragmentHomeScreenBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*

class HomeScreen : Fragment() {

    lateinit var binding: FragmentHomeScreenBinding
    private var currentYear: Int = 0
    lateinit var mAdView: AdView
    private var backPressedTime: Long = 0
    private val doubleBackToExitPressedMessage = "Press back again to exit"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)

        val navHost = NavHostFragment.findNavController(this)

        getCurrentYear()
        updateYearTextView()

//        MobileAds.initialize(requireContext()) {}
//
//        showBanner()

        binding.nextYear.setOnClickListener {
            incrementYear()
            updateYearTextView()
        }

        binding.previousYear.setOnClickListener {
            decrementYear()
            updateYearTextView()
        }

        binding.janBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "January")
            bundle.putString("monthNo", "1")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.fabBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "February")
            bundle.putString("monthNo", "2")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.marBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "March")
            bundle.putString("monthNo", "3")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.aprBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "April")
            bundle.putString("monthNo", "4")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.mayBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "May")
            bundle.putString("monthNo", "5")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }


        binding.junBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "June")
            bundle.putString("monthNo", "6")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.julBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "July")
            bundle.putString("monthNo", "7")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.augBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "August")
            bundle.putString("monthNo", "8")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.sepBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "September")
            bundle.putString("monthNo", "9")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.octBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "October")
            bundle.putString("monthNo", "10")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.novBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "November")
            bundle.putString("monthNo", "11")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.decBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("year", binding.yearTxt.text.toString())
            bundle.putString("month", "December")
            bundle.putString("monthNo", "12")

            navHost.navigate(R.id.action_homeScreen_to_pickDateScreen, bundle)

        }

        binding.settingBtn.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_homeScreen_to_settingScreen)

        }


        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        requireActivity().finish()
                        return
                    } else {
                        Toast.makeText(
                            requireContext(),
                            doubleBackToExitPressedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })

        return binding.root
    }

    private fun getCurrentYear() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR)
    }

    private fun getNextYear(currentYear: Int): Int {
        return currentYear + 1
    }

    private fun getPreviousYear(currentYear: Int): Int {
        return currentYear - 1
    }

    private fun incrementYear() {
        currentYear = getNextYear(currentYear)
    }

    private fun decrementYear() {
        currentYear = getPreviousYear(currentYear)
    }

    private fun updateYearTextView() {
        binding.yearTxt.text = currentYear.toString()
    }

//    private fun showBanner() {
//        mAdView = binding.adView
//
//        val adRequest = AdRequest.Builder().build()
//        mAdView.loadAd(adRequest)
//    }
}