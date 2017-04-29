package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.content.ContentValues;
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
import com.awesomedroidapps.inappstoragereader.DatabaseColumnType;
import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommandType;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.TableDataAsyncTask;
import com.awesomedroidapps.inappstoragereader.UpdateDatabaseAsyncTask;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.TableInfo;
import com.awesomedroidapps.inappstoragereader.interfaces.CommandResponses;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryResponseListener;
import com.awesomedroidapps.inappstoragereader.interfaces.TableDataEditListener;
import com.awesomedroidapps.inappstoragereader.interfaces.TableDataView;
import com.awesomedroidapps.inappstoragereader.utilities.AppDatabaseHelper;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class TableDataActivity extends AppCompatActivity
    implements ErrorMessageInterface, DataItemClickListener, TableDataView,
    TableDataEditListener, QueryResponseListener, CommandResponses {

  private AppStorageDataRecyclerView tableDataRecyclerView;
  private String databaseName, tableName;
  private ProgressDialog progressDialog;
  private RelativeLayout errorHandlerLayout;
  private TableDataResponse tableDataResponse;
//  private List<String> tableColumnNames;
//  private List<DatabaseColumnType> tableColumnTypes;
//  private List<Integer> primaryKeysList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_table_data);
    tableDataRecyclerView =
        (AppStorageDataRecyclerView) findViewById(R.id.table_data_recycler_view);
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);
    progressDialog = new ProgressDialog(this);

    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
      tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    initUI();
    fetchData();
  }

  private void fetchData() {
    new TableDataAsyncTask(new WeakReference(this), this).execute(
        new String[]{databaseName, tableName});
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
    TableInfo tableInfo = new TableInfo();
    tableInfo.setDatabaseName(databaseName);
    tableInfo.setTableName(tableName);
    tableInfo.setPrimaryKeysList(tableDataResponse.getPrimaryKeyList());
    tableInfo.setTableColumnNames(tableDataResponse.getColumnNames());
    tableInfo.setTableColumnTypes(tableDataResponse.getColumnTypes());

    bundle.putSerializable(Constants.BUNDLE_TABLE_INFO, tableInfo);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  private void initUI() {
    tableDataRecyclerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.GONE);
    progressDialog.setMessage(
        getString(R.string.com_awesomedroidapps_inappstoragereader_progressBar_message));
    progressDialog.setIndeterminate(false);
    progressDialog.show();
  }


  @Override
  public void handleError(ErrorType errorType) {

  }

  @Override
  public void onDataItemClicked(String data) {

  }

  @Override
  public void onDataItemClicked(String data, int columnIndex, List<String> columnValues) {
    DataItemDialogFragment dataItemDialogFragment = DataItemDialogFragment.newInstance(data, this,
        columnIndex, columnValues);
    dataItemDialogFragment.show(getSupportFragmentManager(), "dialog");
  }

  @Override
  public void onDataFetched(TableDataResponse tableDataResponse) {

    this.tableDataResponse = tableDataResponse;
    if (progressDialog != null) {
      progressDialog.dismiss();
    }

    if (tableDataResponse == null || Utils.isEmpty(tableDataResponse.getTableData())) {
      handleError(ErrorType.NO_TABLE_DATA_FOUND);
      return;
    }

//    tableColumnNames = tableDataResponse.getColumnNames();
//    tableColumnTypes = tableDataResponse.getColumnTypes();
//    primaryKeysList = tableDataResponse.getPrimaryKeyList();

    tableDataRecyclerView.setVisibility(View.VISIBLE);
    tableDataRecyclerView.setRecyclerViewWidth(tableDataResponse.getRecyclerViewWidth());

    TableDataListAdapter adapter =
        new TableDataListAdapter(tableDataResponse.getTableData(), this,
            tableDataResponse.getRecyclerViewColumnsWidth(), this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager
        .VERTICAL, false);
    Utils.setActionBarTitle(getSupportActionBar(), tableName,
        tableDataResponse.getTableData().size() - 1);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }

  @Override
  public void onTableDataEdited(String newValue, int columnIndex,
                                List<String> columnValues) {
    List<String> tableColumnNames = tableDataResponse.getColumnNames();
    List<DatabaseColumnType> tableColumnTypes = tableDataResponse.getColumnTypes();
    List<Integer> primaryKeysList = tableDataResponse.getPrimaryKeyList();

    ContentValues contentValues = null;
    try {
      contentValues =
          AppDatabaseHelper.getContentValues(tableColumnNames, tableColumnTypes,
               columnIndex, newValue, contentValues);
    } catch (Exception e) {
      Toast.makeText(this, "Update Failed", Toast.LENGTH_LONG).show();
      return;
    }
    if (contentValues == null) {
      Toast.makeText(this, "Update Failed", Toast.LENGTH_LONG).show();
      //TODO anshul.jain Take proper action here.
      return;
    }
    String whereClause = AppDatabaseHelper.getUpdateWhereClause(tableColumnNames, tableColumnTypes,
        columnValues, primaryKeysList);
    new UpdateDatabaseAsyncTask(new WeakReference(this), this, contentValues).execute(new String[]{
        databaseName, tableName, whereClause
    });
  }


  @Override
  public void onSelectQueryResponse(QueryDataResponse queryDataResponse) {
    switch (queryDataResponse.getQueryStatus()) {
      case SUCCESS:
        break;
      case FAILURE:
        break;
    }
  }

  @Override
  public void onUpdateQueryResponse(QueryDataResponse queryDataResponse) {
    if (queryDataResponse == null || queryDataResponse.getQueryStatus() == null) {
      return;
    }

    switch (queryDataResponse.getQueryStatus()) {
      case SUCCESS:
        String toastMessage = Utils.getString(this, R.string
            .com_awesomedroidapps_inappstoragereader_database_query_success);
        Utils.showLongToast(this, toastMessage);
        fetchData();
        break;
      case FAILURE:
        //TODO anshul.jain Replace this with a proper message.
        Toast.makeText(this, "Update Failed", Toast.LENGTH_LONG).show();
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
    fetchData();
  }

  @Override
  public void onRawQueryDataFetched(QueryDataResponse queryDataResponse) {

    if (progressDialog != null) {
      progressDialog.dismiss();
    }

    if (queryDataResponse == null || queryDataResponse.getQueryStatus() == null) {
      String toastMessage = Utils.getString(this, R.string
          .com_awesomedroidapps_inappstoragereader_database_query_failed);
      Utils.showLongToast(this, toastMessage);
      return;
    }

    DatabaseQueryCommandType commandType = queryDataResponse.getDatabaseQueryCommandType();

    if (commandType == null) {
      onUnknownTypeQueryResponse(queryDataResponse);
      return;
    }

    switch (commandType) {
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
}
