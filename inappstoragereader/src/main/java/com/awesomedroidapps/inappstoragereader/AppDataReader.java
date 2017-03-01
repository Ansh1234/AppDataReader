package com.awesomedroidapps.inappstoragereader;

import android.content.Context;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The purpose of this class is to show get all the databases used in the application and show
 * SharedPreferences as an list item if available.
 * Created by anshul on 25/2/17.
 */

public class AppDataReader {


  /**
   * This method will return a list of all the databases in the current application.
   * It will remove all the com_awesomedroidapps_inappstoragereader_database names which ends with .journal
   *
   * @param context
   * @return
   */
  public static List<AppDataStorageItem> readAppDataStorageList(Context context) {

    if (context == null) {
      return null;
    }

    ArrayList appDataStorageList = new ArrayList();

    //Get names of all the Databases
    List<AppDataStorageItem> appDatabaseItemsList = getDatabaseItemList(context);
    if (Utils.isEmpty(appDatabaseItemsList)) {
      return null;
    }
    appDataStorageList.addAll(appDatabaseItemsList);

    //Get SharedPreference Object
    AppDataStorageItem sharedPreferenceItem = getSharedPreferenceItem();
    if (!Utils.isEmpty(appDatabaseItemsList)) {
      appDataStorageList.add(Constants.ZERO_INDEX, sharedPreferenceItem);
    } else if (!SharedPreferenceReader.isSharedPreferencesEmpty(context)) {
      appDataStorageList.add(Constants.ZERO_INDEX, sharedPreferenceItem);
    }
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return appDataStorageList;
  }

  /**
   * Get all the database names
   *
   * @param context
   * @return
   */
  private static List<AppDataStorageItem> getDatabaseItemList(Context context) {

    if (context == null) {
      return null;

    }

    ArrayList databaseList = new ArrayList(Arrays.asList(context.databaseList()));
    if (Utils.isEmpty(databaseList)) {
      return null;
    }

    Iterator iterator = databaseList.iterator();
    List<AppDataStorageItem> appDataStorageItemList = new ArrayList<>();

    while (iterator.hasNext()) {
      String databaseName;
      try {
        databaseName = (String) iterator.next();
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
      if (databaseName.endsWith(SqliteConstants.JOURNAL_SUFFIX)) {
        continue;
      }
      AppDataStorageItem appDataStorageItem = new AppDataStorageItem();
      appDataStorageItem.setStorageType(StorageType.DATABASE);
      appDataStorageItem.setStorageName(databaseName);
      appDataStorageItemList.add(appDataStorageItem);
    }
    return appDataStorageItemList;
  }

  /**
   * Get SharedPreference object.
   *
   * @return
   */
  private static AppDataStorageItem getSharedPreferenceItem() {
    AppDataStorageItem appDataStorageItem = new AppDataStorageItem();
    appDataStorageItem.setStorageType(StorageType.SHARED_PREFERENCE);
    appDataStorageItem.setStorageName(Constants.SHARED_PREFERENCES_NAME);
    return appDataStorageItem;
  }
}


