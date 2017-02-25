package com.awesomedroidapps.inappstoragereader.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.AppStorageItemClickListener;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.IconWithTextListAdapter;

import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class TableListActivity extends AppCompatActivity implements AppStorageItemClickListener {

  RecyclerView tablesRecylerView;
  private String databaseName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_tables_list);
    tablesRecylerView = (RecyclerView) findViewById(R.id.tables_recycler_view);
    Bundle bundle = getIntent().getExtras();
     databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    List tablesList = SqliteDatabaseReader.readTablesList(this, databaseName);
    if (tablesList == null) {
      return;
    }
    IconWithTextListAdapter adapter = new IconWithTextListAdapter(tablesList, this);
    tablesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    tablesRecylerView.setAdapter(adapter);
  }


  @Override
  public void onItemClicked(AppDataStorageItem appDataStorageItem) {
    Intent intent = new Intent(this,TableDataActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME,databaseName);
    bundle.putString(Constants.BUNDLE_TABLE_NAME,appDataStorageItem.getStorageName());
    intent.putExtras(bundle);
    startActivity(intent);
  }
}
