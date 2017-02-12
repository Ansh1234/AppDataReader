package com.awesomedroidapps.inappstoragereader;

import android.content.Context;
import android.content.SharedPreferences;

import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by anshul on 12/2/17.
 */

public class SharedPreferenceReader {

  /**
   * This method is used to return all the shared preferences
   *
   * @param context
   * @return
   */
  public static ArrayList<String> getSharedPreferencesTags(Context context) {
    ArrayList<String> sharedPreferences = new ArrayList<>();
    String rootPath = context.getApplicationInfo().dataDir + SqliteConstants
        .SHARED_PREFERENCES_PATH;
    File sharedPreferenceDirectory = new File(rootPath);
    if (!sharedPreferenceDirectory.exists()) {
      return sharedPreferences;
    }
    for (File file : sharedPreferenceDirectory.listFiles()) {
      String fileName = file.getName();
      if (fileName.endsWith(SqliteConstants.XML_SUFFIX)) {
        fileName = fileName.substring(0, fileName.length() - SqliteConstants.XML_SUFFIX.length());
        sharedPreferences.add(fileName);
      }
    }
    return sharedPreferences;
  }

  public static ArrayList<SharedPreferenceObject> getAllSharedPreferences(Context context) {

    ArrayList<String> sharedPreferencesTagList = getSharedPreferencesTags(context);
    if (sharedPreferencesTagList == null || sharedPreferencesTagList.isEmpty()) {
      return null;
    }

    ArrayList<SharedPreferenceObject> sharedPreferenceDataTypeArrayList = new ArrayList<>();
    for (String tag : sharedPreferencesTagList) {
      ArrayList sharedPreferencesForTagList = getSharedPreferencesForTag(context, tag);
      sharedPreferenceDataTypeArrayList.addAll(sharedPreferencesForTagList);
    }
    return sharedPreferenceDataTypeArrayList;
  }


  public static ArrayList<SharedPreferenceObject> getSharedPreferencesForTag(Context context,
                                                                             String tag) {

    ArrayList<SharedPreferenceObject> sharedPreferenceDataTypeArrayList = new ArrayList<>();

    SharedPreferences preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);

    Map<String, ?> allEntries = preferences.getAll();


    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

      SharedPreferenceObject sharedPreferenceObject = new SharedPreferenceObject();
      sharedPreferenceObject.setKey(entry.getKey());
      if (entry.getValue() == null) {
        continue;
      }

      if (entry.getValue() instanceof String) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.STRING);
        sharedPreferenceObject.setValue((String) entry.getValue());
      } else if (entry.getValue() instanceof Integer) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.INTEGER);
        sharedPreferenceObject.setValue(Integer.toString((Integer) entry.getValue()));
      } else if (entry.getValue() instanceof Long) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.LONG);
        sharedPreferenceObject.setValue(Long.toString((Long) entry.getValue()));
      } else if (entry.getValue() instanceof Float) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.FLOAT);
        sharedPreferenceObject.setValue(Float.toString((Float) entry.getValue()));
      } else if (entry.getValue() instanceof Boolean) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.BOOLEAN);
        sharedPreferenceObject.setValue(Boolean.toString((Boolean) entry.getValue()));
      } else if (entry.getValue() instanceof Set) {
        sharedPreferenceObject.setSharedPreferenceDataType(SharedPreferenceDataType.SET);
        sharedPreferenceObject.setValue((String) entry.getValue());
      }
      sharedPreferenceDataTypeArrayList.add(sharedPreferenceObject);
    }
    return sharedPreferenceDataTypeArrayList;
  }
}
