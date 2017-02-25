package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.SharedPreferenceDataType;

import java.util.ArrayList;

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

  public ArrayList<String> getAsList(){
    ArrayList arrayList = new ArrayList();
    arrayList.add(key);
    arrayList.add(value);
    arrayList.add(sharedPreferenceDataType.getType());
    return arrayList;
  }
}
