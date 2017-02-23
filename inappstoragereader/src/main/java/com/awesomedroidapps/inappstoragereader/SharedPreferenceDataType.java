package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 12/2/17.
 */

public enum SharedPreferenceDataType {
  STRING(200),
  INT(100),
  FLOAT(100),
  LONG(150),
  BOOLEAN(50),
  STRING_SET(250);

  private int columnHeight;

  SharedPreferenceDataType(int columnHeight) {
    this.columnHeight = columnHeight;
  }

  public int getColumnHeight() {
    return columnHeight;
  }

  public void setColumnHeight(int columnHeight) {
    this.columnHeight = columnHeight;
  }
}
