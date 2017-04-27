package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommands;
import com.awesomedroidapps.inappstoragereader.QueryStatus;

/**
 * Created by anshul on 6/3/17.
 * A class for encapsulating the response of a database query.
 */

public class QueryDataResponse {

  private QueryStatus queryStatus;
  private TableDataResponse tableDataResponse;
  private String errorMessage;
  private String successMessage;
  private DatabaseQueryCommands databaseQueryCommands;

  public QueryStatus getQueryStatus() {
    return queryStatus;
  }

  public void setQueryStatus(QueryStatus queryStatus) {
    this.queryStatus = queryStatus;
  }

  public TableDataResponse getTableDataResponse() {
    return tableDataResponse;
  }

  public void setTableDataResponse(
      TableDataResponse tableDataResponse) {
    this.tableDataResponse = tableDataResponse;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getSuccessMessage() {
    return successMessage;
  }

  public void setSuccessMessage(String successMessage) {
    this.successMessage = successMessage;
  }

  public DatabaseQueryCommands getDatabaseQueryCommands() {
    return databaseQueryCommands;
  }

  public void setDatabaseQueryCommands(
      DatabaseQueryCommands databaseQueryCommands) {
    this.databaseQueryCommands = databaseQueryCommands;
  }
}
