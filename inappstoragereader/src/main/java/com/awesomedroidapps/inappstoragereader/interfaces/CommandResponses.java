package com.awesomedroidapps.inappstoragereader.interfaces;

import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseResponse;

/**
 * Created by anshul on 27/04/17.
 */

public interface CommandResponses {

  void onSelectQueryResponse(QueryDatabaseResponse queryDatabaseResponse);

  void onUpdateQueryResponse(QueryDatabaseResponse queryDatabaseResponse);

  void onDeleteQueryResponse(QueryDatabaseResponse queryDatabaseResponse);

  void onInsertQueryResponse(QueryDatabaseResponse queryDatabaseResponse);

  void onUnknownTypeQueryResponse(QueryDatabaseResponse queryDatabaseResponse);

}
