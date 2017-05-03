package com.awesomedroidapps.inappstoragereader;

import android.app.Activity;
import android.os.AsyncTask;

import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseRequest;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryResponseListener;

import java.lang.ref.WeakReference;

/**
 * Created by anshul on 1/3/17.
 * An async task for query the database tables.
 */

public class QueryDatabaseAsyncTask extends AsyncTask<String, Void, QueryDatabaseResponse> {

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
  protected QueryDatabaseResponse doInBackground(String... params) {
    if (activtyWeakReference.get() == null || queryDatabaseRequest==null) {
      return null;
    }


    QueryDatabaseResponse queryDatabaseResponse = SqliteDatabaseReader.queryDatabase(
        activtyWeakReference.get(), queryDatabaseRequest);
    return queryDatabaseResponse;
  }


  protected void onPostExecute(QueryDatabaseResponse queryDatabaseResponse) {
    if (queryResponseListener != null && activtyWeakReference.get() != null) {
      queryResponseListener.onRawQueryDataFetched(queryDatabaseResponse);
    }
  }
}
