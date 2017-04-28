package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;

/**
 * Created by anshul on 1/3/17.
 *
 * Interface for propogating the response from async tasks to Activity
 */

public interface QueryResponseListener {

  void onRawQueryDataFetched(QueryDataResponse queryDataResponse);
}
