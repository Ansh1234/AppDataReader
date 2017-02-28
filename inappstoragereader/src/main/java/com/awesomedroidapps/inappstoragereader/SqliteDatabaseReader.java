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


  public static List<AppDataStorageItem> readTablesList(Context context, String databaseName) {

    if (context == null) {
      return null;
    }

    ArrayList tablesList = new ArrayList();
    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor =
        sqLiteDatabase.rawQuery(SqliteConstants.RETRIEVE_ALL_TABLES_QUERY, null);
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        AppDataStorageItem appDataStorageItem = new AppDataStorageItem();
        appDataStorageItem.setStorageType(StorageType.TABLE);
        appDataStorageItem.setStorageName(cursor.getString(0));
        tablesList.add(appDataStorageItem);
        cursor.moveToNext();
      }
    }
    return tablesList;
  }

  public static void queryDatabase(Context context, String databaseName, String query) {
    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if(!cursor.moveToFirst()){

    }
    while (cursor.moveToNext()) {

    }
  }

  public static ArrayList<ArrayList<String>> getAllTableData(Context context, String
      databaseName,
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
          rowData.add(SqliteConstants.BLOB);
          break;
        case Cursor.FIELD_TYPE_FLOAT:
          rowData.add(Float.toString(cursor.getFloat(i)));
      }
    }
    return rowData;
  }

  @NonNull
  public static ArrayList<Integer> getTableDataColumnWidth(Context context, String databaseName,
                                                           String
                                                               tableName) {
    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    ArrayList<Integer> tableDataColumnWidth = new ArrayList<>();

    if (!cursor.moveToFirst()) {
      int defaultWidth = (int) context.getResources().getDimension(R.dimen
          .database_item_string_width);
      for (int i = 0; i < cursor.getColumnCount(); i++) {
        tableDataColumnWidth.add(defaultWidth);
      }
      return tableDataColumnWidth;
    }

    int width = 0;
    for (int i = 0; i < cursor.getColumnCount(); i++) {
      int columnType = cursor.getType(i);
      switch (columnType) {
        case Cursor.FIELD_TYPE_STRING:
          width = (int) context.getResources().getDimension(R.dimen
              .database_item_string_width);
          break;
        case Cursor.FIELD_TYPE_INTEGER:
          width = width + (int) context.getResources().getDimension(R.dimen
              .database_item_int_width);
          break;
        case Cursor.FIELD_TYPE_BLOB:
          width = width + (int) context.getResources().getDimension(R.dimen
              .database_item_blob_width);
          break;
        case Cursor.FIELD_TYPE_FLOAT:
          width = width + (int) context.getResources().getDimension(R.dimen
              .database_item_float_width);
      }
      tableDataColumnWidth.add(width);
    }
    return tableDataColumnWidth;
  }

  public static int getTableWidth(ArrayList<Integer> tableDataColumnWidth) {
    int width = 0;
    if (tableDataColumnWidth == null) {
      return width;
    }

    for (Integer i : tableDataColumnWidth) {
      width = width + i;
    }
    return width;
  }
}
