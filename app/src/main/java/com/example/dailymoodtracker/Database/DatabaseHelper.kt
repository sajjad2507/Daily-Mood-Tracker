package com.example.dailymoodtracker.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mood_tracker.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "mood_entries"
        const val COLUMN_ID = "_id"
        const val COLUMN_DATE = "date"
        const val COLUMN_ANSWER1 = "answer1"
        const val COLUMN_ANSWER2 = "answer2"
        const val COLUMN_ANSWER3 = "answer3"
        const val COLUMN_MOOD = "mood"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT,
                $COLUMN_ANSWER1 TEXT,
                $COLUMN_ANSWER2 TEXT,
                $COLUMN_ANSWER3 TEXT,
                $COLUMN_MOOD TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveData(
        date: String,
        answer1: String,
        answer2: String,
        answer3: String,
        mood: String
    ): Long {
        val values = ContentValues().apply {
            put(COLUMN_DATE, date)
            put(COLUMN_ANSWER1, answer1)
            put(COLUMN_ANSWER2, answer2)
            put(COLUMN_ANSWER3, answer3)
            put(COLUMN_MOOD, mood)
        }

        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun updateData(
        date: String,
        answer1: String,
        answer2: String,
        answer3: String,
        mood: String
    ): Int {
        val values = ContentValues().apply {
            put(COLUMN_ANSWER1, answer1)
            put(COLUMN_ANSWER2, answer2)
            put(COLUMN_ANSWER3, answer3)
            put(COLUMN_MOOD, mood)
        }

        return writableDatabase.update(
            TABLE_NAME,
            values,
            "$COLUMN_DATE = ?",
            arrayOf(date)
        )
    }

    fun getDataForDate(date: String): Cursor? {
        return readableDatabase.query(
            TABLE_NAME,
            null,
            "$COLUMN_DATE = ?",
            arrayOf(date),
            null,
            null,
            null
        )
    }

    fun getDataForMonth(month: String, year: String): Cursor? {
        val startDate = "$year-$month-01"
        val endDate = "$year-${month.toInt() + 1}-01"

        return readableDatabase.query(
            TABLE_NAME,
            null,
            "$COLUMN_DATE >= ? AND $COLUMN_DATE < ?",
            arrayOf(startDate, endDate),
            null,
            null,
            null
        )
    }
}
