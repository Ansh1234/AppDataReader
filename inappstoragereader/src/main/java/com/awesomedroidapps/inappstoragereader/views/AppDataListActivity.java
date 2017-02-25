package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.awesomedroidapps.inappstoragereader.AppDataReader;
import com.awesomedroidapps.inappstoragereader.ErrorMessageHandler;
import com.awesomedroidapps.inappstoragereader.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SharedPreferenceReader;
import com.awesomedroidapps.inappstoragereader.SqliteDatabaseReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.AppDataListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class AppDataListActivity extends AppCompatActivity implements ErrorMessageInterface {

  private RecyclerView appDataRecylerView;
  private RelativeLayout errorHandlerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appdata_list);
    appDataRecylerView = (RecyclerView) findViewById(R.id.storage_data_recycler_view);
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);
  }

  @Override
  public void onStart(){
    super.onStart();
    List appDataList = AppDataReader.readAppDataStorageList(this);
    if (Utils.isEmpty(appDataList)) {
      handleError(ErrorType.NO_ITEM_FOUND);
      return;
    }
    appDataRecylerView.setVisibility(View.VISIBLE);
    errorHandlerLayout.setVisibility(View.GONE);

    AppDataListAdapter adapter = new AppDataListAdapter(appDataList, this);
    appDataRecylerView.setLayoutManager(new LinearLayoutManager(this));
    appDataRecylerView.setAdapter(adapter);
    SharedPreferenceReader.getAllSharedPreferences(this);
  }

  @Override
  public void handleError(ErrorType errorType) {
    appDataRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.VISIBLE);
    ErrorMessageHandler handler = new ErrorMessageHandler();
    handler.handleError(errorType, errorHandlerLayout);
  }
}
