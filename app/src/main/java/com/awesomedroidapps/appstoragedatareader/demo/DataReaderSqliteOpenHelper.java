package com.awesomedroidapps.appstoragedatareader.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.awesomedroidapps.appstoragedatareader.demo.entity.PersonInfo;

/**
 * Created by anshul on 11/2/17.
 */

public class DataReaderSqliteOpenHelper extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "person.db";
  public static final String TABLE_NAME = "person_info";
  public static final String COLUMN_ID = "uniqueId";
  public static final String COLUMN_FIRST_NAME = "first_name";
  public static final String COLUMN_LAST_NAME = "last_name";
  public static final String COLUMN_ADDRESS = "address";


  private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
      COLUMN_ID + " TEXT," +
      COLUMN_FIRST_NAME + " TEXT," +
      COLUMN_LAST_NAME + " TEXT," +
      COLUMN_ADDRESS + " TEXT)";

  public DataReaderSqliteOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public long insert(PersonInfo personInfo) {
    // Gets the data repository in write mode
    SQLiteDatabase db = getWritableDatabase();

    if (db == null || personInfo == null) {
      return Constants.INVALID_INSERT_RESPONSE;
    }

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();
    values.put(COLUMN_FIRST_NAME, personInfo.getFirstName());
    values.put(COLUMN_LAST_NAME, personInfo.getLastName());
    values.put(COLUMN_ADDRESS, personInfo.getAddress());

    return db.insert(TABLE_NAME, null, values);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    System.out.println(SQL_CREATE_ENTRIES);
    sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //Do nothing
  }
}
