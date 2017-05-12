package com.awesomedroidapps.inappstoragereader.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
 * Activity for showing the list of all the tables in a particular database.
 * Created by anshul on 11/2/17.
 */

public class TableListActivity extends BaseActivity implements AppStorageItemClickListener,
    ErrorMessageInterface, ListDataView {

  RecyclerView tablesRecylerView;
  private String databaseName;
  private RelativeLayout errorHandlerLayout;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.com_awesomedroidapps_inappstoragereader_activity_tables_list);
    tablesRecylerView = (RecyclerView) findViewById(R.id.tables_recycler_view);
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);

    Bundle bundle = getIntent().getExtras();
    databaseName = bundle.getString(Constants.BUNDLE_DATABASE_NAME);
    progressDialog = new ProgressDialog(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    initUI();
    new AppStorageItemListAsyncTask(new WeakReference(this), this, StorageType.TABLE).execute(databaseName);
  }

  private void initUI() {
    tablesRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.GONE);
    progressDialog.setMessage(
        getString(R.string.com_awesomedroidapps_inappstoragereader_progressBar_message));
    progressDialog.setIndeterminate(false);
    progressDialog.show();
  }

  @Override
  public void onItemClicked(AppDataStorageItem appDataStorageItem) {
    if (appDataStorageItem == null) {
      return;
    }
    Intent intent = new Intent(this, TableDataActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_DATABASE_NAME, databaseName);
    bundle.putString(Constants.BUNDLE_TABLE_NAME, appDataStorageItem.getStorageName());
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void handleError(ErrorType errorType) {
    tablesRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.VISIBLE);
    ErrorMessageHandler handler = new ErrorMessageHandler();
    handler.handleError(errorType, errorHandlerLayout);
  }

  @Override
  public void onDataFetched(List<AppDataStorageItem> tablesList) {
    progressDialog.dismiss();
    if (Utils.isEmpty(tablesList)) {
      handleError(ErrorType.NO_TABLES_FOUND);
    }

    tablesRecylerView.setVisibility(View.VISIBLE);
    IconWithTextListAdapter adapter = new IconWithTextListAdapter(tablesList, this);
    tablesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    tablesRecylerView.setAdapter(adapter);
  }
}
