package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 12/2/17.
 */

public enum SharedPreferenceDataType {
  STRING("STRING",200),
  INT("INT",100),
  FLOAT("FLOAT",100),
  LONG("LONG",150),
  BOOLEAN("BOOLEAN",50),
  STRING_SET("STRING_SET",250);

  private int columnHeight;
  private String type;

  SharedPreferenceDataType(String type, int columnHeight) {
    this.type=type;
    this.columnHeight = columnHeight;
  }

  public int getColumnHeight() {
    return columnHeight;
  }

  public String getType() {
    return type;
  }

  public void setColumnHeight(int columnHeight) {
    this.columnHeight = columnHeight;
  }
}
