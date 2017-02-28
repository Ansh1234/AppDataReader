package com.awesomedroidapps.inappstoragereader.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.DataItemDialogFragment;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;

import java.util.ArrayList;

/**
 * Created by anshul on 11/2/17.
 */

public class TableDataActivity extends AppCompatActivity
    implements ErrorMessageInterface, DataItemClickListener {

  private AppStorageDataRecyclerView tableDataRecyclerView;
  private String databaseName, tableName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_table_data);
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
        new TableDataListAdapter(tableData, this, tableDataColumnWidthList, this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager
        .VERTICAL, false);
    Utils.setActionBarTitle(getSupportActionBar(), tableName, tableData.size() - 1);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }

  @Override
  public void handleError(ErrorType errorType) {

  }

  @Override
  public void onDataItemClicked(String data) {
    if (Utils.isEmpty(data)) {
      String toastMessage =
          getResources().getString(R.string.com_awesomedroidapps_inappstoragereader_item_empty);
      Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
      return;
    }
    DataItemDialogFragment dataItemDialogFragment = DataItemDialogFragment.newInstance(data);
    dataItemDialogFragment.show(getSupportFragmentManager(), "dialog");
  }

}
