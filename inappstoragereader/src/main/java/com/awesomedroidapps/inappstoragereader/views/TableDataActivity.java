package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class TableDataActivity extends AppCompatActivity implements ErrorMessageInterface {

  private AppStorageDataRecyclerView tableDataRecyclerView;
  private String databaseName, tableName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_table_data);
    tableDataRecyclerView =
        (AppStorageDataRecyclerView) findViewById(R.id.table_data_recycler_view);

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
    loadTableData();
  }

  private void loadTableData() {
    ArrayList<Integer> tableDataColumnWidthList = SqliteDatabaseReader.getTableDataColumnWidth(this,
        databaseName, tableName);
    int recyclerViewWidth = SqliteDatabaseReader.getTableWidth(tableDataColumnWidthList);
    tableDataRecyclerView.setRecyclerViewWidth(recyclerViewWidth);
    ArrayList<ArrayList<String>> tableData = SqliteDatabaseReader.getAllTableData(this,
        databaseName, tableName);

    if (Utils.isEmpty(tableData)) {
      handleError(ErrorType.NO_TABLE_DATA_FOUND);
    }

    TableDataListAdapter adapter =
        new TableDataListAdapter(tableData, this, tableDataColumnWidthList);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager
        .VERTICAL, false);
    Utils.setActionBarTitle(getSupportActionBar(), tableName, tableData.size() - 1);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }

  @Override
  public void handleError(ErrorType errorType) {

  }

}
