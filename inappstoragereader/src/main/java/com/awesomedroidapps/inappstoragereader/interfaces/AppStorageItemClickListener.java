package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;

/**
 * Created by anshul on 25/2/17.
 *
 * Interface when a database or sharedPreference item is clicked.
 */

public interface AppStorageItemClickListener {
  void onItemClicked(AppDataStorageItem appDataStorageItem);
}
