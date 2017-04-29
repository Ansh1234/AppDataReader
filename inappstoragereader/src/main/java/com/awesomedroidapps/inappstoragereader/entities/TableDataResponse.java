package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.DatabaseColumnType;

import java.util.List;

/**
 * Created by anshul on 1/3/17.
 */

public class TableDataResponse {

  private int recyclerViewWidth;
  private List<Integer> recyclerViewColumnsWidth;
  private List<List<String>> tableData;
  private List<String> columnNames;
  private List<DatabaseColumnType> columnTypes;
  private List<Integer> primaryKeyList;

  public int getRecyclerViewWidth() {
    return recyclerViewWidth;
  }

  public void setRecyclerViewWidth(int recyclerViewWidth) {
    this.recyclerViewWidth = recyclerViewWidth;
  }

  public List<Integer> getRecyclerViewColumnsWidth() {
    return recyclerViewColumnsWidth;
  }

  public void setRecyclerViewColumnsWidth(List<Integer> recyclerViewColumnsWidth) {
    this.recyclerViewColumnsWidth = recyclerViewColumnsWidth;
  }

  public List<List<String>> getTableData() {
    return tableData;
  }

  public void setTableData(List<List<String>> tableData) {
    this.tableData = tableData;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(List<String> columnNames) {
    this.columnNames = columnNames;
  }


  public List<DatabaseColumnType> getColumnTypes() {
    return columnTypes;
  }

  public void setColumnTypes(List<DatabaseColumnType> columnTypes) {
    this.columnTypes = columnTypes;
  }

  public List<Integer> getPrimaryKeyList() {
    return primaryKeyList;
  }

  public void setPrimaryKeyList(List<Integer> primaryKeyList) {
    this.primaryKeyList = primaryKeyList;
  }
}
