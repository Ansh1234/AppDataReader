package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 29/04/17.
 */

public enum DatabaseColumnType {

  FIELD_TYPE_INTEGER("INTEGER", 1),

  FIELD_TYPE_FLOAT("FLOAT", 2),

  FIELD_TYPE_TEXT("TEXT", 3),

  FIELD_TYPE_BLOB("BLOB", 4);

  private int value;
  private String strValue;

  DatabaseColumnType(String strValue, int value) {
    this.strValue = strValue;
    this.value = value;
  }

  public String getStrValue() {
    return strValue;
  }

  public static DatabaseColumnType getType(String strValue){
    for(DatabaseColumnType type : DatabaseColumnType.values()){
      if(type.getStrValue().equals(strValue)){
        return type;
      }
    }
    return null;
  }
}
