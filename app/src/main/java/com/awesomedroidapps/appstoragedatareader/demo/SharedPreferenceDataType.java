package com.awesomedroidapps.appstoragedatareader.demo;

/**
 * Shared Preference data type with it's column width.
 * Created by anshul on 12/2/17.
 */

public enum SharedPreferenceDataType {
  STRING("STRING"),
  INT("INT"),
  UNKNOWN("SELECT DATA TYPE");

  private String type;

  SharedPreferenceDataType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public static SharedPreferenceDataType getDataType(String type) {
    for (SharedPreferenceDataType dataType : SharedPreferenceDataType.values()) {
      if (dataType.getType().equals(type)) {
        return dataType;
      }
    }
    return UNKNOWN;
  }
}
