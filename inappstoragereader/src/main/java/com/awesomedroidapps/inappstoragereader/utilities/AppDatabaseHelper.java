package com.awesomedroidapps.inappstoragereader.utilities;

import android.content.ContentValues;
import android.database.Cursor;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DatabaseColumnType;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.util.List;

/**
 * Created by anshul on 27/04/17.
 */

public class AppDatabaseHelper {

  public static ContentValues getUpdateQuery(List<String> tableColumnNames, List<DatabaseColumnType>
      tableColumnTypes, List<String> columnValues, int columnIndex, String newValue) {

    if (tableColumnNames == null || columnValues == null) {
      return null;
    }

    String columnName = tableColumnNames.get(columnIndex);

    ContentValues contentValues = new ContentValues();
    switch (tableColumnTypes.get(columnIndex)){
      case FIELD_TYPE_BLOB:
        contentValues.put(columnName,newValue.getBytes());
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
      tableColumnTypes, List<String> columnValues,List<Integer> primaryKeyList){
    if (tableColumnNames == null || columnValues == null) {
      return null;
    }


    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < columnValues.size(); i++) {

      if (tableColumnTypes.get(i)==
          DatabaseColumnType.FIELD_TYPE_BLOB || (!Utils.isEmpty(primaryKeyList) &&
          !primaryKeyList.contains(i))) {
        continue;
      }

      String columnName = tableColumnNames.get(i);
      if(Utils.isEmpty(columnName)){
        continue;
      }

      stringBuilder.append(columnName);
      stringBuilder.append(Constants.EQUAL);
      if (tableColumnTypes.get(i).equals(Cursor.FIELD_TYPE_STRING)) {
        stringBuilder.append(Constants.INVERTED_COMMA);
      }
      stringBuilder.append(columnValues.get(i));
      if (tableColumnTypes.get(i).equals(Cursor.FIELD_TYPE_STRING)) {
        stringBuilder.append(Constants.INVERTED_COMMA);
      }
      if (i == columnValues.size() - 1) {
        break;
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
