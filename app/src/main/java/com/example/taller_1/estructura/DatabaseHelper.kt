package com.example.taller_1.estructura

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun deleteNameAndColor(name: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(name))
    }

    fun saveNameAndColor(name: String, color: Int) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_COLOR, color)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllNamesAndColors(): List<Pair<String, Int>> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_NAME, COLUMN_COLOR),
            null,
            null,
            null,
            null,
            null
        )

        val namesAndColors = mutableListOf<Pair<String, Int>>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val color = getInt(getColumnIndexOrThrow(COLUMN_COLOR))
                namesAndColors.add(Pair(name, color))
            }
        }
        cursor.close()
        return namesAndColors
    }
    fun updateColorByName(name: String, newColor: Int) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_COLOR, newColor)
        }
        db.update(TABLE_NAME, contentValues, "$COLUMN_NAME = ?", arrayOf(name))
    }



    companion object {
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "user"
        const val COLUMN_NAME = "name"
        const val COLUMN_COLOR = "color"

        private const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_COLOR INTEGER)"
    }
}