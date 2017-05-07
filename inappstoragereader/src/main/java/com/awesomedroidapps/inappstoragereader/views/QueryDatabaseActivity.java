package com.awesomedroidapps.inappstoragereader.views;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommandType;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.entities.QueryDatabaseRequest;
import com.awesomedroidapps.inappstoragereader.entities.TableInfo;
import com.awesomedroidapps.inappstoragereader.helpers.GeneralSqliteHelper;
import com.awesomedroidapps.inappstoragereader.interfaces.ColumnSelectListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity for querying the database.
 * Created by anshul on 27/2/17.
 */

public class QueryDatabaseActivity extends AppCompatActivity implements
    View.OnClickListener, ErrorMessageInterface, ColumnSelectListener,
    AdapterView.OnItemSelectedListener, QueryDatabaseView {

  private Button submitQueryButton;
  private Spinner queryTypeSpinner;
  private TextView selectTableTextView, updateTableTextView, insertTableTextView, deleteTableTextView;
  private Button selectedColumnsButton, deleteWhereClause, selectWhereClause,
  updateWhereClause, setClauseButton,
      valuesClauseButton;
  ArrayList<String> querySpinnerArrayList = new ArrayList<>();
  private EditText rawQueryEditText;
  private TableInfo tableInfo;
  private QueryDatabaseRequest queryDatabaseRequest;
  private RelativeLayout selectContainer, updateContainer, deleteContainer, insertContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_query_database);
    submitQueryButton = (Button) findViewById(R.id.com_awesomedroidapps_inappstoragereader_submit);
    submitQueryButton.setOnClickListener(this);

    // tableColumnsSpinner = (AppCompatSpinner) findViewById(R.id.spinner_database_table_columns);
    selectTableTextView = (TextView) findViewById(R.id.textview_select_query_table_name);
    updateTableTextView = (TextView) findViewById(R.id.textview_update_query_table_name);
    insertTableTextView = (TextView) findViewById(R.id.textview_insert_query_table_name);
    deleteTableTextView = (TextView) findViewById(R.id.textview_delete_query_table_name);
    rawQueryEditText = (EditText) findViewById(R.id
        .com_awesomedroidapps_inappstoragereader_query_editText);

    queryTypeSpinner = (AppCompatSpinner) findViewById(R.id
        .spinner_database_query_command);
    queryTypeSpinner.setOnItemSelectedListener(this);

    selectedColumnsButton = (Button) findViewById(R.id.button_database_table_columns);
    updateWhereClause = (Button) findViewById(R.id.button_update_where_cause);
    deleteWhereClause = (Button) findViewById(R.id.button_delete_where_cause);
    selectWhereClause = (Button) findViewById(R.id.button_select_where_cause);

    setClauseButton = (Button) findViewById(R.id.set_clause);
    valuesClauseButton = (Button) findViewById(R.id.button_values_cause);
    selectedColumnsButton.setOnClickListener(this);
    updateWhereClause.setOnClickListener(this);
    deleteWhereClause.setOnClickListener(this);
    selectWhereClause.setOnClickListener(this);

    setClauseButton.setOnClickListener(this);
    valuesClauseButton.setOnClickListener(this);

    selectContainer = (RelativeLayout) findViewById(R.id.select_clause_container);
    updateContainer = (RelativeLayout) findViewById(R.id.update_clause_container);
    deleteContainer = (RelativeLayout) findViewById(R.id.delete_clause_container);
    insertContainer = (RelativeLayout) findViewById(R.id.insert_clause_container);

    queryDatabaseRequest = new QueryDatabaseRequest();
    readBundle();
    initInitialUI();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void readBundle() {
    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      tableInfo = (TableInfo) bundle.get(Constants.BUNDLE_TABLE_INFO);
    }
  }

  private void initInitialUI() {
    for (DatabaseQueryCommandType sharedPreferenceDataType : DatabaseQueryCommandType.values()) {
      querySpinnerArrayList.add(sharedPreferenceDataType.getCommand());
    }
    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.com_awesomedroidapps_inappstoragereadder_custom_spinner,
        querySpinnerArrayList);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    queryTypeSpinner.setAdapter(adapter);
    onSelectCommandSelected();
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
      onSelectCommandSelected();
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
    } else if (view == selectedColumnsButton) {
      launchColumnsSelectionDialog();
    } else if (view == deleteWhereClause|| view==selectWhereClause|| view==updateWhereClause) {
      launchActivityForWhereClauseAndContentValues(Constants.REQUEST_CODE_WHERE_CLAUSE);
    } else if (view == setClauseButton) {
      launchActivityForWhereClauseAndContentValues(Constants.REQUEST_CODE_SET_CLAUSE);
    } else if (view == valuesClauseButton) {
      launchActivityForWhereClauseAndContentValues(Constants.REQUEST_CODE_VALUES_CLAUSE);
    }
  }

  private void launchActivityForWhereClauseAndContentValues(int requestCode) {
    Intent intent = new Intent(QueryDatabaseActivity.this, ClauseActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(Constants.BUNDLE_TABLE_INFO, tableInfo);
    bundle.putInt(Constants.BUNDLE_REQUEST_CODE, requestCode);
    intent.putExtras(bundle);
    startActivityForResult(intent, requestCode);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != RESULT_OK) {
      return;
    }

    if (requestCode == Constants.REQUEST_CODE_WHERE_CLAUSE) {
      handleWhereClauseResult(data);
    } else if (requestCode == Constants.REQUEST_CODE_SET_CLAUSE) {
      handleContentValuesResult(data);
    } else if (requestCode == Constants.REQUEST_CODE_VALUES_CLAUSE) {
      handleInsertValuesResult(data);
    }
  }


  private void handleWhereClauseResult(Intent data) {
    String str = data.getStringExtra(Constants.BUNDLE_WHERE_CLAUSE);
    queryDatabaseRequest.setWhereClause(str);
    str = new StringBuilder(Constants.WHERE_CLAUSE).append(Constants
        .SPACE).append(str).toString();
    selectWhereClause.setText(str);
    deleteWhereClause.setText(str);
    updateWhereClause.setText(str);
  }

  private void handleContentValuesResult(Intent data) {
    ContentValues contentValues = data.getExtras().getParcelable(Constants.BUNDLE_CONTENT_VALUES);
    queryDatabaseRequest.setContentValues(contentValues);
    String str = data.getStringExtra(Constants.SET_CLAUSE);
    str = new StringBuilder(Constants.SET_CLAUSE).append(Constants
        .SPACE).append(str).toString();
    setClauseButton.setText(str);
  }

  private void handleInsertValuesResult(Intent data) {
    ContentValues contentValues = data.getExtras().getParcelable(Constants.BUNDLE_CONTENT_VALUES);
    queryDatabaseRequest.setContentValues(contentValues);
    String str = data.getStringExtra(Constants.INSERT_CLAUSE);
    valuesClauseButton.setText(str);
  }

  private void launchColumnsSelectionDialog() {
    String[] columnNames = SqliteDatabaseReader.getColumnNames(this, tableInfo.getDatabaseName(),
        tableInfo.getTableName());
    boolean[] previouslySelectedColumns = null;
    if (!Constants.ASTERIK.equals(selectedColumnsButton.getText().toString())) {
      String selectedColumnsStr = selectedColumnsButton.getText().toString();
      List<String> selectedColumns = GeneralSqliteHelper.getListFromString(selectedColumnsStr);
      previouslySelectedColumns = GeneralSqliteHelper.getCheckedArray(columnNames, selectedColumns);
    }
    TableColumnsDialog tableColumnsDialog = TableColumnsDialog.newInstance(columnNames,
        previouslySelectedColumns, this);
    tableColumnsDialog.show(getFragmentManager(), "columnsDialog");
  }

  /**
   * This method will query the database.
   */
  private void queryDatabase() {
    //String query = queryDatabaseEditText.getText().toString();

    String queryType = (String) queryTypeSpinner.getSelectedItem();
    DatabaseQueryCommandType databaseQueryCommandType = DatabaseQueryCommandType.getCommand
        (queryType);

    if (databaseQueryCommandType == null) {
      return;
    }

    if (queryDatabaseRequest == null) {
      queryDatabaseRequest = new QueryDatabaseRequest();
    }

    String query = Constants.EMPTY_STRING;
    switch (databaseQueryCommandType) {
      case SELECT:
        query = getSelectQuery();
        queryDatabaseRequest.setSelectQuery(query);
        break;
      case UPDATE:
        break;
      case DELETE:
        break;
      case INSERT:
        query = getInsertQuery();
        break;
      case RAW_QUERY:
        query = getRawQuery();
        if (Utils.isEmpty(query)) {
          String toastMessage = Utils.getString(this, R.string
              .com_awesomedroidapps_inappstoragereader_database_query_empty);
          Utils.showLongToast(this, toastMessage);
          return;
        }
        queryDatabaseRequest.setRawQuery(query);
        break;
    }
    queryDatabaseRequest.setDatabaseQueryCommandType(databaseQueryCommandType);


    Intent intent = new Intent(this, QueryResultActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_RAW_QUERY, query);
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, tableInfo.getDatabaseName());
    bundle.putString(Constants.BUNDLE_TABLE_NAME, tableInfo.getTableName());
    bundle.putParcelable(Constants.BUNDLE_CONTENT_VALUES, queryDatabaseRequest.getContentValues());
    queryDatabaseRequest.setContentValues(null);
    bundle.putSerializable(Constants.BUNDLE_QUERY_REQUEST, queryDatabaseRequest);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void handleError(ErrorType errorType) {

  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String queryCommand = querySpinnerArrayList.get(position);
    DatabaseQueryCommandType command = DatabaseQueryCommandType.getCommand(queryCommand);
    switch (command) {
      case SELECT:
        onSelectCommandSelected();
        break;
      case UPDATE:
        onUpdateCommandSelected();
        break;
      case DELETE:
        onDeleteCommandSelected();
        break;
      case INSERT:
        onInsertCommandSelected();
        break;
      case RAW_QUERY:
        onRawQueryCommandSelected();
        break;
    }
  }

  private String getSelectQuery() {
    String queryType = queryTypeSpinner.getSelectedItem().toString();
    String queryColumns = selectedColumnsButton.getText().toString();
    String queryTableName = selectTableTextView.getText().toString();
    String queryWhereClause = selectWhereClause.getText().toString();

    if (Constants.WHERE_CLAUSE.equals(queryWhereClause.trim())) {
      queryWhereClause = Constants.EMPTY_STRING;
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(queryType)
        .append(Constants.SPACE)
        .append(queryColumns)
        .append(Constants.SPACE)
        .append(queryTableName)
        .append(Constants.SPACE).append(queryWhereClause);

    String query = stringBuilder.toString().trim();
    return query;
  }

  private String getInsertQuery() {
    return Constants.EMPTY_STRING;

  }

  private String getRawQuery() {
    String rawQuery = rawQueryEditText.getText().toString();
    return rawQuery;
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }

  @Override
  public void onSelectCommandSelected() {
    showOrHideViews(selectContainer);
    queryDatabaseRequest.setDatabaseQueryCommandType(DatabaseQueryCommandType.SELECT);
    queryTypeSpinner.setSelection(Constants.ZERO_INDEX);
    selectTableTextView.setText(Constants.FROM_PREFIX + Constants.SPACE + tableInfo.getTableName());
    selectWhereClause.setText(Constants.WHERE_CLAUSE);
    selectedColumnsButton.setText(Constants.ASTERIK);
  }

  @Override
  public void onUpdateCommandSelected() {
    showOrHideViews(updateContainer);
    queryDatabaseRequest.setDatabaseQueryCommandType(DatabaseQueryCommandType.UPDATE);
    updateTableTextView.setText(Constants.SPACE + tableInfo.getTableName());
    setClauseButton.setText(Constants.SET_CLAUSE + Constants.SPACE);
    updateWhereClause.setText(Constants.WHERE_CLAUSE);
  }

  @Override
  public void onDeleteCommandSelected() {
    showOrHideViews(deleteContainer);
    queryDatabaseRequest.setDatabaseQueryCommandType(DatabaseQueryCommandType.DELETE);
    deleteTableTextView.setText(Constants.FROM_PREFIX+Constants.SPACE+tableInfo.getTableName());
    deleteWhereClause.setText(Constants.WHERE_CLAUSE);
  }

  @Override
  public void onInsertCommandSelected() {
    showOrHideViews(insertContainer);
    queryDatabaseRequest.setDatabaseQueryCommandType(DatabaseQueryCommandType.DELETE);
    valuesClauseButton.setText(Constants.VALUES);
    insertTableTextView.setText(Constants.INTO_PREFIX + Constants.SPACE + tableInfo.getTableName());
  }

  @Override
  public void onRawQueryCommandSelected() {
    showOrHideViews(rawQueryEditText);
    queryDatabaseRequest.setDatabaseQueryCommandType(DatabaseQueryCommandType.RAW_QUERY);
  }

  private void showOrHideViews(View view) {
    selectContainer.setVisibility(View.GONE);
    updateContainer.setVisibility(View.GONE);
    insertContainer.setVisibility(View.GONE);
    deleteContainer.setVisibility(View.GONE);
    rawQueryEditText.setVisibility(View.GONE);

    if (view == selectContainer) {
      selectContainer.setVisibility(View.VISIBLE);
    } else if (view == updateContainer) {
      updateContainer.setVisibility(View.VISIBLE);
    } else if (view == deleteContainer) {
      deleteContainer.setVisibility(View.VISIBLE);
    } else if (view == insertContainer) {
      insertContainer.setVisibility(View.VISIBLE);
    } else if (view == rawQueryEditText) {
      rawQueryEditText.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onColumnsSelected(String columns) {
    if (Utils.isEmpty(columns)) {
      selectedColumnsButton.setText(Constants.ASTERIK);
    }
    selectedColumnsButton.setText(columns);
  }
}
