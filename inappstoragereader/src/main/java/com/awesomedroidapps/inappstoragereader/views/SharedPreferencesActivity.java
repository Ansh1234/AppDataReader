package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SharedPreferenceReader;
import com.awesomedroidapps.inappstoragereader.adapters.SharedPreferencesListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class SharedPreferencesActivity extends AppCompatActivity {

  AppStorageDataRecyclerView sharedPreferencesRecylerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shared_preferences_list);
    sharedPreferencesRecylerView =
        (AppStorageDataRecyclerView) findViewById(R.id.shared_preferences_recycler_view);
    int height = (int) (getResources().getDimension(R.dimen.sharedpreferences_type_width) +
        getResources
        ().getDimension(R.dimen.sharedpreferences_key_width)+getResources().getDimension(R.dimen
        .sharedpreferences_value_width));
    sharedPreferencesRecylerView.setRecyclerViewWidth(height);
  }

  @Override
  public void onStart(){
    super.onStart();
    loadSharedPreferences();
  }

  private void loadSharedPreferences() {
    List<SharedPreferenceObject> sharedPreferenceObjectArrayList = SharedPreferenceReader.getAllSharedPreferences
        (this);
    if (sharedPreferenceObjectArrayList == null) {
      return;
    }
    SharedPreferencesListAdapter
        adapter = new SharedPreferencesListAdapter(sharedPreferenceObjectArrayList, this);
    sharedPreferencesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    sharedPreferencesRecylerView.setAdapter(adapter);
  }
}
