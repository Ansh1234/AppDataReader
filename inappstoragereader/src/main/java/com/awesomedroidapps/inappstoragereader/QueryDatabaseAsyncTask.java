package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryResponseListener;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 1/3/17.
 * An async task for query the database tables.
 */

public class QueryDatabaseAsyncTask extends AsyncTask<String, Void, QueryDataResponse> {

  private final WeakReference<Activity> activtyWeakReference;
  private final QueryResponseListener queryResponseListener;

  public QueryDatabaseAsyncTask(WeakReference activtyWeakReference,
                                QueryResponseListener queryResponseListener) {
    this.activtyWeakReference = activtyWeakReference;
    this.queryResponseListener = queryResponseListener;
  }

  @Override
  protected QueryDataResponse doInBackground(String... params) {
    if (activtyWeakReference.get() == null || params == null || params.length != 2) {
      return null;
    }
    String databaseName = params[0];
    String query = params[1];

    QueryDataResponse queryDataResponse = SqliteDatabaseReader.queryDatabase(
        activtyWeakReference.get(), databaseName, query);
    return queryDataResponse;
  }


  protected void onPostExecute(QueryDataResponse queryDataResponse) {
    if (queryResponseListener != null && activtyWeakReference.get() != null) {
      queryResponseListener.onRawQueryDataFetched(queryDataResponse);
    }
  }
}
