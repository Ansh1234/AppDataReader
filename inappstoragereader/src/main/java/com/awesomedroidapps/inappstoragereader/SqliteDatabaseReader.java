package com.awesomedroidapps.inappstoragereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseRequest;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;

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

  public static List getColumnTypes(Context context, String databaseName, String
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


    ArrayList<Integer> tableColumnDataTypes = new ArrayList<>();

    //If no data is present, then give default width to show all the column names.
    if (!cursor.moveToFirst()) {
      int defaultWidth = (int) context.getResources().getDimension(R.dimen
          .com_awesomedroidapps_inappstoragereader_database_item_string_width);
      for (int i = 0; i < cursor.getColumnCount(); i++) {
        tableColumnDataTypes.add(defaultWidth);
      }
      return tableColumnDataTypes;
    }

    for (int i = 0; i < cursor.getColumnCount(); i++) {
      int columnType = cursor.getType(i);
      tableColumnDataTypes.add(columnType);
    }
    return tableColumnDataTypes;
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

  @NonNull
  public static ArrayList<DatabaseColumnType> getTableDataColumnTypes(Context context,
                                                                      String databaseName,
                                                                      String tableName) {
    String query = "pragma table_info(" + tableName + ")";
    SQLiteDatabase sqLiteDatabase;
    ArrayList<DatabaseColumnType> primaryKeyList = new ArrayList<>();

    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return primaryKeyList;
    }

    Cursor cursor = null;
    try {
      cursor = sqLiteDatabase.rawQuery(query, null);
    } catch (Exception e) {
      e.printStackTrace();
      return primaryKeyList;
    }

    if (cursor == null || !cursor.moveToFirst()) {
      return primaryKeyList;
    }

    int primaryKeyColumnIndex = cursor.getColumnIndex(Constants.PRAGMA_COLUMN_TYPE);
    do {
      DatabaseColumnType databaseColumnType = DatabaseColumnType.getType(cursor.getString
          (primaryKeyColumnIndex));
      primaryKeyList.add(databaseColumnType);
    } while (cursor.moveToNext());
    cursor.close();
    return primaryKeyList;
  }

  @NonNull
  public static ArrayList<Integer> getTableDataPrimaryKey(Context context, String databaseName,
                                                          String tableName) {
    String query = "pragma table_info(" + tableName + ")";
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Integer> primaryKeyList = new ArrayList<>();

    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      return primaryKeyList;
    }

    Cursor cursor = null;
    try {
      cursor = sqLiteDatabase.rawQuery(query, null);
    } catch (Exception e) {
      e.printStackTrace();
      return primaryKeyList;
    }

    if (cursor == null || !cursor.moveToFirst()) {
      return primaryKeyList;
    }

    int primaryKeyColumnIndex = cursor.getColumnIndex(Constants.PRAGMA_COLUMN_PK);
    int cidIndex = cursor.getColumnIndex(Constants.PRAGMA_COLUMN_CID);

    do {
      int value = cursor.getInt(primaryKeyColumnIndex);
      if (value == Constants.COLUMN_PRIMARY_KEY_VALUE) {
        primaryKeyList.add(cursor.getInt(cidIndex));
      }
    } while (cursor.moveToNext());
    cursor.close();
    return primaryKeyList;
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

    int width = (int) context.getResources().getDimension(R.dimen
        .com_awesomedroidapps_inappstoragereader_database_item_string_width);
    //If no data is present, then give default width to show all the column names.
    if (!cursor.moveToFirst()) {
      for (int i = 0; i < cursor.getColumnCount(); i++) {
        tableDataColumnWidth.add(width);
      }
      return tableDataColumnWidth;
    }

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
        case Cursor.FIELD_TYPE_NULL:
          width = (int) context.getResources().getDimension(R.dimen
              .com_awesomedroidapps_inappstoragereader_database_item_string_width);
          break;

      }
      tableDataColumnWidth.add(width);
    }
    return tableDataColumnWidth;
  }

  public static QueryDataResponse queryDatabase(Context context, QueryDatabaseRequest
      queryDatabaseRequest, String databaseName, String tableName, String query) {

    QueryDataResponse queryDataResponse = new QueryDataResponse();
    String errorMessage = Utils.getString(context, R.string
        .com_awesomedroidapps_inappstoragereader_database_query_failed);
    queryDataResponse.setQueryStatus(QueryStatus.FAILURE);

    SQLiteDatabase sqLiteDatabase;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      queryDataResponse.setErrorMessage(errorMessage);
      return queryDataResponse;
    }


    DatabaseQueryCommandType commandType = queryDatabaseRequest.getDatabaseQueryCommandType();
    switch (commandType) {
      case SELECT:
        queryDataResponse.setDatabaseQueryCommandType(DatabaseQueryCommandType.SELECT);
        String selectQuery = queryDatabaseRequest.getSelectQuery();
        handleRawQuery(context, sqLiteDatabase, queryDataResponse, query);
        break;

      case UPDATE:
        queryDataResponse.setDatabaseQueryCommandType(DatabaseQueryCommandType.UPDATE);
        int updatedRows = handleUpdateAndDeleteAndIndexQuery(queryDatabaseRequest, context,
          databaseName,
            queryDataResponse, tableName,
            queryDatabaseRequest.getContentValues(), queryDatabaseRequest.getWhereClause(),
            commandType);
        if (updatedRows == Constants.INVALID_RESPONSE) {
        } else {
          queryDataResponse.setSuccessMessage("Success");
        }
        break;

      case DELETE:
        queryDataResponse.setDatabaseQueryCommandType(DatabaseQueryCommandType.DELETE);
        int deletedRows = handleDeleteOrUpdateRawQuery(sqLiteDatabase, query, queryDataResponse);
        if (deletedRows == Constants.INVALID_RESPONSE) {
        } else {
          queryDataResponse.setSuccessMessage("Success");
        }
        break;

      case INSERT:
        break;

      case RAW_QUERY:
      default:
        handleRawQuery(context, sqLiteDatabase, queryDataResponse, query);
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
      int response = statement.executeUpdateDelete();
      queryDataResponse.setQueryStatus(QueryStatus.SUCCESS);
      return response;
    } catch (Exception e) {
      queryDataResponse.setErrorMessage(e.getMessage());
      return Constants.INVALID_RESPONSE;
    } finally {
      if (statement != null) {
        statement.close();
      }
    }
  }

  public static int handleUpdateAndDeleteAndIndexQuery(QueryDatabaseRequest queryDatabaseRequest,
                                                       Context context,
                                                       String databaseName,
                                                       QueryDataResponse queryDataResponse,
                                                       String tableName,
                                                       ContentValues contentValues,
                                                       String whereClause,
                                                       DatabaseQueryCommandType type) {

    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    long affectedRows = -1;
    try {

      switch (type) {
        case UPDATE:
          affectedRows = handleUpdateQuery(queryDatabaseRequest, sqLiteDatabase, queryDataResponse,
            tableName,
              contentValues, whereClause, type);
          break;
        case DELETE:
          affectedRows = sqLiteDatabase.delete(tableName, whereClause, null);
          break;
        case INSERT:
          affectedRows = sqLiteDatabase.insert(tableName, null, contentValues);
          break;
      }
      queryDataResponse.setQueryStatus(QueryStatus.SUCCESS);
      return (int) affectedRows;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (int)affectedRows;
  }

  private static int handleUpdateQuery(QueryDatabaseRequest queryDatabaseRequest,
                                       SQLiteDatabase sqLiteDatabase,
                                       QueryDataResponse queryDataResponse,
                                       String tableName,
                                       ContentValues contentValues,
                                       String whereClause,
                                       DatabaseQueryCommandType type) {
    long affectedRows = -1;
    if (Utils.isEmpty(tableName)) {

      Cursor cursor = sqLiteDatabase.rawQuery(queryDatabaseRequest.getRawQuery(), null);
      try {
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
          affectedRows = cursor.getLong(cursor.getColumnIndex("affected_row_count"));
        } else {
          // Some error occurred?
        }
      } catch (SQLException e) {
        // Handle exception here.
      } finally {
        if (cursor != null) {
          cursor.close();
        }
      }
    } else {
      affectedRows = sqLiteDatabase.update(tableName, contentValues, whereClause, null);
    }
    return (int) affectedRows;
  }


  private static void handleSelectQuery(Context context, SQLiteDatabase sqliteDatabase,
                                        QueryDataResponse queryDataResponse, String query) {
    Cursor cursor = null;
    try {
      cursor = sqliteDatabase.rawQuery(query, null);
      TableDataResponse tableDataResponse = new TableDataResponse();
      tableDataResponse.setTableData(getAllTableData(cursor));
      ArrayList<Integer> tableColumnWidth = getTableColumnWidth(context, cursor);
      tableDataResponse.setRecyclerViewColumnsWidth(tableColumnWidth);
      tableDataResponse.setRecyclerViewWidth(Utils.getTableWidth(tableColumnWidth));
      queryDataResponse.setTableDataResponse(tableDataResponse);
      queryDataResponse.setQueryStatus(QueryStatus.SUCCESS);

    } catch (Exception e) {
      queryDataResponse.setErrorMessage(e.getMessage());
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  private static void handleRawQuery(Context context, SQLiteDatabase sqliteDatabase,
                                     QueryDataResponse queryDataResponse, String query) {
    Cursor cursor = null;
    try {
      cursor = sqliteDatabase.rawQuery(query, null);
      TableDataResponse tableDataResponse = new TableDataResponse();
      tableDataResponse.setTableData(getAllTableData(cursor));
      ArrayList<Integer> tableColumnWidth = getTableColumnWidth(context, cursor);
      tableDataResponse.setRecyclerViewColumnsWidth(tableColumnWidth);
      tableDataResponse.setRecyclerViewWidth(Utils.getTableWidth(tableColumnWidth));
      queryDataResponse.setTableDataResponse(tableDataResponse);
      queryDataResponse.setQueryStatus(QueryStatus.SUCCESS);

    } catch (Exception e) {
      queryDataResponse.setErrorMessage(e.getMessage());
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }
}
