package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryDatabaseView;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 1/3/17.
 * An async task for query the database tables.
 */

public class QueryDatabaseAsyncTask extends AsyncTask<String, Void, QueryDataResponse> {

  private final WeakReference<Activity> activtyWeakReference;
  private final QueryDatabaseView queryDatabaseView;

  public QueryDatabaseAsyncTask(WeakReference activtyWeakReference,
                                QueryDatabaseView queryDatabaseView) {
    this.activtyWeakReference = activtyWeakReference;
    this.queryDatabaseView = queryDatabaseView;
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
    if (queryDatabaseView != null && activtyWeakReference.get() != null) {
      queryDatabaseView.onDataFetched(queryDataResponse);
    }
  }
}
