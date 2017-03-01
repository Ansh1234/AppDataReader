package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.awesomedroidapps.inappstoragereader.AppStorageItemListAsyncTask;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.ErrorMessageHandler;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.StorageType;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.IconWithTextListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.interfaces.AppStorageItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.interfaces.ListDataView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * The Main activity for showing all the data of the app
 * Created by anshul on 11/2/17.
 */

public class AppDataListActivity extends AppCompatActivity implements ErrorMessageInterface,
    AppStorageItemClickListener, ListDataView {

  private RecyclerView appDataRecylerView;
  private RelativeLayout errorHandlerLayout;
  private ProgressDialog progressDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_appdata_list);
    appDataRecylerView = (RecyclerView) findViewById(R.id.app_data_recycler_view);
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);
    progressDialog = new ProgressDialog(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    initUI();
    new AppStorageItemListAsyncTask(new WeakReference(this), this, StorageType.ALL).execute();
  }

  private void initUI() {
    appDataRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.GONE);
    progressDialog.setMessage(
        getString(R.string.com_awesomedroidapps_inappstoragereader_progressBar_message));
    progressDialog.setIndeterminate(false);
    progressDialog.show();
  }

  public void onDataFetched(List<AppDataStorageItem> appDataList) {
    progressDialog.dismiss();
    if (Utils.isEmpty(appDataList)) {
      handleError(ErrorType.NO_ITEM_FOUND);
      return;
    }

    appDataRecylerView.setVisibility(View.VISIBLE);
    errorHandlerLayout.setVisibility(View.GONE);

    IconWithTextListAdapter adapter = new IconWithTextListAdapter(appDataList, this);
    appDataRecylerView.setLayoutManager(new LinearLayoutManager(this));
    appDataRecylerView.setAdapter(adapter);
  }


  @Override
  public void handleError(ErrorType errorType) {
    appDataRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.VISIBLE);
    ErrorMessageHandler handler = new ErrorMessageHandler();
    handler.handleError(errorType, errorHandlerLayout);
  }

  @Override
  public void onItemClicked(AppDataStorageItem appDataStorageItem) {
    StorageType storageType = appDataStorageItem.getStorageType();
    switch (storageType) {
      case DATABASE:
        startTableListActivity(appDataStorageItem);
        break;
      case SHARED_PREFERENCE:
        startSharedPreferenceActivity();
        break;
    }
  }

  private void startSharedPreferenceActivity() {
    Intent sharedPreferenceActivityIntent = new Intent(this, SharedPreferencesActivity.class);
    startActivity(sharedPreferenceActivityIntent);
  }

  private void startTableListActivity(AppDataStorageItem appDataStorageItem) {
    Intent intent = new Intent(this, TableListActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, appDataStorageItem.getStorageName());
    intent.putExtras(bundle);
    startActivity(intent);
  }

}
