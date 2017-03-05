package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;
import com.awesomedroidapps.inappstoragereader.interfaces.SharedPreferenceView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 1/3/17.
 */

public class SharedPreferenceAsyncTask
    extends AsyncTask<String, Void, List<SharedPreferenceObject>> {

  private final WeakReference<Activity> activtyWeakReference;
  private final SharedPreferenceView sharedPreferenceView;

  public SharedPreferenceAsyncTask(WeakReference activtyWeakReference,
                                   SharedPreferenceView sharedPreferenceView) {
    this.activtyWeakReference = activtyWeakReference;
    this.sharedPreferenceView = sharedPreferenceView;
  }

  @Override
  protected List<SharedPreferenceObject> doInBackground(String... params) {
    if (activtyWeakReference.get() == null) {
      return null;
    }

    if (params != null && !Utils.isEmpty(params[Constants.ZERO_INDEX])) {
      return SharedPreferenceReader.getSharedPreferencesBaseOnFileName(activtyWeakReference.get(),
          params[Constants.ZERO_INDEX]);
    }
    return SharedPreferenceReader.getAllSharedPreferences(activtyWeakReference.get());

  }


  protected void onPostExecute(List<SharedPreferenceObject> sharedPreferenceObjectList) {
    if (sharedPreferenceView != null && activtyWeakReference.get() != null) {
      sharedPreferenceView.onSharedPreferencesFetched(sharedPreferenceObjectList);
    }
  }
}
