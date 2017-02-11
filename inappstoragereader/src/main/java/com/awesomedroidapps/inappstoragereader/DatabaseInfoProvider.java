package com.awesomedroidapps.inappstoragereader;

import android.content.Context;

/**
 * Created by anshul on 11/2/17.
 */

public class DatabaseInfoProvider {

  public void getDatabaseList(Context context) {
    String[] databaseList = context.databaseList();
  }
}
