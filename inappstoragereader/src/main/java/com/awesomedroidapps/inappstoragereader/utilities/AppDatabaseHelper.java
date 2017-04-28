package com.awesomedroidapps.inappstoragereader.utilities;

import android.database.Cursor;

import com.awesomedroidapps.inappstoragereader.Constants;

import java.util.List;

/**
 * Created by anshul on 27/04/17.
 */

public class AppDatabaseHelper {

  public static String getUpdateQuery(List<String> tableColumnNames, List<Integer>
      tableColumnTypes, List<String> columnValues, int columnIndex, String newValue, String tableName){

    String toUpdateColumn = tableColumnNames.get(columnIndex);

    String whereClause = Constants.EMPTY_STRING;
    StringBuilder stringBuilder = new StringBuilder(" WHERE ");
    for (int i = 0; i < columnValues.size(); i++) {
      if (tableColumnTypes.get(i) == Cursor.FIELD_TYPE_NULL) {
        continue;
      }
      stringBuilder.append(tableColumnNames.get(i));
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
    whereClause = stringBuilder.toString();
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
