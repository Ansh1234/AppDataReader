package com.awesomedroidapps.inappstoragereader;

/**
 * A class for storing DB constants.
 * Created by anshul on 11/2/17.
 */

public class SqliteConstants {

  public static final String JOURNAL_SUFFIX = "-journal";
  public static final String RETRIEVE_ALL_TABLES_QUERY = "SELECT name FROM sqlite_master WHERE " +
      "type='table'";
  public static final String SHARED_PREFERENCES_PATH  = "/shared_prefs";
  public static final String XML_SUFFIX = ".xml";
  public static final String BLOB = "blob";
}
