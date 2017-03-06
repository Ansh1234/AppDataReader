package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 5/3/17.
 * An enum for detecting the query type.
 */

public enum SqliteRawStatementsType {
  SELECT("select"),
  INSERT("insert"),
  DELETE("delete"),
  UPDATE("update"),
  UNKNOWN("");

  SqliteRawStatementsType(String type) {
    this.type = type;
  }

  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public static SqliteRawStatementsType getType(String type) {
    for (SqliteRawStatementsType sqliteRawStatementType : SqliteRawStatementsType.values()) {
      if (sqliteRawStatementType.getType().equals(type)) {
        return sqliteRawStatementType;
      }
    }
    return UNKNOWN;
  }
}
