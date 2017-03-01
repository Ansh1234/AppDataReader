package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.views.AppDataListActivity;
import com.awesomedroidapps.inappstoragereader.views.AppDataListView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 1/3/17.
 */

public class AppDataAsyncTask extends AsyncTask<String, Void, List<AppDataStorageItem>> {

  private final WeakReference<Activity> activtyWeakReference;
  private final AppDataListView appDataListView;
  private final StorageType storageType;

  public AppDataAsyncTask(WeakReference activtyWeakReference,
                          AppDataListView appDataListView, StorageType storageType) {
    this.activtyWeakReference = activtyWeakReference;
    this.appDataListView = appDataListView;
    this.storageType = storageType;
  }

  @Override
  protected List<AppDataStorageItem> doInBackground(String... params) {
    if (activtyWeakReference.get() != null) {
      switch (storageType) {
        case ALL:
          return AppDataReader.readAppDataStorageList(activtyWeakReference.get());
        case TABLE:
          if (params == null) {
            return null;
          }
          String databaseName = params[0];
          if (Utils.isEmpty(databaseName)) {
            return null;
          }
          return SqliteDatabaseReader.readTablesList(activtyWeakReference.get(), databaseName);
      }
    }
    return null;
  }


  protected void onPostExecute(List<AppDataStorageItem> appDataList) {
    if (appDataListView != null && activtyWeakReference.get() != null) {
      appDataListView.onDataFetched(appDataList);
    }
  }
}
