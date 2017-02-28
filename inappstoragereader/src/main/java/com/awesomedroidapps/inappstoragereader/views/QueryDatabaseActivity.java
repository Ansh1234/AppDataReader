package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;

import java.util.ArrayList;

/**
 * Created by anshul on 27/2/17.
 */

public class QueryDatabaseActivity extends AppCompatActivity implements
    View.OnClickListener, ErrorMessageInterface {

  private EditText queryDatabaseEditText;
  private Button submitQueryButton;
  private String databaseName;
  private RecyclerView tableDataRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_query_database);
    queryDatabaseEditText = (EditText) findViewById(R.id
        .com_awesomedroidapps_inappstoragereader_query_editText);
    submitQueryButton = (Button) findViewById(R.id.com_awesomedroidapps_inappstoragereader_submit);
    tableDataRecyclerView =
        (AppStorageDataRecyclerView) findViewById(R.id.table_data_recycler_view);

    submitQueryButton.setOnClickListener(this);

    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
  }


  @Override
  public void onClick(View view) {
    if (view == submitQueryButton) {
      String query = queryDatabaseEditText.getText().toString();
      if (Utils.isEmpty(query)) {
        return;
      }

      ArrayList<ArrayList<String>> tableData = SqliteDatabaseReader.queryDatabase(this,
          databaseName, query);

      if (Utils.isEmpty(tableData)) {
        handleError(ErrorType.NO_TABLE_DATA_FOUND);
      }

      TableDataListAdapter adapter =
          new TableDataListAdapter(tableData, this, tableDataColumnWidthList, this);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager
          .VERTICAL, false);
      tableDataRecyclerView.setLayoutManager(linearLayoutManager);
      tableDataRecyclerView.setAdapter(adapter);
    }
  }

  @Override
  public void handleError(ErrorType errorType) {

  }
}
