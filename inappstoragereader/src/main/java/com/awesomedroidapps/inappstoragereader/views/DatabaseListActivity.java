package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.DatabaseListAdapter;

import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class DatabaseListActivity extends AppCompatActivity {

  RecyclerView databaseRecylerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_database_list);
    databaseRecylerView = (RecyclerView) findViewById(R.id.database_recycler_view);

    List databaseList = SqliteDatabaseReader.readDatabaseList(this);
    if(databaseList==null){
      return;
    }
    DatabaseListAdapter adapter = new DatabaseListAdapter(databaseList,this);
    databaseRecylerView.setLayoutManager(new LinearLayoutManager(this));
    databaseRecylerView.setAdapter(adapter);
  }
}
