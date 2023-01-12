package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String TABLE_NAME = "SENSOR_DATA";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_ACCEL_DATA = "ACCEL_DATA";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "sensors.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStatement= "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TIME + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_ACCEL_DATA + " TEXT )";
        sqLiteDatabase.execSQL(createStatement);

    }

    public boolean insertRow(String location, String accelerationData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TIME, Calendar.getInstance().getTime().toString());
        cv.put(COLUMN_LOCATION,location);
        cv.put(COLUMN_ACCEL_DATA,accelerationData);

        long res = db.insert(TABLE_NAME,null,cv);
        return res > -1 ? true : false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<SendorDataModel> selectAll(){
        List<SendorDataModel> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from " + TABLE_NAME,null );
        while (cursor.moveToNext()){
            @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            @SuppressLint("Range") String accelerometerData = cursor.getString(cursor.getColumnIndex(COLUMN_ACCEL_DATA));
            SendorDataModel sendorDataModel = new SendorDataModel(location,accelerometerData,time);
            resultList.add(sendorDataModel);
        }
        return resultList;
    }
}

//TODO
//SELECT
//Show on map
