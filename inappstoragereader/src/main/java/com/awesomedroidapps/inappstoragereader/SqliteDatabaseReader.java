package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

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
  public static List<AppDataStorageItem> readAppDataStorageList(Context context) {
    if (context == null) {
      return null;
    }

    ArrayList appDataStorageList = new ArrayList();
    AppDataStorageItem appDataStorageItem = new AppDataStorageItem();
    appDataStorageItem.setStorageType(StorageType.SHARED_PREFERENCE);
    appDataStorageItem.setStorageName(Constants.SHARED_PREFERENCES_NAME);
    appDataStorageList.add(appDataStorageItem);

    ArrayList databaseList = new ArrayList(Arrays.asList(context.databaseList()));
    Iterator iterator = databaseList.iterator();
    while (iterator.hasNext()) {
      String databaseName = (String) iterator.next();
      if (databaseName.endsWith(SqliteConstants.JOURNAL_SUFFIX)) {
        continue;
      }
      appDataStorageItem = new AppDataStorageItem();
      appDataStorageItem.setStorageType(StorageType.DATABASE);
      appDataStorageItem.setStorageName(databaseName);
      appDataStorageList.add(appDataStorageItem);
    }


    return appDataStorageList;
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

  public static int getColumnCount(Context context, String databaseName, String tableName) {
    if (context == null) {
      return 0;
    }

    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    return cursor.getColumnCount();
  }

  public static ArrayList<ArrayList<String>> getAllTableData(Context context, String databaseName,
                                                             String tableName) {
    if (context == null) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    ArrayList<ArrayList<String>> tableData = new ArrayList();
    String[] columnNames = cursor.getColumnNames();
    ArrayList columnData = new ArrayList();
    for (int i = 0; i < columnNames.length; i++) {
      columnData.add(columnNames[i]);
    }
    tableData.add(columnData);

    int columnCount = cursor.getColumnCount();
    if (cursor.moveToFirst()) {
      //Beginning of the row
      do {
        ArrayList rowData = getRowData(cursor, columnCount);
        tableData.add(rowData);
      } while (cursor.moveToNext());
      //End of the row.
    }
    cursor.close();
    return tableData;
  }

  @NonNull
  private static ArrayList getRowData(Cursor cursor, int columnCount) {
    ArrayList rowData = new ArrayList();
    for (int i = 0; i < columnCount; i++) {
      int columnType = cursor.getType(i);
      switch (columnType) {
        case Cursor.FIELD_TYPE_STRING:
          rowData.add(cursor.getString(i));
          break;
        case Cursor.FIELD_TYPE_INTEGER:
          rowData.add(Integer.toString(cursor.getInt(i)));
          break;
        case Cursor.FIELD_TYPE_BLOB:
          rowData.add("blob");
          break;
        case Cursor.FIELD_TYPE_FLOAT:
          rowData.add(Float.toString(cursor.getFloat(i)));
      }
    }
    return rowData;
  }
}
