package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseRequest;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryResponseListener;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 1/3/17.
 * An async task for query the database tables.
 */

public class QueryDatabaseAsyncTask extends AsyncTask<String, Void, QueryDataResponse> {

  private final WeakReference<Activity> activtyWeakReference;
  private final QueryResponseListener queryResponseListener;
  private final QueryDatabaseRequest queryDatabaseRequest;

  public QueryDatabaseAsyncTask(WeakReference activtyWeakReference,
                                QueryResponseListener queryResponseListener, QueryDatabaseRequest
                                    queryDatabaseRequest) {
    this.activtyWeakReference = activtyWeakReference;
    this.queryResponseListener = queryResponseListener;
    this.queryDatabaseRequest = queryDatabaseRequest;
  }

  @Override
  protected QueryDataResponse doInBackground(String... params) {
    if (activtyWeakReference.get() == null || params == null || params.length != 3) {
      return null;
    }
    String databaseName = params[0];
    String tableName = params[1];
    String query = params[2];

    QueryDataResponse queryDataResponse = SqliteDatabaseReader.queryDatabase(
        activtyWeakReference.get(), queryDatabaseRequest, databaseName, tableName);
    return queryDataResponse;
  }


  protected void onPostExecute(QueryDataResponse queryDataResponse) {
    if (queryResponseListener != null && activtyWeakReference.get() != null) {
      queryResponseListener.onRawQueryDataFetched(queryDataResponse);
    }
  }
}
