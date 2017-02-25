package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 25/2/17.
 */

public enum ErrorType {
  NO_ITEM_FOUND("No SharedPreferences or Databases found."),
  NO_TABLES_FOUND("No tables found in the com_awesomedroidapps_inappstoragereader_database."),
  NO_SHARED_PREFERENCES_FOUND("No SharedPreferences found."),
  NO_DATABASES_FOUND("No databases found.");

  private String errorMessage;

  ErrorType(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
