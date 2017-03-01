package com.awesomedroidapps.inappstoragereader;

import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.List;

/**
 * Created by anshul on 1/3/17.
 */

public interface SharedPreferenceView {
  void onSharedPreferencesFetched(List<SharedPreferenceObject> appDataList);
}
