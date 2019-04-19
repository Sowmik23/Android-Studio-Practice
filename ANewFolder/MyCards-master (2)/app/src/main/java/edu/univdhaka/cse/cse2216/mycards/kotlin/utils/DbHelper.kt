package edu.univdhaka.cse.cse2216.mycards.kotlin.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        // If you change the database schema, you must increment the database version.
        private const val DB_VERSION = 1

        private const val DB_NAME = "MyCards.db"

    }


    override fun onCreate(db: SQLiteDatabase) {
        val createCardTableSql = """
                    CREATE TABLE cards (
                        _id INTEGER PRIMARY KEY AUTOINCREMENT,
                        number TEXT UNIQUE,
                        type TEXT,
                        bank_name TEXT,
                        cardholder_name TEXT,
                        expiry_date TEXT,
                        cvv INTEGER
                    )
                    """.trimMargin()

        db.execSQL(createCardTableSql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you need to execute the sql statements required to modify the old DB schema to be compatible with the new one
    }
}
