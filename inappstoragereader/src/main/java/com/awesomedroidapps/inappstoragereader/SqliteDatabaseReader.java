package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class SqliteDatabaseReader {

  /**
   * This method will return a list of all the databases in the current application.
   * It will remove all the database names which ends with .journal
   *
   * @param context
   * @return
   */
  public static List readDatabaseList(Context context) {
    if (context == null) {
      return null;
    }

    ArrayList databaseList = new ArrayList(Arrays.asList(context.databaseList()));
    Iterator iterator = databaseList.iterator();
    while (iterator.hasNext()) {
      String databaseName = (String) iterator.next();
      if (databaseName.endsWith(SqliteConstants.JOURNAL_SUFFIX)) {
        iterator.remove();
      }
    }
    return databaseList;
  }

  public static List readTablesList(Context context, String databaseName) {

    if (context == null) {
      return null;
    }

    ArrayList tablesList = new ArrayList();
    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor =
        sqLiteDatabase.rawQuery(SqliteConstants.RETRIEVE_ALL_TABLES_QUERY, null);
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        tablesList.add(cursor.getString(0));
        cursor.moveToNext();
      }
    }
    return tablesList;
  }

  public static ArrayList getAllTableColumns(Context context, String databaseName, String
      tableName) {
    if (context == null) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor dbCursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    String[] columnNames = dbCursor.getColumnNames();
    return new ArrayList(Arrays.asList(columnNames));
  }

  public static ArrayList getAllTableData(Context context, String databaseName, String
      tableName) {
    if (context == null) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    if (cursor.moveToFirst()) {
      do {

      } while (cursor.moveToNext());
    }
    cursor.close();
    return null;
  }
}
