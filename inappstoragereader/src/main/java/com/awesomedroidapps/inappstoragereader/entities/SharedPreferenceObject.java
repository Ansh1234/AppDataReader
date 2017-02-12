package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.SharedPreferenceDataType;

/**
 * Created by anshul on 12/2/17.
 */

public class SharedPreferenceObject {

  private String key;
  private String value;
  private SharedPreferenceDataType sharedPreferenceDataType;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public SharedPreferenceDataType getSharedPreferenceDataType() {
    return sharedPreferenceDataType;
  }

  public void setSharedPreferenceDataType(
      SharedPreferenceDataType sharedPreferenceDataType) {
    this.sharedPreferenceDataType = sharedPreferenceDataType;
  }
}
