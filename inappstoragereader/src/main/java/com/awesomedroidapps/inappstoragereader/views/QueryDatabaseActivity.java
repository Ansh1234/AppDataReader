package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.QueryDatabaseAsyncTask;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.QueryDataResponse;
import com.awesomedroidapps.inappstoragereader.entities.TableDataResponse;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.interfaces.QueryDatabaseView;

import java.lang.ref.WeakReference;

/**
 * An activity for querying the database.
 * Created by anshul on 27/2/17.
 */

public class QueryDatabaseActivity extends AppCompatActivity implements
    View.OnClickListener, ErrorMessageInterface, QueryDatabaseView, DataItemClickListener {

  private EditText queryDatabaseEditText;
  private Button submitQueryButton;
  private String databaseName;
  private TextView errorMessageTextView;
  private AppStorageDataRecyclerView tableDataRecyclerView;
  private ProgressDialog progressDialog;
  private RelativeLayout databaseQueryContainer;


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

    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
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
    if (item.getItemId() == R.id.com_awesomedroidapps_inappstoragereader_edit) {
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
    }
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

}
