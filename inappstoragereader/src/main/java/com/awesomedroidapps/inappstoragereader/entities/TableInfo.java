package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.DatabaseColumnType;
import com.awesomedroidapps.inappstoragereader.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anshul on 29/04/17.
 */

public class TableInfo implements Serializable {

  private String databaseName;
  private String tableName;
  private List<String> tableColumnNames;
  private List<DatabaseColumnType> tableColumnTypes;
  private List<Integer> primaryKeysList;

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<String> getTableColumnNames() {
    return tableColumnNames;
  }

  public void setTableColumnNames(List<String> tableColumnNames) {
    this.tableColumnNames = tableColumnNames;
  }

  public List<DatabaseColumnType> getTableColumnTypes() {
    return tableColumnTypes;
  }

  public void setTableColumnTypes(
      List<DatabaseColumnType> tableColumnTypes) {
    this.tableColumnTypes = tableColumnTypes;
  }

  public List<Integer> getPrimaryKeysList() {
    return primaryKeysList;
  }

  public void setPrimaryKeysList(List<Integer> primaryKeysList) {
    this.primaryKeysList = primaryKeysList;
  }

  //TODO anshul.jain Write a UT for testing this scenario.
  public boolean isNotProper() {
    boolean FieldsNullOrEmpty = (Utils.isEmpty(databaseName) || Utils.isEmpty(tableName) ||
        Utils.isEmpty(tableColumnNames) || Utils.isEmpty(tableColumnTypes));
    if (FieldsNullOrEmpty) {
      return true;
    }
    boolean sizesAreNotEqual = (tableColumnNames.size() != tableColumnTypes.size());
    return sizesAreNotEqual;
  }
}
