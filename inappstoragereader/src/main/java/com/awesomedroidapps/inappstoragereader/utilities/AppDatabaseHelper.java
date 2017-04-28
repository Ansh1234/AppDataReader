package com.awesomedroidapps.inappstoragereader.utilities;

import android.database.Cursor;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.util.List;

/**
 * Created by anshul on 27/04/17.
 */

public class AppDatabaseHelper {

  public static String getUpdateQuery(List<String> tableColumnNames, List<Integer>
      tableColumnTypes, List<String> columnValues, int columnIndex, String newValue,
                                      String tableName) {

    if (tableColumnNames == null || columnValues == null) {
      return Constants.EMPTY_STRING;
    }

    String toUpdateColumn = tableColumnNames.get(columnIndex);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Constants.SPACE);
    stringBuilder.append(Constants.WHERE_CLAUSE);
    stringBuilder.append(Constants.SPACE);

    for (int i = 0; i < columnValues.size(); i++) {

      if (tableColumnTypes.get(i) == Cursor.FIELD_TYPE_NULL || tableColumnTypes.get(i)==Cursor.FIELD_TYPE_BLOB) {
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
    int toUpdateColumnType = tableColumnTypes.get(columnIndex);
    if (toUpdateColumnType == Cursor.FIELD_TYPE_STRING) {
      newValue = Constants.INVERTED_COMMA + newValue + Constants.INVERTED_COMMA;
    }
    whereClause = whereClause.trim();

    String updateQuery =
        "update " + tableName + " set " + toUpdateColumn + "=" + newValue + Constants
            .SPACE + whereClause;
    return updateQuery;
  }


}
