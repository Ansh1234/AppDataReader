package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.TableDataListAdapter;

import java.util.ArrayList;

/**
 * Created by anshul on 11/2/17.
 */

public class TableDataActivity extends AppCompatActivity {

  private RecyclerView tableDataRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_table_data);
    tableDataRecyclerView = (RecyclerView) findViewById(R.id.table_data_recycler_view);

    //Read the bundle
    Bundle bundle = getIntent().getExtras();
    String databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    String tableName = bundle.getString(Constants.BUNDLE_TABLE_NAME);

    ArrayList<ArrayList<String>> tableData = SqliteDatabaseReader.getAllTableData(this,
        databaseName, tableName);
    int columnCount = SqliteDatabaseReader.getColumnCount(this,databaseName,tableName);
    TableDataListAdapter adapter = new TableDataListAdapter(tableData, this,columnCount);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager
        .VERTICAL,false);
    tableDataRecyclerView.setLayoutManager(linearLayoutManager);
    tableDataRecyclerView.setAdapter(adapter);
  }
}
