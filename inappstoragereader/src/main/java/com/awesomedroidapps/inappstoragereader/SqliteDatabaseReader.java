package com.awesomedroidapps.inappstoragereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseRequest;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseResponse;
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

  public static QueryDatabaseResponse queryDatabase(Context context,
                                                    QueryDatabaseRequest queryDatabaseRequest) {

    QueryDatabaseResponse queryDatabaseResponse = new QueryDatabaseResponse();

    if (queryDatabaseRequest == null) {
      fillQueryResponseWithUnExpectedError(context, queryDatabaseResponse);
      return queryDatabaseResponse;
    }

    String databaseName = queryDatabaseRequest.getDatabaseName();
    if (context == null || queryDatabaseRequest == null || Utils.isEmpty(databaseName)) {
      fillQueryResponseWithUnExpectedError(context, queryDatabaseResponse);
      return queryDatabaseResponse;
    }

    SQLiteDatabase sqLiteDatabase;
    try {
      sqLiteDatabase = context.openOrCreateDatabase(databaseName, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
      fillQueryResponseWithError(queryDatabaseResponse, e);
      return queryDatabaseResponse;
    }

    DatabaseQueryCommandType commandType = queryDatabaseRequest.getDatabaseQueryCommandType();

    if (commandType == null) {
      queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
      String errorMessage = Utils.getString(context, R.string
          .com_awesomedroidapps_inappstoragereader_database_query_invalid);
      queryDatabaseResponse.setErrorMessage(errorMessage);
      return queryDatabaseResponse;
    }

    //Set the command type in the response for updating the UI properly.
    queryDatabaseResponse.setDatabaseQueryCommandType(commandType);

    switch (commandType) {
      case SELECT:
        handleSelectQuery(context, sqLiteDatabase, queryDatabaseRequest, queryDatabaseResponse);
        break;

      case UPDATE:
        handleUpdateQuery(context, sqLiteDatabase, queryDatabaseRequest, queryDatabaseResponse);
        break;

      case DELETE:
        handleDeleteQuery(context, sqLiteDatabase, queryDatabaseRequest, queryDatabaseResponse);
        break;

      case INSERT:
        handleInsertQuery(context, sqLiteDatabase, queryDatabaseRequest, queryDatabaseResponse);
        break;

      case RAW_QUERY:
        handleRawQuery(context, sqLiteDatabase, queryDatabaseRequest, queryDatabaseResponse);
    }
    return queryDatabaseResponse;
  }

  /**
   * This function handles Delete Query.
   *
   * @param context               - Context of the application.
   * @param sqLiteDatabase        - SQLite Database
   * @param queryDatabaseRequest  - The request coming from the UI.
   * @param queryDatabaseResponse - The response posted back to the UI.
   */
  private static void handleDeleteQuery(Context context,
                                        SQLiteDatabase sqLiteDatabase,
                                        QueryDatabaseRequest queryDatabaseRequest,
                                        QueryDatabaseResponse queryDatabaseResponse) {

    String whereClause = queryDatabaseRequest.getWhereClause();
    String tableName = queryDatabaseRequest.getTableName();
    if (Utils.isEmpty(tableName)) {
      fillQueryResponseWithUnExpectedError(context, queryDatabaseResponse);
      return;
    }

    long affectedRows = -1;
    try {
      affectedRows = sqLiteDatabase.delete(tableName, whereClause, null);
      queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
      queryDatabaseResponse.setAffectedRows(affectedRows);
    } catch (Exception e) {
      queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
      queryDatabaseResponse.setErrorMessage(e.getMessage());
    }
  }

  /**
   * This function handles Delete Query.
   *
   * @param context               - Context of the application.
   * @param sqLiteDatabase        - SQLite Database
   * @param queryDatabaseRequest  - The request coming from the UI.
   * @param queryDatabaseResponse - The response posted back to the UI.
   */
  private static void handleInsertQuery(Context context,
                                        SQLiteDatabase sqLiteDatabase,
                                        QueryDatabaseRequest queryDatabaseRequest,
                                        QueryDatabaseResponse queryDatabaseResponse) {
    ContentValues contentValues = queryDatabaseRequest.getContentValues();
    long affectedRows = -1;
    try {
      String tableName = queryDatabaseRequest.getTableName();
      affectedRows = sqLiteDatabase.insert(tableName, null, contentValues);
      queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
      queryDatabaseResponse.setAffectedRows(affectedRows);
    } catch (Exception e) {
      queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
      queryDatabaseResponse.setErrorMessage(e.getMessage());
    }
  }

  /**
   * This function handles Update Query.
   *
   * @param context               - Context of the application.
   * @param sqLiteDatabase        - SQLite Database
   * @param queryDatabaseRequest  - The request coming from the UI.
   * @param queryDatabaseResponse - The response posted back to the UI.
   */
  private static void handleUpdateQuery(Context context,
                                        SQLiteDatabase sqLiteDatabase,
                                        QueryDatabaseRequest queryDatabaseRequest,
                                        QueryDatabaseResponse queryDatabaseResponse) {

    long affectedRows = -1;

    ContentValues contentValues = queryDatabaseRequest.getContentValues();
    String whereClause = queryDatabaseRequest.getWhereClause();

    if (contentValues == null && Utils.isEmpty(whereClause)) {
      queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
      queryDatabaseResponse.setAffectedRows(affectedRows);
      String errorMessage = Utils.getString(context, R.string
          .com_awesomedroidapps_inappstoragereader_generic_error);
      queryDatabaseResponse.setErrorMessage(errorMessage);
    }

    String tableName = queryDatabaseRequest.getTableName();

    if (!Utils.isEmpty(tableName)) {
      try {
        affectedRows = sqLiteDatabase.update(tableName, contentValues, whereClause, null);
        queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
        queryDatabaseResponse.setAffectedRows(affectedRows);
      } catch (Exception e) {
        fillQueryResponseWithError(queryDatabaseResponse, e);
        e.printStackTrace();
      }
      return;
    }

    if (Utils.isEmpty(queryDatabaseRequest.getRawQuery())) {
      queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
      String errorMessage = Utils.getString(context, R.string
          .com_awesomedroidapps_inappstoragereader_database_query_empty);
      queryDatabaseResponse.setErrorMessage(errorMessage);
      queryDatabaseResponse.setAffectedRows(affectedRows);
    }

    Cursor cursor = null;
    try {
      cursor = sqLiteDatabase.rawQuery(queryDatabaseRequest.getRawQuery(), null);
      affectedRows = getAffectedRows(cursor);
      queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
    } catch (Exception e) {
      fillQueryResponseWithError(queryDatabaseResponse, e);
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  private static long getAffectedRows(Cursor cursor) {
    long affectedRows = -1;
    try {
      if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
        int columnIndex = cursor.getColumnIndex(Constants.QUERY_AFFECTED_ROWS);
        if (columnIndex < 0) {
          return affectedRows;
        }
        affectedRows = cursor.getLong(columnIndex);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return affectedRows;
  }

  /**
   * This function handles select Query.
   *
   * @param context               - Context of the application.
   * @param sqliteDatabase        - SQLite Database
   * @param queryDatabaseRequest  - The request coming from the UI.
   * @param queryDatabaseResponse - The response posted back to the UI.
   */
  private static void handleSelectQuery(@NonNull Context context,
                                        @NonNull SQLiteDatabase sqliteDatabase,
                                        @NonNull QueryDatabaseRequest queryDatabaseRequest,
                                        @NonNull QueryDatabaseResponse queryDatabaseResponse) {

    String selectQuery = queryDatabaseRequest.getSelectQuery();

    if (Utils.isEmpty(selectQuery)) {
      fillQueryResponseWithUnExpectedError(context, queryDatabaseResponse);
      return;
    }

    Cursor cursor = null;
    try {

      cursor = sqliteDatabase.rawQuery(selectQuery, null);
      if (cursor == null) {
        queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
        queryDatabaseResponse.setAffectedRows(Constants.ZERO_INDEX);
        return;
      }

      TableDataResponse tableDataResponse = new TableDataResponse();

      List<List<String>> allTableData = getAllTableData(cursor);

      if (Utils.isEmpty(allTableData)) {
        queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
        queryDatabaseResponse.setAffectedRows(Constants.ZERO_INDEX);
        return;
      }

      tableDataResponse.setTableData(allTableData);

      ArrayList<Integer> tableColumnsWidth = getTableColumnWidth(context, cursor);
      tableDataResponse.setRecyclerViewColumnsWidth(tableColumnsWidth);
      tableDataResponse.setRecyclerViewWidth(Utils.getTableWidth(tableColumnsWidth));

      queryDatabaseResponse.setTableDataResponse(tableDataResponse);
      queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
      queryDatabaseResponse.setAffectedRows(cursor.getCount());

    } catch (Exception e) {
      fillQueryResponseWithError(queryDatabaseResponse, e);
    } finally {
      closeCursor(cursor);
    }
  }


  private static void handleRawQuery(Context context,
                                     SQLiteDatabase sqliteDatabase,
                                     QueryDatabaseRequest queryDatabaseRequest,
                                     QueryDatabaseResponse queryDatabaseResponse) {

    Cursor cursor = null;
    try {
      String rawQuery = queryDatabaseRequest.getRawQuery();
      cursor = sqliteDatabase.rawQuery(rawQuery, null);
      if (cursor != null && cursor.getCount() > 0) {
        TableDataResponse tableDataResponse = new TableDataResponse();
        List<List<String>> data = getAllTableData(cursor);
        tableDataResponse.setTableData(data);
        queryDatabaseResponse.setTableDataResponse(tableDataResponse);
        List<Integer> tableColumnWidth = getTableColumnWidth(context, cursor);
        tableDataResponse.setRecyclerViewColumnsWidth(tableColumnWidth);
        //Get the width
        int recyclerViewWidth = Utils.getTableWidth(tableColumnWidth);
        tableDataResponse.setRecyclerViewWidth(recyclerViewWidth);
      }
      long affectedRows = getAffectedRows(cursor);
      queryDatabaseResponse.setQueryStatus(QueryStatus.SUCCESS);
      queryDatabaseResponse.setAffectedRows(affectedRows);
    } catch (Exception e) {
      fillQueryResponseWithError(queryDatabaseResponse, e);
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  private static void fillQueryResponseWithError(
      @NonNull QueryDatabaseResponse queryDatabaseResponse, Exception e) {
    queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
    queryDatabaseResponse.setErrorMessage(e.getMessage());
  }

  private static void closeCursor(Cursor cursor) {
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
  }

  private static void fillQueryResponseWithUnExpectedError(Context context, QueryDatabaseResponse
      queryDatabaseResponse) {
    queryDatabaseResponse.setQueryStatus(QueryStatus.FAILURE);
    String errorMessage = Utils.getString(context, R.string
        .com_awesomedroidapps_inappstoragereader_generic_error);
    queryDatabaseResponse.setErrorMessage(errorMessage);
  }
}
