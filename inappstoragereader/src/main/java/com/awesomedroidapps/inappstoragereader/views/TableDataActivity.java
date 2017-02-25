package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
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

public class TableDataActivity extends AppCompatActivity {

  private AppStorageDataRecyclerView tableDataRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_table_data);
    tableDataRecyclerView = (AppStorageDataRecyclerView) findViewById(R.id.table_data_recycler_view);

    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    String databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    String tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);

    ArrayList<Integer> tableDataColumnWidthList = SqliteDatabaseReader.getTableDataColumnWidth(this,
        databaseName, tableName);
    int recyclerViewWidth = SqliteDatabaseReader.getTableWidth(tableDataColumnWidthList);
    tableDataRecyclerView.setRecyclerViewWidth(recyclerViewWidth);
    ArrayList<ArrayList<String>> tableData = SqliteDatabaseReader.getAllTableData(this,
        databaseName, tableName);
    TableDataListAdapter adapter = new TableDataListAdapter(tableData, this,tableDataColumnWidthList);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager
        .VERTICAL,false);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }

  /**
   * Sets the title of the action bar with the number of shared preferences items.
   *
   * @param sharedPreferenceObjectList - List containing the items.
   */
  private void setActionBarTitle(List<SharedPreferenceObject> sharedPreferenceObjectList) {
    if (getSupportActionBar() == null) {
      return;
    }
    String sharedPreferenceTitle = getResources().getString(R.string
        .com_awesomedroidapps_inappstoragereader_shared_preferences_list_activity);
    if (Utils.isEmpty(sharedPreferenceObjectList)) {
      getSupportActionBar().setTitle(sharedPreferenceTitle);
      return;
    }

    int size = sharedPreferenceObjectList.size();
    StringBuilder stringBuilder = new StringBuilder(sharedPreferenceTitle);
    stringBuilder.append(Constants.SPACE);
    stringBuilder.append(Constants.OPENING_BRACKET);
    stringBuilder.append(size);
    stringBuilder.append(Constants.CLOSING_BRACKET);
    getSupportActionBar().setTitle(stringBuilder.toString());
  }
}
