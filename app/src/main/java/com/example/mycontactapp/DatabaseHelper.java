package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact2019.db";
    public static final String TABLE_NAME = "Contact2019_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_ADDRESS = "address";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_CONTACT + " TEXT," + COLUMN_NAME_PHONE + " TEXT," + COLUMN_NAME_ADDRESS + " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase(); //for test delete later
        Log.d("MyContactApp", "DatabaseHelper: constructed the DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp", "DatabaseHelper: creating db");
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData(String name, String phone, String address)
    {
        Log.d("MyContactApp", "DatabaseHelper: putting data in contentvalues");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, name);
        contentValues.put(COLUMN_NAME_PHONE, phone);
        contentValues.put(COLUMN_NAME_ADDRESS, address);
        Log.d("MyContactApp", "DatabaseHelper: inserting data into db");
        long result = db.insertOrThrow(DatabaseHelper.TABLE_NAME, null, contentValues);


        if (result == -1)
        {
            Log.d("MyContactApp", "DatabaseHelper: contact insert failed");
            return false;
        }
        else
        {
            Log.d("MyContactApp", "DatabaseHelper: contact insert successful");
            return true;
        }

    }

    public boolean editData(String id, String nameNew, String phoneNew, String addressNew)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res1 = this.getALlData();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, nameNew);
        contentValues.put(COLUMN_NAME_PHONE, phoneNew);
        contentValues.put(COLUMN_NAME_ADDRESS, addressNew);

        //while (res1.moveToNext())
        //{
            //if (Integer.parseInt(res1.getString(0).substring(4)) == id)
            //{
                long result = db.update(TABLE_NAME, contentValues,ID + "=" + id, null);
                if (result == -1) {
                    return false;
                }
                return true;
            //}
        //}
    }

    public Cursor getALlData()
    {
        Log.d("MyContactApp", "DatabaseHelper: getting data");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    public Cursor searchData(String srchN, String srchP, String srchA)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_NAME + " where name=\""+ srchN + "\"";
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}
