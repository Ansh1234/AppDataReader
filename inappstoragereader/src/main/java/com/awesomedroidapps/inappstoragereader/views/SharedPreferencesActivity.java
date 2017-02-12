package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SharedPreferenceReader;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.adapters.SharedPreferencesListAdapter;
import com.awesomedroidapps.inappstoragereader.adapters.TablesListAdapter;

import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class SharedPreferencesActivity extends AppCompatActivity {

  RecyclerView sharedPreferencesRecylerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shared_preferences_list);
    sharedPreferencesRecylerView =
        (RecyclerView) findViewById(R.id.shared_preferences_recycler_view);

    List sharedPreferenceObjectArrayList = SharedPreferenceReader.getAllSharedPreferences(this);
    if (sharedPreferenceObjectArrayList == null) {
      return;
    }
    SharedPreferencesListAdapter adapter = new SharedPreferencesListAdapter(sharedPreferenceObjectArrayList, this);
    sharedPreferencesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    sharedPreferencesRecylerView.setAdapter(adapter);

  }
}
