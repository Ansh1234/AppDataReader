package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DataItemDialogFragment;
import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommandType;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.QueryDatabaseAsyncTask;
import com.awesomedroidapps.inappstoragereader.QueryStatus;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseRequest;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseResponse;
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

public class QueryResultActivity extends BaseActivity
    implements ErrorMessageInterface, DataItemClickListener, TableDataView,
    QueryResponseListener, CommandResponses {

  private AppStorageDataRecyclerView tableDataRecyclerView;
  private String rawQuery, databaseName, tableName;
  private ProgressDialog progressDialog;
  private RelativeLayout errorHandlerLayout;
  private QueryDatabaseRequest queryDatabaseRequest;

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
      tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);
      ContentValues contentValues = bundle.getParcelable(Constants.BUNDLE_CONTENT_VALUES);
      queryDatabaseRequest = (QueryDatabaseRequest) bundle.get(Constants.BUNDLE_QUERY_REQUEST);
      queryDatabaseRequest.setContentValues(contentValues);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    queryDatabaseRequest.setDatabaseName(databaseName);
    queryDatabaseRequest.setTableName(tableName);
    new QueryDatabaseAsyncTask(new WeakReference(this), this, queryDatabaseRequest).execute();
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
  public void onRawQueryDataFetched(QueryDatabaseResponse queryDatabaseResponse) {

    if (progressDialog != null) {
      progressDialog.dismiss();
    }

    if (queryDatabaseResponse == null || queryDatabaseResponse.getQueryStatus() == null) {
      showGenericErrorToast();
      finish();
      return;
    }

    DatabaseQueryCommandType databaseQueryCommandType =
        queryDatabaseResponse.getDatabaseQueryCommandType();

    if (databaseQueryCommandType == null) {
      showGenericErrorToast();
      finish();
      return;
    }

    switch (databaseQueryCommandType) {
      case UPDATE:
        onUpdateQueryResponse(queryDatabaseResponse);
        break;
      case SELECT:
        onSelectQueryResponse(queryDatabaseResponse);
        break;
      case DELETE:
        onDeleteQueryResponse(queryDatabaseResponse);
        break;
      case INSERT:
        onInsertQueryResponse(queryDatabaseResponse);
        break;
      case RAW_QUERY:
        onUnknownTypeQueryResponse(queryDatabaseResponse);
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
    Utils.showLongToast(this, errorMessage);
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
  public void onSelectQueryResponse(@NonNull QueryDatabaseResponse queryDatabaseResponse) {

    QueryStatus queryStatus = queryDatabaseResponse.getQueryStatus();

    switch (queryStatus) {
      case SUCCESS:
        int selectedRows = (int) queryDatabaseResponse.getAffectedRows();
        showQueryData(queryDatabaseResponse.getTableDataResponse());
        String toastMessage = getResources().getQuantityString(R.plurals
            .com_awesomedroidapps_inappstoragereader_records_found, selectedRows, selectedRows);
        Utils.showLongToast(this, toastMessage);
        break;
      case FAILURE:
        finish();
        showQueryError(queryDatabaseResponse.getErrorMessage());
        break;
    }
  }

  @Override
  public void onUpdateQueryResponse(QueryDatabaseResponse queryDatabaseResponse) {
    switch (queryDatabaseResponse.getQueryStatus()) {
      case SUCCESS:
        long updatedRows = queryDatabaseResponse.getAffectedRows();
        String toastMessage = Utils.getString(this, R.string
            .com_awesomedroidapps_inappstoragereader_table_updated_toast, updatedRows);
        Utils.showLongToast(this, toastMessage);
        break;
      case FAILURE:
        String errorMessage = queryDatabaseResponse.getErrorMessage();
        if (Utils.isEmpty(errorMessage)) {
          errorMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_generic_error);
        }
        Utils.showLongToast(this, errorMessage);
        break;
    }
    finish();
  }

  @Override
  public void onDeleteQueryResponse(QueryDatabaseResponse queryDatabaseResponse) {

    switch (queryDatabaseResponse.getQueryStatus()) {
      case SUCCESS:
        long deletedRows = queryDatabaseResponse.getAffectedRows();
        String toastMessage = Utils.getString(this, R.string
            .com_awesomedroidapps_inappstoragereader_query_deleted_successful, deletedRows);
        Utils.showLongToast(this, toastMessage);
        break;
      case FAILURE:
        String errorMessage = queryDatabaseResponse.getErrorMessage();
        if (Utils.isEmpty(errorMessage)) {
          errorMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_generic_error);
        }
        Utils.showLongToast(this, errorMessage);
        break;
    }
    finish();
  }

  @Override
  public void onInsertQueryResponse(QueryDatabaseResponse queryDatabaseResponse) {

    switch (queryDatabaseResponse.getQueryStatus()) {
      case SUCCESS:
        String toastMessage = Utils.getString(this,
            R.string.com_awesomedroidapps_inappstoragereader_table_inserted_toast);
        Utils.showLongToast(this, toastMessage);
        break;
      case FAILURE:
        String errorMessage = queryDatabaseResponse.getErrorMessage();
        if (Utils.isEmpty(errorMessage)) {
          errorMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_generic_error);
        }
        Utils.showLongToast(this, errorMessage);
        break;
    }
    finish();
  }

  @Override
  public void onUnknownTypeQueryResponse(QueryDatabaseResponse queryDatabaseResponse) {

    QueryStatus queryStatus = queryDatabaseResponse.getQueryStatus();

    switch (queryStatus) {
      case SUCCESS:
        if (queryDatabaseResponse.getTableDataResponse() != null) {
          onDataFetched(queryDatabaseResponse.getTableDataResponse());
          return;
        }
        String succesfulMessage = Utils.getString(this, R.string
            .com_awesomedroidapps_inappstoragereader_database_query_success);
        Utils.showLongToast(this, succesfulMessage);
        finish();
        break;
      case FAILURE:
        String failureReason = queryDatabaseResponse.getErrorMessage();
        if (Utils.isEmpty(failureReason)) {
          failureReason = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_database_query_failed);
        }
        Utils.showLongToast(this, failureReason);
        finish();
        break;
    }
  }

  private void showGenericErrorToast() {
    String toastMessage = Utils.getString(this, R.string
        .com_awesomedroidapps_inappstoragereader_generic_error);
    Utils.showLongToast(this, toastMessage);
  }
}
