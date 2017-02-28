package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.content.SharedPreferences;

import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by anshul on 12/2/17.
 * <p>
 * A class for reading shared preferences data of an application.
 */

public class SharedPreferenceReader {

  /**
   * This method is used to return all the shared preferences tags stored inside of an application.
   *
   * @param context - Context object.
   * @return - List of all the tags of the shared preferences.
   */
  public static ArrayList<AppDataStorageItem> getSharedPreferencesTags(Context context) {

    if (context == null || context.getApplicationInfo() == null) {
      return null;
    }


    String rootPath = context.getApplicationInfo().dataDir + SqliteConstants
        .SHARED_PREFERENCES_PATH;
    File sharedPreferenceDirectory = new File(rootPath);
    if (!sharedPreferenceDirectory.exists()) {
      return null;
    }

    ArrayList<AppDataStorageItem> sharedPreferencesList = new ArrayList<>();

    for (File file : sharedPreferenceDirectory.listFiles()) {
      String fileName = file.getName();
      if (!Utils.isEmpty(fileName) && fileName.endsWith(SqliteConstants.XML_SUFFIX)) {
        fileName = fileName.substring(0, fileName.length() - SqliteConstants.XML_SUFFIX.length());
        AppDataStorageItem appDataStorageItem = new AppDataStorageItem();
        appDataStorageItem.setStorageType(StorageType.SHARED_PREFERENCE);
        appDataStorageItem.setStorageName(fileName);
        sharedPreferencesList.add(appDataStorageItem);
      }
    }
    return sharedPreferencesList;
  }


  /**
   * This method will return the shared preferences for individual files.
   *
   * @param context
   * @param tag
   * @return
   */
  public static ArrayList<SharedPreferenceObject> getSharedPreferencesBaseOnFileName(
      Context context, String tag) {

    if (context == null || Utils.isEmpty(tag)) {
      return null;
    }

    //Open the shared preferences file.
    SharedPreferences sharedPreferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);

    if (sharedPreferences == null) {
      return null;
    }

    //This list will contain all the key-value-type of the shared preferences based on the tag.
    ArrayList<SharedPreferenceObject> sharedPreferenceListForTag = new ArrayList<>();

    //Get all the key-value pairs of a shared preference file
    Map<String, ?> sharedPreferenceEntities = sharedPreferences.getAll();

    if (sharedPreferenceEntities == null || sharedPreferenceEntities.size() == 0) {
      return sharedPreferenceListForTag;
    }

    for (Map.Entry<String, ?> entry : sharedPreferenceEntities.entrySet()) {

      if (entry == null || entry.getKey() == null) {
        continue;
      }

      //Create new instance of SharedPreferenceObject
      SharedPreferenceObject sharedPreferenceObject = new SharedPreferenceObject();
      sharedPreferenceObject.setKey(entry.getKey());

      if (entry.getValue() == null) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.STRING);
        continue;
      }

      if (entry.getValue() instanceof String) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.STRING);
        String strValue = (String) entry.getValue();
        sharedPreferenceObject.setValue(strValue);

      } else if (entry.getValue() instanceof Integer) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.INT);
        Integer intValue = (Integer) entry.getValue();
        sharedPreferenceObject.setValue(Integer.toString(intValue));

      } else if (entry.getValue() instanceof Long) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.LONG);
        Long longValue = (Long) entry.getValue();
        sharedPreferenceObject.setValue(Long.toString(longValue));

      } else if (entry.getValue() instanceof Float) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.FLOAT);
        Float floatValue = (Float) entry.getValue();
        sharedPreferenceObject.setValue(Float.toString((floatValue)));

      } else if (entry.getValue() instanceof Boolean) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.BOOLEAN);
        Boolean boolValue = (Boolean) entry.getValue();
        sharedPreferenceObject.setValue(Boolean.toString(boolValue));

      } else if (entry.getValue() instanceof Set) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.STRING_SET);
        Set set = (Set) entry.getValue();
        sharedPreferenceObject.setValue(set.toString());
      }

      sharedPreferenceListForTag.add(sharedPreferenceObject);
    }

    return sharedPreferenceListForTag;
  }

  /**
   * This method is used to get all the shared preferences stored inside of an application. First
   * it gets through all the shared preference tags and based on the tag, retrieve the shared
   * preferences for every tag.
   *
   * @param context - Context object.
   * @return - List of all the shared preferences stored in the application.
   */
  public static ArrayList<SharedPreferenceObject> getAllSharedPreferences(Context context) {
    if (context == null) {
      return null;
    }

    List<AppDataStorageItem> sharedPreferencesTagList = getSharedPreferencesTags(context);

    if (sharedPreferencesTagList == null || sharedPreferencesTagList.isEmpty()) {
      return null;
    }

    ArrayList<SharedPreferenceObject> sharedPreferenceDataTypeArrayList = new ArrayList<>();

    for (AppDataStorageItem appDataStorageItem : sharedPreferencesTagList) {
      ArrayList sharedPreferencesForTagList =
          getSharedPreferencesBaseOnFileName(context, appDataStorageItem.getStorageName());
      sharedPreferenceDataTypeArrayList.addAll(sharedPreferencesForTagList);
    }
    return sharedPreferenceDataTypeArrayList;
  }

  public static boolean isSharedPreferencesEmpty(Context context) {
    if (context == null) {
      return true;
    }
    List sharedPreferenceTags = getSharedPreferencesTags(context);
    return Utils.isEmpty(sharedPreferenceTags);
  }
}
