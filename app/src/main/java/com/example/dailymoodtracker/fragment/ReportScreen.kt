package com.example.dailymoodtracker.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.CategoryValueDataEntry
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.charts.Cartesian
import com.example.dailymoodtracker.DataModel.ReportItem
import com.example.dailymoodtracker.Database.DatabaseHelper
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.adapter.DatabaseManager
import com.example.dailymoodtracker.adapter.DaysAdapter
import com.example.dailymoodtracker.adapter.ReportAdapter
import com.example.dailymoodtracker.databinding.FragmentReportScreenBinding
import java.time.LocalDate

class ReportScreen : Fragment() {

    lateinit var binding: FragmentReportScreenBinding
    private lateinit var databaseManager: DatabaseManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReportScreenBinding.inflate(layoutInflater, container, false)

        databaseManager = DatabaseManager(requireContext())
        // Open the database connection
        databaseManager.open()

        val startDate = arguments?.getString("startDate", "")
        val endDate = arguments?.getString("endDate", "")
        val month = arguments?.getString("month", "")
        val year = arguments?.getString("year", "")

        binding.monthTxt.setText(month!!)
        binding.yearTxt.setText(year!!)

        val list = getList(startDate!!, endDate!!)


        val adapter = ReportAdapter(
            requireContext(),
            list,
        )
        binding.moodRcv.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.moodRcv.layoutManager = layoutManager

        binding.backBtn.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_reportScreen_to_homeScreen)

        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getList(startDate: String, endDate: String): List<ReportItem> {
        val list = mutableListOf<ReportItem>() // Use a MutableList to add items

        val moodList = databaseManager.getMoodDataForCustomRange(startDate, endDate)

        for (i in moodList) {
            val item = ReportItem(i)
            list.add(item) // Add the ReportItem to the list
        }

        return list
    }


    override fun onDestroy() {
        super.onDestroy()
        databaseManager.close()
    }
}