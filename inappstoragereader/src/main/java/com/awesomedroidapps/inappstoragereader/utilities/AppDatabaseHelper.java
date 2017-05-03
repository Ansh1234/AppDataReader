package com.awesomedroidapps.inappstoragereader.utilities;

import android.content.ContentValues;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DatabaseColumnType;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.util.List;

/**
 * Created by anshul on 27/04/17.
 */

public class AppDatabaseHelper {

  public static ContentValues getContentValues(List<String> tableColumnNames,
                                               List<DatabaseColumnType> tableColumnTypes,
                                               int columnIndex,
                                               String newValue, ContentValues contentValues) {

    if (tableColumnNames == null) {
      return null;
    }

    String columnName = tableColumnNames.get(columnIndex);

    if (contentValues == null) {
      contentValues = new ContentValues();
    }
    switch (tableColumnTypes.get(columnIndex)) {
      case FIELD_TYPE_BLOB:
        contentValues.put(columnName, newValue.getBytes());
        break;
      case FIELD_TYPE_FLOAT:
        contentValues.put(columnName, Float.parseFloat(newValue));
        break;
      case FIELD_TYPE_INTEGER:
        contentValues.put(columnName, Integer.parseInt(newValue));
        break;
      case FIELD_TYPE_TEXT:
        contentValues.put(columnName, newValue);
        break;
    }

    return contentValues;
  }

  public static String getUpdateWhereClause(List<String> tableColumnNames, List<DatabaseColumnType>
      tableColumnTypes, List<String> columnValues, List<Integer> primaryKeyList) {
    if (tableColumnNames == null || columnValues == null) {
      return null;
    }


    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < columnValues.size(); i++) {

      DatabaseColumnType databaseColumnType = tableColumnTypes.get(i);
      if (databaseColumnType ==
          DatabaseColumnType.FIELD_TYPE_BLOB || (!Utils.isEmpty(primaryKeyList) &&
          !primaryKeyList.contains(i))) {
        continue;
      }

      String columnName = tableColumnNames.get(i);
      String columnValue = columnValues.get(i);

      if (Utils.isEmpty(columnValue)) {
        continue;
      }

      stringBuilder.append(columnName);

      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_FLOAT ||
          databaseColumnType == DatabaseColumnType.FIELD_TYPE_INTEGER) {
        if (Utils.isEmpty(columnValue)) {
          stringBuilder.append(Constants.SPACE);
          stringBuilder.append("IS NULL ");
          stringBuilder.append(Constants.AND);
          stringBuilder.append(Constants.SPACE);
          continue;
        }
      }

      stringBuilder.append(Constants.EQUAL);
      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_TEXT) {
        stringBuilder.append(Constants.INVERTED_COMMA);
      }
      stringBuilder.append(columnValue);
      if (databaseColumnType == DatabaseColumnType.FIELD_TYPE_TEXT) {
        stringBuilder.append(Constants.INVERTED_COMMA);
      }

      stringBuilder.append(Constants.SPACE);
      stringBuilder.append(Constants.AND);
      stringBuilder.append(Constants.SPACE);
    }

    String whereClause = stringBuilder.toString();
    whereClause = whereClause.trim();
    if (whereClause.endsWith(Constants.AND)) {
      int lastIndex = whereClause.lastIndexOf(Constants.AND);
      whereClause = whereClause.substring(0, lastIndex);
      whereClause = whereClause.trim();
    }

    whereClause = whereClause.trim();
    return whereClause;
  }

}
