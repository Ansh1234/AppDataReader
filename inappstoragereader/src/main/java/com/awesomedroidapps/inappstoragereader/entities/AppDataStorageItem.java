package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.StorageType;

/**
 * A class for specifying the storage type and name.
 * Created by anshul on 12/2/17.
 */

public class AppDataStorageItem {

  String storageName;
  StorageType storageType;

  public String getStorageName() {
    return storageName;
  }

  public void setStorageName(String storageName) {
    this.storageName = storageName;
  }

  public StorageType getStorageType() {
    return storageType;
  }

  public void setStorageType(StorageType storageType) {
    this.storageType = storageType;
  }
}
