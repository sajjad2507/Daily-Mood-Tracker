package com.example.dailymoodtracker.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailymoodtracker.DataModel.DayItem
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.adapter.DatabaseManager
import com.example.dailymoodtracker.adapter.DaysAdapter
import com.example.dailymoodtracker.databinding.FragmentPickDateScreenBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PickDateScreen : Fragment() {

    lateinit var binding: FragmentPickDateScreenBinding
    lateinit var mAdView: AdView
    private lateinit var databaseManager: DatabaseManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPickDateScreenBinding.inflate(layoutInflater, container, false)

        val year = arguments?.getString("year", "")
        val month = arguments?.getString("month", "")
        val monthNo = arguments?.getString("monthNo", "")

        databaseManager = DatabaseManager(requireContext())
        // Open the database connection
        databaseManager.open()

        MobileAds.initialize(requireContext()) {}

        showBanner()

        if (year!!.isNotEmpty() && month!!.isNotEmpty()) {

            binding.monthTxt.setText(month)
            binding.yearTxt.setText(year)

        }

        val daysList = getDaysOfMonth(year!!.toInt(), monthNo!!.toInt()).map { DayItem(it) }

        val adapter = DaysAdapter(
            requireContext(),
            this,
            daysList,
            month.toString(),
            monthNo.toString(),
            year.toString()
        )
        binding.dateRcv.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.dateRcv.layoutManager = layoutManager

        binding.reportBtn.setOnClickListener {

            val yearCheck = year
            var monthNoCheck = monthNo

            if (year.isNotEmpty() && monthNo.isNotEmpty()) {

                if (monthNoCheck?.length == 1) {
                    monthNoCheck = monthNoCheck?.padStart(2, '0')
                }

                val startDate = "$year-$monthNoCheck-01"

                val endDate = LocalDate.parse(startDate)
                    .plusMonths(1)
                    .minusDays(1).toString()

                val bundle = Bundle()
                bundle.putString("startDate", startDate)
                bundle.putString("endDate", endDate)
                bundle.putString("month", month)
                bundle.putString("year", year)

                NavHostFragment.findNavController(this).navigate(R.id.action_pickDateScreen_to_reportScreen, bundle)

            } else {
                Toast.makeText(requireContext(), "Please select a valid year and month", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysOfMonth(year: Int, month: Int): List<String> {
        val startDate = LocalDate.of(year, month, 1)
        val endDate = startDate.plusMonths(1).minusDays(1)

        val formatter = DateTimeFormatter.ofPattern("d - EEEE", Locale.ENGLISH)

        return generateSequence(startDate) { date ->
            date.plusDays(1)
        }.takeWhile { it <= endDate }
            .map { it.format(formatter) }
            .toList()
    }

    private fun showBanner() {
        mAdView = binding.adView

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the database connection when the activity is destroyed
        databaseManager.close()
    }

}