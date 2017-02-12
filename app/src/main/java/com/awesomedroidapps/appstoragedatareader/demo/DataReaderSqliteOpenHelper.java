package com.awesomedroidapps.appstoragedatareader.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.awesomedroidapps.appstoragedatareader.demo.entity.Person;

/**
 * Created by anshul on 11/2/17.
 */

public class DataReaderSqliteOpenHelper extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "person.db";
  public static final String TABLE_NAME = "person_info";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_SURNAME = "surname";
  public static final String COLUMN_AGE = "age";
  public static final String COLUMN_OCCUPATION = "occupation";
  public static final String COLUMN_INTERESTS = "interests";

  private SQLiteDatabase sqLiteDatabase;

  private static final String SQL_CREATE_ENTRIES =
      "CREATE TABLE " + TABLE_NAME + " (" +
          COLUMN_ID + " INTEGER PRIMARY KEY," +
          COLUMN_NAME + " TEXT," +
          COLUMN_SURNAME + " TEXT," +
          COLUMN_AGE + " INTEGER," +
          COLUMN_OCCUPATION + " TEXT," +
          COLUMN_INTERESTS + " TEXT)";

  public DataReaderSqliteOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void insert(Person person) {
// Gets the data repository in write mode
    SQLiteDatabase db = getWritableDatabase();

// Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, person.getName());
    values.put(COLUMN_SURNAME, person.getSurName());

    //Hardcoded values
    values.put(COLUMN_AGE,35);
    values.put(COLUMN_OCCUPATION, "Student");
    values.put(COLUMN_INTERESTS, "Reading, writing, exploring new avenues are my interests " +
        "primarily");

    db.insert(TABLE_NAME, null, values);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    System.out.println("inside on Create");
    System.out.println(SQL_CREATE_ENTRIES);
    sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //Do nothing
  }
}
