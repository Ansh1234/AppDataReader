package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;

/**
 * Created by anshul on 27/04/17.
 */

public interface CommandResponses {

  void onSelectQueryResponse(QueryDataResponse queryDataResponse);

  void onUpdateQueryResponse(QueryDataResponse queryDataResponse);

  void onDeleteQueryResponse(QueryDataResponse queryDataResponse);

  void onInsertQueryResponse(QueryDataResponse queryDataResponse);

  void onUnknownTypeQueryResponse(QueryDataResponse queryDataResponse);

}
