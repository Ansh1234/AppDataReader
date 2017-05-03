package com.awesomedroidapps.inappstoragereader.entities;

import android.content.ContentValues;

import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommandType;

import java.io.Serializable;

/**
 * Created by anshul on 29/04/17.
 */

public class QueryDatabaseRequest implements Serializable {

  private DatabaseQueryCommandType databaseQueryCommandType;
  private ContentValues contentValues;
  private String selectQuery;
  private String rawQuery;
  private String whereClause;
  private String databaseName;
  private String tableName;

  public DatabaseQueryCommandType getDatabaseQueryCommandType() {
    return databaseQueryCommandType;
  }

  public void setDatabaseQueryCommandType(
      DatabaseQueryCommandType databaseQueryCommandType) {
    this.databaseQueryCommandType = databaseQueryCommandType;
  }

  public ContentValues getContentValues() {
    return contentValues;
  }

  public void setContentValues(ContentValues contentValues) {
    this.contentValues = contentValues;
  }

  public String getSelectQuery() {
    return selectQuery;
  }

  public void setSelectQuery(String selectQuery) {
    this.selectQuery = selectQuery;
  }

  public String getRawQuery() {
    return rawQuery;
  }

  public void setRawQuery(String rawQuery) {
    this.rawQuery = rawQuery;
  }

  public String getWhereClause() {
    return whereClause;
  }

  public void setWhereClause(String whereClause) {
    this.whereClause = whereClause;
  }

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
}
