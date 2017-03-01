package com.awesomedroidapps.inappstoragereader.views;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;

import java.util.List;

/**
 * Created by anshul on 1/3/17.
 */

public interface AppDataListView {

  void onDataFetched(List<AppDataStorageItem> appDataList);
}
