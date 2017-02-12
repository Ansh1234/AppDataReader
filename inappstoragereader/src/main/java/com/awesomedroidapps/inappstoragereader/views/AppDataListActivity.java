package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SharedPreferenceReader;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.AppDataListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class AppDataListActivity extends AppCompatActivity {

  RecyclerView databaseRecylerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_database_list);
    databaseRecylerView = (RecyclerView) findViewById(R.id.database_recycler_view);

    List databaseList = SqliteDatabaseReader.readAppDataStorageList(this);
    if (databaseList == null) {
      databaseList = new ArrayList();
    }

    AppDataListAdapter adapter = new AppDataListAdapter(databaseList, this);
    databaseRecylerView.setLayoutManager(new LinearLayoutManager(this));
    databaseRecylerView.setAdapter(adapter);
    SharedPreferenceReader.getAllSharedPreferences(this);

  }
}
