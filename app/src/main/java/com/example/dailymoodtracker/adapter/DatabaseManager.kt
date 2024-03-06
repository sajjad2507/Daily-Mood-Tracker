package com.example.dailymoodtracker.adapter

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import com.example.dailymoodtracker.Database.DatabaseHelper
import java.time.LocalDate

class DatabaseManager(private val context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private var db: SQLiteDatabase? = null

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        db?.close()
    }

    // Save data
    fun saveData(date: String, answer1: String, answer2: String, answer3: String, mood: String): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_DATE, date)
            put(DatabaseHelper.COLUMN_ANSWER1, answer1)
            put(DatabaseHelper.COLUMN_ANSWER2, answer2)
            put(DatabaseHelper.COLUMN_ANSWER3, answer3)
            put(DatabaseHelper.COLUMN_MOOD, mood)
        }

        return db?.insert(DatabaseHelper.TABLE_NAME, null, values) ?: -1
    }

    // Update data for a specific date
    fun updateData(date: String, answer1: String, answer2: String, answer3: String, mood: String): Int {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ANSWER1, answer1)
            put(DatabaseHelper.COLUMN_ANSWER2, answer2)
            put(DatabaseHelper.COLUMN_ANSWER3, answer3)
            put(DatabaseHelper.COLUMN_MOOD, mood)
        }

        return db?.update(
            DatabaseHelper.TABLE_NAME,
            values,
            "${DatabaseHelper.COLUMN_DATE} = ?",
            arrayOf(date)
        ) ?: -1
    }

    // Get data for a specific date
    fun getDataForDate(date: String): Cursor? {
        return db?.query(
            DatabaseHelper.TABLE_NAME,
            null,
            "${DatabaseHelper.COLUMN_DATE} = ?",
            arrayOf(date),
            null,
            null,
            null
        )
    }

    fun getMoodDataForMonth(month: String, year: String): List<String> {
        val startDate = "$year-$month-01"
        val endDate = "$year-${month.toInt() + 1}-01"

        val query = "SELECT ${DatabaseHelper.COLUMN_MOOD} FROM ${DatabaseHelper.TABLE_NAME} " +
                "WHERE ${DatabaseHelper.COLUMN_DATE} >= '$startDate' " +
                "AND ${DatabaseHelper.COLUMN_DATE} < '$endDate'"

        val moodList = mutableListOf<String>()

        db?.rawQuery(query, null)?.use { cursor ->
            while (cursor.moveToNext()) {
                val mood = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOOD))
                moodList.add(mood)
            }
        }

        return moodList
    }

//    fun getMoodDataForCustomRange(startDate: String, endDate: String): List<String> {
//        val query = "SELECT ${DatabaseHelper.COLUMN_MOOD} FROM ${DatabaseHelper.TABLE_NAME} " +
//                "WHERE ${DatabaseHelper.COLUMN_DATE} >= '$startDate' " +
//                "AND ${DatabaseHelper.COLUMN_DATE} < '$endDate'"
//
//        Log.d("SQL_QUERY", query)  // Add this line to log the SQL query
//
//        val moodList = mutableListOf<String>()
//
//        db?.rawQuery(query, null)?.use { cursor ->
//            while (cursor.moveToNext()) {
//                val mood = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOOD))
//                Log.d("MOOD_VALUE", "Mood: $mood")
//                moodList.add(mood)
//            }
//        }
//
//        return moodList
//    }

    fun getMoodDataForCustomRange(startDate: String, endDate: String): List<String> {
        val dateList = generateDateList(startDate, endDate)

        val moodList = mutableListOf<String>()

        for (date in dateList) {
            val mood = getMoodForDate(date)
            moodList.add(mood)
        }

        return moodList
    }

    private fun generateDateList(startDate: String, endDate: String): List<String> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)

        return generateSequence(start) { it.plusDays(1) }
            .takeWhile { it <= end }
            .map { it.toString() }
            .toList()
    }

    private fun getMoodForDate(date: String): String {
        val cursor = db?.query(
            DatabaseHelper.TABLE_NAME,
            null,
            "${DatabaseHelper.COLUMN_DATE} = ?",
            arrayOf(date),
            null,
            null,
            null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val mood = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOOD))
            cursor.close()
            mood ?: "0"
        } else {
            "0"
        }
    }


}
