package com.example.dailymoodtracker.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymoodtracker.DataModel.ReportItem
import com.example.dailymoodtracker.R

class ReportAdapter(
    private val context: Context,
    private val daysList: List<ReportItem>
) :
    RecyclerView.Adapter<ReportAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_mood, parent, false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val dayItem = daysList[position]
        holder.bind(dayItem, position)



    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.dayTxt)
        private val moodImageView: ImageView = itemView.findViewById(R.id.moodImg)


        fun bind(dayItem: ReportItem, position: Int) {
            
            dayTextView.text = (position + 1).toString()

            if (dayItem.mood.equals("1")) {
                moodImageView.setImageResource(R.drawable.sad)
            } else if (dayItem.mood.equals("2")) {
                moodImageView.setImageResource(R.drawable.good)
            } else if (dayItem.mood.equals("3")) {
                moodImageView.setImageResource(R.drawable.happy)
            }

        }
    }
}
