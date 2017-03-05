package com.awesomedroidapps.inappstoragereader;

/**
 * Shared Preference data type with it's column width.
 * Created by anshul on 12/2/17.
 */

public enum SharedPreferenceDataType {
  STRING("STRING"),
  INT("INT"),
  FLOAT("FLOAT"),
  LONG("LONG"),
  BOOLEAN("BOOLEAN"),
  STRING_SET("STRING_SET");

  private String type;

  SharedPreferenceDataType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
