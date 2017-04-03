package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommands;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.QueryDatabaseAsyncTask;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.ColumnSelectListener;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryDatabaseView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * An activity for querying the database.
 * Created by anshul on 27/2/17.
 */

public class QueryDatabaseActivity extends AppCompatActivity implements
    View.OnClickListener, ErrorMessageInterface, QueryDatabaseView, DataItemClickListener,
    ColumnSelectListener {

  private EditText queryDatabaseEditText;
  private Button submitQueryButton;
  private String databaseName, tableName;
  private TextView errorMessageTextView;
  private AppStorageDataRecyclerView tableDataRecyclerView;
  private ProgressDialog progressDialog;
  private RelativeLayout databaseQueryContainer;
  private Spinner databaseQueryCommandSpinner, tableColumnsSpinner;
  private TextView tableNameTv, queryTextTv;
  private Button buttonDatabaseTableColumns, whereClauseButton;
  private final int requestCode = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_query_database);
    databaseQueryContainer = (RelativeLayout) findViewById(R.id.database_query_container);
    queryDatabaseEditText = (EditText) findViewById(R.id
        .com_awesomedroidapps_inappstoragereader_query_editText);
    submitQueryButton = (Button) findViewById(R.id.com_awesomedroidapps_inappstoragereader_submit);
    tableDataRecyclerView =
        (AppStorageDataRecyclerView) findViewById(R.id.table_data_recycler_view);
    errorMessageTextView = (TextView) findViewById(R.id.error_message_textview);
    progressDialog = new ProgressDialog(this);
    submitQueryButton.setOnClickListener(this);

    // tableColumnsSpinner = (AppCompatSpinner) findViewById(R.id.spinner_database_table_columns);
    tableNameTv = (TextView) findViewById(R.id.table_name);
    queryTextTv = (TextView) findViewById(R.id.query_text);
    databaseQueryCommandSpinner = (AppCompatSpinner) findViewById(R.id
        .spinner_database_query_command);
    buttonDatabaseTableColumns = (Button) findViewById(R.id.button_database_table_columns);
    whereClauseButton = (Button) findViewById(R.id.button_where_cause);

    readBundle();
    //TODO anshul.jain Remove this.
    onSelectQueryCommandSelected();
    initInitialUI();
  }

  private void initInitialUI() {
    ArrayList<String> spinnerArrayList = new ArrayList<>();
    for (DatabaseQueryCommands sharedPreferenceDataType : DatabaseQueryCommands.values()) {
      spinnerArrayList.add(sharedPreferenceDataType.getCommand());
    }
    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
        spinnerArrayList);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    databaseQueryCommandSpinner.setAdapter(adapter);
    buttonDatabaseTableColumns.setText("*");
    buttonDatabaseTableColumns.setOnClickListener(this);
    whereClauseButton.setOnClickListener(this);
    tableNameTv.setText(Constants.FROM_PREFIX + Constants.SPACE + tableName);
  }

  private void onSelectQueryCommandSelected() {
  }

  private void readBundle() {
    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
      tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.com_awesomedroidapps_inappstoragereader_query, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    if (item.getItemId() == R.id.com_awesomedroidapps_inappstoragereader_refresh) {
      tableDataRecyclerView.setVisibility(View.GONE);
      showQueryUI();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onStart() {
    super.onStart();
  }


  @Override
  public void onClick(View view) {
    if (view == submitQueryButton) {
      queryDatabase();
    } else if (view == buttonDatabaseTableColumns) {
      launchColumnsDialog();
    } else if (view == whereClauseButton) {
      launchWhereClauseActivity();
    }
  }

  private void launchWhereClauseActivity() {
    String[] columnNames = SqliteDatabaseReader.getColumnNames(QueryDatabaseActivity.this,
        databaseName, tableName);
    Intent intent = new Intent(QueryDatabaseActivity.this, WhereCauseActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, databaseName);
    bundle.putString(Constants.BUNDLE_TABLE_NAME, tableName);
    intent.putExtras(bundle);
    startActivityForResult(intent, 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == RESULT_OK) {
      String str = data.getStringExtra("whereClause");
      whereClauseButton.setText(str);
    }
  }

  private void launchColumnsDialog() {
    String[] columnNames = SqliteDatabaseReader.getColumnNames(QueryDatabaseActivity.this,
        databaseName, tableName);
    TableColumnsDialog tableColumnsDialog = TableColumnsDialog.newInstance(columnNames, this);
    tableColumnsDialog.show(getFragmentManager(), "columnsDialog");
  }

  /**
   * This method will query the database.
   */
  private void queryDatabase() {
    String query = queryDatabaseEditText.getText().toString();
    if (Utils.isEmpty(query)) {
      String message = Utils.getString(this,
          R.string.com_awesomedroidapps_inappstoragereader_database_query_empty);
      Utils.showToast(this, message);
      return;
    }
    new QueryDatabaseAsyncTask(new WeakReference(this), this).execute(new String[]{
        databaseName, query
    });
  }

  @Override
  public void handleError(ErrorType errorType) {

  }

  //Hide the query editText and submit button
  private void hideQueryUI() {
    databaseQueryContainer.setVisibility(View.GONE);
  }

  //Show the query editText and submit button
  private void showQueryUI() {
    databaseQueryContainer.setVisibility(View.VISIBLE);
    errorMessageTextView.setText(Constants.EMPTY_STRING);
    queryDatabaseEditText.setText(Constants.EMPTY_STRING);
  }

  @Override
  public void onDataFetched(QueryDataResponse queryDataResponse) {
    progressDialog.dismiss();

    if (queryDataResponse == null || queryDataResponse.getQueryStatus() == null) {
      return;
    }
    switch (queryDataResponse.getQueryStatus()) {
      case SUCCESS:
        showQueryData(queryDataResponse.getTableDataResponse());
        break;
      case FAILURE:
        showQueryError(queryDataResponse.getErrorMessage());
        break;
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

    hideQueryUI();
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
    errorMessageTextView.setText(errorMessage);
  }

  @Override
  public void onDataItemClicked(String data) {

  }

  @Override
  public void onColumnsSelected(String columns) {
    buttonDatabaseTableColumns.setText(columns);
  }
}
