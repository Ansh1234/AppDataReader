package com.awesomedroidapps.appstoragedatareader.demo;

/**
 * Shared Preference data type.
 * Created by anshul on 12/2/17.
 */

public enum SharedPreferenceDataType {
  UNKNOWN("SELECT DATA TYPE"),
  STRING("STRING"),
  INT("INT");

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
