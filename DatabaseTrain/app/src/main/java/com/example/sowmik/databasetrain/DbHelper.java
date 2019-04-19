package com.example.sowmik.databasetrain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "traindetails.db";
    private static final String TABLE_NAME = "train_details";

    private static final int VERSION_NUMBER = 2;

    private static final String ID = "_id";
    private static final String TRAIN_NAME = "name";
    private static final String TRAIN_NO = "train_no";
    private static final String OFFDAY = "offday";
    private static final String DEPARTURE = "departure";
    private static final String DEPARTURE_TIME = "departure_time";
    private static final String ARRIVAL = "arrival";
    private static final String ARRIVAL_TIME = "arrival_time";


    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +TRAIN_NAME + " TEXT , " + TRAIN_NO + " TEXT , " + OFFDAY + " TEXT , " + DEPARTURE + " TEXT , " + DEPARTURE_TIME + " TEXT , " + ARRIVAL + " TEXT , " + ARRIVAL_TIME + " TEXT )";

    private static final String DROP_TABLE = " DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String SELECT_ALL = " SELECT * FROM " + TABLE_NAME;


    public DbHelper(Context context) {


        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            Toast.makeText(context, "onCreate is called.", Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);

        }catch (Exception e){
            Toast.makeText(context, "Exception : "+e, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context, "onUpgrade is called.", Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);

        }catch (Exception e){

            Toast.makeText(context, "Exception : "+e, Toast.LENGTH_SHORT).show();

        }

    }


    public long insertData(String t_name,String t_no,String off_day,String d_station,String d_time,String a_station,String a_time){


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TRAIN_NAME,t_name);
        contentValues.put(TRAIN_NO,t_no);
        contentValues.put(OFFDAY,off_day);
        contentValues.put(DEPARTURE,d_station);
        contentValues.put(DEPARTURE_TIME,d_time);
        contentValues.put(ARRIVAL,a_station);
        contentValues.put(ARRIVAL_TIME,a_time);

        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowId;
    }

    public Cursor displayAllData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return cursor;
    }

    public Cursor displaySearchData(final String a, final String b){

        //System.out.println(a +" sowmik sarker after getting"+b);

        final String str1 = a;
        final String str2 = b;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE  ( " + DEPARTURE + " = '"+str1+"'  AND " + ARRIVAL + " = '"+str2+"') ",null);

        return cursor;

    }


}
