package com.example.dailymoodtracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.dailymoodtracker.Database.DatabaseHelper
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.adapter.DatabaseManager
import com.example.dailymoodtracker.databinding.FragmentDetailScreenBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.time.LocalDate
import java.util.*

class DetailScreen : Fragment() {

    lateinit var binding: FragmentDetailScreenBinding
    var mood: Int = 0
    private lateinit var databaseManager: DatabaseManager
    var saved: Int = 0
    lateinit var mAdView: AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailScreenBinding.inflate(layoutInflater, container, false)

        var day = arguments?.getString("day", "")
        var month = arguments?.getString("month", "")
        var monthNo = arguments?.getString("monthNo", "")
        val year = arguments?.getString("year", "")
        val date = day + ", " + year

        MobileAds.initialize(requireContext()) {}

        showBanner()

        databaseManager = DatabaseManager(requireContext())
        // Open the database connection
        databaseManager.open()

        var dateStart = date
        var monthStart = monthNo

        if (day!!.toInt() < 10) {
            day = "0" + day
        }

        if (monthNo!!.toInt() < 10) {
            monthStart = "0" + monthNo
        }

        val dateString = "$year-$dateStart-$monthStart"
        val dateStringStore = "$year-$monthStart-$day"

        try {

            val data = databaseManager.getDataForDate(dateStringStore)

            if (data != null && data.moveToFirst()) {

                val id = data.getLong(data.getColumnIndex(DatabaseHelper.COLUMN_ID))
                val date = data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_DATE))
                val answer1 = data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_ANSWER1))
                val answer2 = data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_ANSWER2))
                val answer3 = data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_ANSWER3))
                val moodRecord = data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_MOOD))

                binding.edtYesterday.setText(answer1)
                binding.edtToday.setText(answer2)
                binding.edtIssues.setText(answer3)

                if (moodRecord.toInt() == 1) {
                    binding.sadImg.setBackgroundResource(R.drawable.month_bg)
                    mood = 1
                } else if (moodRecord.toInt() == 2) {
                    binding.goodImg.setBackgroundResource(R.drawable.month_bg)
                    mood = 2
                } else if (moodRecord.toInt() == 3) {
                    binding.happyImg.setBackgroundResource(R.drawable.month_bg)
                    mood = 3
                }

                saved = 1

                data.close()

            } else {
                Log.e("@@@", "empty")
            }

        } catch (e: Exception) {

            Log.e("@@@", "Error")
        }

        binding.monthTxt.setText(month)
        binding.dateYearTxt.setText(date)

        binding.sadImg.setOnClickListener {

            binding.sadImg.setBackgroundResource(R.drawable.month_bg)
            binding.goodImg.setBackgroundResource(R.drawable.empty_bg)
            binding.happyImg.setBackgroundResource(R.drawable.empty_bg)

            mood = 1

        }

        binding.goodImg.setOnClickListener {

            binding.sadImg.setBackgroundResource(R.drawable.empty_bg)
            binding.goodImg.setBackgroundResource(R.drawable.month_bg)
            binding.happyImg.setBackgroundResource(R.drawable.empty_bg)

            mood = 2

        }

        binding.happyImg.setOnClickListener {

            binding.sadImg.setBackgroundResource(R.drawable.empty_bg)
            binding.goodImg.setBackgroundResource(R.drawable.empty_bg)
            binding.happyImg.setBackgroundResource(R.drawable.month_bg)

            mood = 3

        }

        binding.saveBtn.setOnClickListener {

            if (mood > 0 && mood <= 3) {

                if (saved == 0) {

                    val result = databaseManager.saveData(
                        dateStringStore,

                        binding.edtYesterday!!.text.toString(),
                        binding.edtToday!!.text.toString(),
                        binding.edtIssues!!.text.toString(),
                        mood.toString() // Convert mood to String

                    )

                    if (result != -1L) {

                        NavHostFragment.findNavController(this)
                            .navigate(R.id.action_detailScreen_to_homeScreen)

                    } else {
                        // Data save failed
                        // You can handle the failure, show an error message, etc.
                        Toast.makeText(requireContext(), "Error saving data.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {

                    val result = databaseManager.updateData(
                        dateString,
                        binding.edtYesterday!!.text.toString(),
                        binding.edtToday!!.text.toString(),
                        binding.edtIssues!!.text.toString(),
                        mood.toString() // Convert mood to String

                    )

                    if (result != -1) {

                        NavHostFragment.findNavController(this)
                            .navigate(R.id.action_detailScreen_to_homeScreen)

                    } else {
                        // Data save failed
                        // You can handle the failure, show an error message, etc.
                        Toast.makeText(requireContext(), "Error saving data.", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            } else {
                Toast.makeText(requireContext(), "Please, select the mood!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the database connection when the activity is destroyed
        databaseManager.close()
    }

    private fun showBanner() {
        mAdView = binding.adView

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

}