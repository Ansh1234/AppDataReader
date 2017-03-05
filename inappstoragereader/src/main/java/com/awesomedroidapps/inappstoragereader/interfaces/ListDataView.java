package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;

import java.util.List;

/**
 * Created by anshul on 1/3/17.
 * * Interface for propogating the response from async tasks to Activity
 */

public interface ListDataView {

  void onDataFetched(List<AppDataStorageItem> appDataList);
}
