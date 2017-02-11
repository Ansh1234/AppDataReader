package com.awesomedroidapps.inappstoragereader;

import android.content.Context;

/**
 * Created by anshul on 11/2/17.
 */

public class InAppStorageSDK {

  private Context context;
  private static InAppStorageSDK instance;

  private InAppStorageSDK() {
    //Do nothing
  }

  public static InAppStorageSDK getInstance() {
    synchronized (InAppStorageSDK.class.getSimpleName()) {
      if (instance == null) {
        instance = new InAppStorageSDK();
      }
    }
    return instance;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }
}
