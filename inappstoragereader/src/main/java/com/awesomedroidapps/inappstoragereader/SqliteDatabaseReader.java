package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class which reads all the data from the database.
 * Created by anshul on 11/2/17.
 */

public class SqliteDatabaseReader {

  /**
   * This method returns all the table names of a particular database.
   *
   * @param context
   * @param databaseName
   * @return
   */
  public static List<AppDataStorageItem> readTablesList(Context context, String databaseName) {

    if (context == null) {
      return null;
    }

    ArrayList tablesList = new ArrayList();
    SQLiteDatabase sqLiteDatabase;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    Cursor cursor =
        sqLiteDatabase.rawQuery(SqliteConstants.RETRIEVE_ALL_TABLES_QUERY, null);
    if (cursor == null || !cursor.moveToFirst()) {
      return null;
    }

    do {
      AppDataStorageItem appDataStorageItem = new AppDataStorageItem();
      appDataStorageItem.setStorageType(StorageType.TABLE);
      try {
        appDataStorageItem.setStorageName(cursor.getString(Constants.ZERO_INDEX));
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
      tablesList.add(appDataStorageItem);
    } while (cursor.moveToNext());
    cursor.close();
    return tablesList;
  }


  /**
   * This method is used to return all the data of a particular table including the column names.
   *
   * @param context      - Context
   * @param databaseName - The name of the database.
   * @param tableName    - The name of the table.
   * @return
   */
  public static List<List<String>> getAllTableData(Context context, String
      databaseName, String tableName) {

    if (context == null || Utils.isEmpty(databaseName) || Utils.isEmpty(tableName)) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);

    if (cursor == null) {
      return null;
    }

    return getAllTableData(cursor);
  }

  @Nullable
  private static List<List<String>> getAllTableData(Cursor cursor) {

    if (cursor == null) {
      return null;
    }

    List<List<String>> tableData = new ArrayList();

    //Get the column names
    String[] columnNames = cursor.getColumnNames();
    if (columnNames == null || columnNames.length == 0) {
      return null;
    }
    ArrayList columnData = new ArrayList(Arrays.asList(columnNames));
    tableData.add(columnData);

    int columnCount = cursor.getColumnCount();

    if (!cursor.moveToFirst()) {
      return tableData;
    }
    do {
      ArrayList rowData = getRowData(cursor, columnCount);
      tableData.add(rowData);
    } while (cursor.moveToNext());

    cursor.close();
    return tableData;
  }

  public static String[] getColumnNames(Context context, String databaseName, String
      tableName) {

    if (context == null || Utils.isEmpty(databaseName) || Utils.isEmpty(tableName)) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);

    if (cursor == null) {
      return null;
    }


    //Get the column names
    String[] columnNames = cursor.getColumnNames();
    if (columnNames == null || columnNames.length == 0) {
      return null;
    }
    return columnNames;
  }

  /**
   * This method is used to return the data of an individual row.
   *
   * @param cursor
   * @param columnCount
   * @return
   */
  @NonNull
  private static ArrayList getRowData(Cursor cursor, int columnCount) {

    if (cursor == null || columnCount == 0) {
      return null;
    }

    ArrayList rowData = new ArrayList();

    for (int i = 0; i < columnCount; i++) {

      int columnType = cursor.getType(i);
      switch (columnType) {
        case Cursor.FIELD_TYPE_STRING:
          try {
            rowData.add(cursor.getString(i));
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case Cursor.FIELD_TYPE_INTEGER:
          try {
            rowData.add(Long.toString(cursor.getLong(i)));
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
        case Cursor.FIELD_TYPE_BLOB:
          String data;
          try {
            byte[] bdata = cursor.getBlob(i);
            data = new String(bdata);
          } catch (Exception e) {
            data = SqliteConstants.BLOB;
            e.printStackTrace();
          }
          rowData.add(data);
          break;
        case Cursor.FIELD_TYPE_FLOAT:
          try {
            rowData.add(Double.toString(cursor.getDouble(i)));
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;

        case Cursor.FIELD_TYPE_NULL:
          try {
            rowData.add(Constants.EMPTY_STRING);
          } catch (Exception e) {
            e.printStackTrace();
            break;
          }
      }
    }
    return rowData;
  }

  /**
   * This method is used to return an arraylist of widths of various columns. Based on the column
   * type the width will be different. e.g the width of a String column will be greater than the
   * width of an int column.
   *
   * @param context
   * @param databaseName
   * @param tableName
   * @return
   */
  @NonNull
  public static ArrayList<Integer> getTableDataColumnWidth(Context context, String databaseName,
                                                           String tableName) {
    if (context == null || Utils.isEmpty(databaseName) || Utils.isEmpty(tableName)) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase = null;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);

    if (cursor == null) {
      return null;
    }

    return getTableColumnWidth(context, cursor);
  }


  /**
   * This method is used to return an arraylist of widths of various columns. Based on the column
   * type the width will be different. e.g the width of a String column will be greater than the
   * width of an int column.
   *
   * @param context
   * @param databaseName
   * @param query
   * @return
   */
  @NonNull
  public static ArrayList<Integer> getQueryColumnWidth(Context context, String databaseName,
                                                       String query) {
    if (context == null || Utils.isEmpty(databaseName) || Utils.isEmpty(query)) {
      return null;
    }

    SQLiteDatabase sqLiteDatabase = null;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    Cursor cursor = null;

    try {
      cursor = sqLiteDatabase.rawQuery(query, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (cursor == null) {
      return null;
    }

    return getTableColumnWidth(context, cursor);
  }

  @NonNull
  private static ArrayList<Integer> getTableColumnWidth(Context context, Cursor cursor) {
    ArrayList<Integer> tableDataColumnWidth = new ArrayList<>();

    //If no data is present, then give default width to show all the column names.
    if (!cursor.moveToFirst()) {
      int defaultWidth = (int) context.getResources().getDimension(R.dimen
          .com_awesomedroidapps_inappstoragereader_database_item_string_width);
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
          width = Utils.getDimensionInInteger(context,
              R.dimen.com_awesomedroidapps_inappstoragereader_database_item_string_width);
          break;
        case Cursor.FIELD_TYPE_INTEGER:
          width = Utils.getDimensionInInteger(context,
              R.dimen.com_awesomedroidapps_inappstoragereader_database_item_int_width);
          break;
        case Cursor.FIELD_TYPE_BLOB:
          width = Utils.getDimensionInInteger(context,
              R.dimen.com_awesomedroidapps_inappstoragereader_database_item_blob_width);
          break;
        case Cursor.FIELD_TYPE_FLOAT:
          width = Utils.getDimensionInInteger(context,
              R.dimen.com_awesomedroidapps_inappstoragereader_database_item_float_width);
          break;
      }
      tableDataColumnWidth.add(width);
    }
    return tableDataColumnWidth;
  }

  public static QueryDataResponse queryDatabase(Context context, String databaseName,
                                                String query) {

    if (Utils.isEmpty(query)) {
      return null;
    }

    QueryDataResponse queryDataResponse = new QueryDataResponse();
    queryDataResponse.setQueryStatus(QueryStatus.FAILURE);

    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);

    String sqliteStatementType = query;
    if (query.contains(Constants.SPACE)) {
      int index = query.indexOf(Constants.SPACE);
      sqliteStatementType = query.substring(Constants.ZERO_INDEX, index).trim();
    }
    SqliteRawStatementsType sqliteRawStatementsType = SqliteRawStatementsType.getType
        (sqliteStatementType);

    switch (sqliteRawStatementsType) {
      case DELETE:
      case UPDATE:
        int modifiedRows = handleDeleteOrUpdateRawQuery(sqLiteDatabase, query, queryDataResponse);
        if (modifiedRows == Constants.INVALID_RESPONSE) {
        } else {
          queryDataResponse.setSuccessMessage("Success");
        }
        break;
      case SELECT:
        break;
      default:
        handleRawQuery(sqLiteDatabase, queryDataResponse, query);
    }
    return queryDataResponse;
  }

  /**
   * @return - The number of modified rows
   */
  private static int handleDeleteOrUpdateRawQuery(SQLiteDatabase sqLiteDatabase,
                                                  String deletQuery,
                                                  QueryDataResponse queryDataResponse) {
    SQLiteStatement statement = null;
    try {
      statement = sqLiteDatabase.compileStatement(deletQuery);
      return statement.executeUpdateDelete();
    } catch (Exception e) {
      queryDataResponse.setErrorMessage(e.getMessage());
      return Constants.INVALID_RESPONSE;
    } finally {
      if (statement != null) {
        statement.close();
      }
    }
  }

  private static void handleRawQuery(SQLiteDatabase sqliteDatabase, QueryDataResponse
      queryDataResponse, String query) {

    try {
      sqliteDatabase.execSQL(query);
    } catch (Exception e) {
      queryDataResponse.setErrorMessage(e.getMessage());
    }
  }
}
