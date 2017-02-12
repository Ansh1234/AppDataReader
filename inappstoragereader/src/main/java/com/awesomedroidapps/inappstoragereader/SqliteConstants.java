package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 11/2/17.
 */

public class SqliteConstants {

  public static String JOURNAL_SUFFIX = "-journal";
  public static String RETRIEVE_ALL_TABLES_QUERY = "SELECT name FROM sqlite_master WHERE " +
      "type='table'";
  public static String SHARED_PREFERENCES_PATH  = "/shared_prefs";
  public static String XML_SUFFIX = ".xml";
}
