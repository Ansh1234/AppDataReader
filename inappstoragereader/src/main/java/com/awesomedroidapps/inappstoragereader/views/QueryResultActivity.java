package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DataItemDialogFragment;
import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommandType;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.QueryDatabaseAsyncTask;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.CommandResponses;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryResponseListener;
import com.awesomedroidapps.inappstoragereader.interfaces.TableDataView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class QueryResultActivity extends AppCompatActivity
    implements ErrorMessageInterface, DataItemClickListener, TableDataView,
    QueryResponseListener, CommandResponses {

  private AppStorageDataRecyclerView tableDataRecyclerView;
  private String rawQuery, databaseName;
  private ProgressDialog progressDialog;
  private RelativeLayout errorHandlerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_query_result);
    tableDataRecyclerView =
        (AppStorageDataRecyclerView) findViewById(R.id.query_result_recycler_view);
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);
    progressDialog = new ProgressDialog(this);

    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      rawQuery = bundle.getString(Constants.BUNDLE_RAW_QUERY);
      databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    new QueryDatabaseAsyncTask(new WeakReference(this), this).execute(new String[]{
        databaseName, rawQuery
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.com_awesomedroidapps_inappstoragereader_table_data, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    if (item.getItemId() == R.id.com_awesomedroidapps_inappstoragereader_edit) {
      openQueryActivity();
    }
    return super.onOptionsItemSelected(item);
  }

  private void openQueryActivity() {
    Intent intent = new Intent(this, QueryDatabaseActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, databaseName);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void handleError(ErrorType errorType) {

  }

  @Override
  public void onDataFetched(TableDataResponse tableDataResponse) {

    progressDialog.dismiss();

    if (tableDataResponse == null || Utils.isEmpty(tableDataResponse.getTableData())) {
      handleError(ErrorType.NO_TABLE_DATA_FOUND);
      return;
    }

    tableDataRecyclerView.setVisibility(View.VISIBLE);
    tableDataRecyclerView.setRecyclerViewWidth(tableDataResponse.getRecyclerViewWidth());


    TableDataListAdapter adapter =
        new TableDataListAdapter(tableDataResponse.getTableData(), this,
            tableDataResponse.getRecyclerViewColumnsWidth(), this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager
        .VERTICAL, false);
    Utils.setActionBarTitle(getSupportActionBar(), "Result",
        tableDataResponse.getTableData().size() - 1);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }

  @Override
  public void onRawQueryDataFetched(QueryDataResponse queryDataResponse) {
    progressDialog.dismiss();

    if (queryDataResponse == null || queryDataResponse.getQueryStatus() == null) {
      return;
    }

    DatabaseQueryCommandType databaseQueryCommandType =
        queryDataResponse.getDatabaseQueryCommandType();

    if (databaseQueryCommandType == null) {
      onUnknownTypeQueryResponse(queryDataResponse);
      return;
    }

    switch (databaseQueryCommandType) {
      case UPDATE:
        onUpdateQueryResponse(queryDataResponse);
        break;
      case SELECT:
        onSelectQueryResponse(queryDataResponse);
        break;
      case DELETE:
        onDeleteQueryResponse(queryDataResponse);
        break;
      case INSERT:
        onInsertQueryResponse(queryDataResponse);
        break;
      default:
        onUnknownTypeQueryResponse(queryDataResponse);
    }
  }


  /**
   * This method is called when the query to the database is succesful.
   *
   * @param tableDataResponse
   */
  private void showQueryData(TableDataResponse tableDataResponse) {
    if (tableDataResponse == null || Utils.isEmpty(tableDataResponse.getTableData())) {
      handleError(ErrorType.NO_TABLE_DATA_FOUND);
      return;
    }

    tableDataRecyclerView.setVisibility(View.VISIBLE);
    tableDataRecyclerView.setRecyclerViewWidth(tableDataResponse.getRecyclerViewWidth());


    TableDataListAdapter adapter =
        new TableDataListAdapter(tableDataResponse.getTableData(), this,
            tableDataResponse.getRecyclerViewColumnsWidth(), this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager
        .VERTICAL, false);
    Utils.setActionBarTitle(getSupportActionBar(), null,
        tableDataResponse.getTableData().size() - 1);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }

  /**
   * This method is called when the query to the database is failed.
   *
   * @param errorMessage
   */
  private void showQueryError(String errorMessage) {
  }

  @Override
  public void onDataItemClicked(String data) {
    if (Utils.isEmpty(data)) {
      String toastMessage =
          getResources().getString(R.string.com_awesomedroidapps_inappstoragereader_item_empty);
      Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
      return;
    }
    DataItemDialogFragment dataItemDialogFragment =
        DataItemDialogFragment.newInstance(data, null, 0,
            null);
    dataItemDialogFragment.show(getSupportFragmentManager(), "dialog");
  }

  @Override
  public void onDataItemClicked(String data, int columnIndex, List<String> columnValues) {

  }

  @Override
  public void onSelectQueryResponse(QueryDataResponse queryDataResponse) {
    switch (queryDataResponse.getQueryStatus()) {
      case SUCCESS:
        showQueryData(queryDataResponse.getTableDataResponse());
        break;
      case FAILURE:
        showQueryError(queryDataResponse.getErrorMessage());
        break;
    }
  }

  @Override
  public void onUpdateQueryResponse(QueryDataResponse queryDataResponse) {
    switch (queryDataResponse.getQueryStatus()) {
      case SUCCESS:
        Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show();
        break;
      case FAILURE:
        Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show();
        break;
    }
  }

  @Override
  public void onDeleteQueryResponse(QueryDataResponse queryDataResponse) {

  }

  @Override
  public void onInsertQueryResponse(QueryDataResponse queryDataResponse) {

  }

  @Override
  public void onUnknownTypeQueryResponse(QueryDataResponse queryDataResponse) {
    if (queryDataResponse == null || queryDataResponse.getQueryStatus() == null) {
      return;
    }

    switch (queryDataResponse.getQueryStatus()) {
      case SUCCESS:
        if (queryDataResponse.getTableDataResponse() != null) {
          onDataFetched(queryDataResponse.getTableDataResponse());
        }
        break;
      case FAILURE:
        break;
    }
  }
}
