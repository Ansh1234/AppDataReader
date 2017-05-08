package com.awesomedroidapps.inappstoragereader;

import java.util.List;

/**
 * Created by anshul on 29/04/17.
 */

public enum DatabaseColumnType {


  FIELD_TYPE_INTEGER(Constants.integerTypes, 1),

  FIELD_TYPE_FLOAT(Constants.floatTypes, 2),

  FIELD_TYPE_TEXT(Constants.textTypes, 3),

  FIELD_TYPE_BLOB(Constants.blobTypes, 4);

  private int value;
  private List typeList;

  DatabaseColumnType(List typeList, int value) {
    this.typeList = typeList;
    this.value = value;
  }

  public static DatabaseColumnType getType(String strValue) {
    for (DatabaseColumnType type : DatabaseColumnType.values()) {
      List list = type.typeList;
      if (list.contains(strValue.toLowerCase())) {
        return type;
      }
    }
    return null;
  }
}
