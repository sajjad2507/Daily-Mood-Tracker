package com.example.dailymoodtracker.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymoodtracker.DataModel.DayItem
import com.example.dailymoodtracker.R
import com.example.dailymoodtracker.fragment.PickDateScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DaysAdapter(
    private val context: Context,
    val frag: PickDateScreen,
    private val daysList: List<DayItem>,
    val month: String,
    val monthNo: String,
    val year: String
) :
    RecyclerView.Adapter<DaysAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_date, parent, false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val dayItem = daysList[position]
        holder.bind(dayItem)

        val day = extractDayFromDate(dayItem.day)

        holder.itemView.setOnClickListener {

            if (compareDateWithCurrent(day.toString(), monthNo, year)) {

                val bundle = Bundle()
                bundle.putString("day", day.toString())
                bundle.putString("month", month)
                bundle.putString("monthNo", monthNo)
                bundle.putString("year", year)

                NavHostFragment.findNavController(frag)
                    .navigate(R.id.action_pickDateScreen_to_detailScreen, bundle)

            } else {
                Toast.makeText(context, "You can't pick next dates!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.itemDateTxt)

        fun bind(dayItem: DayItem) {
            dayTextView.text = dayItem.day
        }
    }

    fun extractDayFromDate(dateString: String): Int? {

        val parts = dateString.split("-")

        if (parts.size == 2) {

            val dayPart = parts[0].trim()

            return dayPart.toIntOrNull()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun compareDateWithCurrent(date: String, month: String, year: String): Boolean {

        var dateStart = date
        var monthStart = month

        if (date.toInt() < 10) {
            dateStart = "0" + date
        }

        if (month.toInt() < 10) {
            monthStart = "0" + month
        }

        val dateString = "$year-$monthStart-$dateStart"

        val inputDate = LocalDate.parse(dateString)

        val currentDate = LocalDate.now()

        return if (inputDate.isAfter(currentDate)) {
            false
        } else {
            true
        }
    }
}
