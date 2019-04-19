package edu.univdhaka.cse.cse2216.mycards.java.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "MyCards.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String createCardTableSql
                = "CREATE TABLE cards (" +
                "      _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "      number TEXT UNIQUE," +
                "      type TEXT," +
                "      bank_name TEXT," +
                "      cardholder_name TEXT," +
                "      expiry_date TEXT," +
                "      cvv INTEGER" +
                "  )";

        db.execSQL(createCardTableSql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Here you need to execute the sql statements required to modify the old DB schema to be compatible with the new one
    }
}
