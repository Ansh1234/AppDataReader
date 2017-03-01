package com.awesomedroidapps.inappstoragereader.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awesomedroidapps.inappstoragereader.entities.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.DataItemDialogFragment;
import com.awesomedroidapps.inappstoragereader.interfaces.AppStorageItemClickListener;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.ErrorMessageHandler;
import com.awesomedroidapps.inappstoragereader.interfaces.DataItemClickListener;
import com.awesomedroidapps.inappstoragereader.interfaces.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SharedPreferenceReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.IconWithTextListAdapter;
import com.awesomedroidapps.inappstoragereader.adapters.SharedPreferencesListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class SharedPreferencesActivity extends AppCompatActivity implements
    ErrorMessageInterface, PopupMenu.OnMenuItemClickListener, AppStorageItemClickListener,
    DataItemClickListener {

  private AppStorageDataRecyclerView sharedPreferencesRecylerView;
  private RelativeLayout errorHandlerLayout;
  private String fileName = null;
  private boolean isFileModeEnabled = false;
  private boolean displayFilterMenu = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(
        R.layout.com_awesomedroidapps_inappstoragereader_activity_shared_preferences_list);
    sharedPreferencesRecylerView =
        (AppStorageDataRecyclerView) findViewById(R.id.shared_preferences_recycler_view);
    sharedPreferencesRecylerView.setRecyclerViewWidth(getRecyclerViewWidth());
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      fileName = bundle.getString(Constants.BUNDLE_FILE_NAME);
      displayFilterMenu = bundle.getBoolean(Constants.BUNDLE_DISPLAY_FILTER);
    }
  }


  /**
   * To get the width of the recyclerView based on its individual column widths.
   *
   * @return - The width of the RecyclerView.
   */
  private int getRecyclerViewWidth() {
    return (int) (getResources().getDimension(
        R.dimen.com_awesomedroidapps_inappstoragereader_sharedpreferences_type_width) +
        getResources().getDimension(
            R.dimen.com_awesomedroidapps_inappstoragereader_sharedpreferences_key_width) +
        getResources().getDimension(
            R.dimen.com_awesomedroidapps_inappstoragereader_sharedpreferences_value_width));
  }

  private ArrayList<Integer> getRecyleViewWidthList() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(Utils.getDimensionInInteger(this,
        R.dimen.com_awesomedroidapps_inappstoragereader_sharedpreferences_key_width));
    arrayList.add(Utils.getDimensionInInteger(this,
        R.dimen.com_awesomedroidapps_inappstoragereader_sharedpreferences_value_width));
    arrayList.add(Utils.getDimensionInInteger(this,
        R.dimen.com_awesomedroidapps_inappstoragereader_sharedpreferences_type_width));
    return arrayList;
  }

  @Override
  public void onStart() {
    super.onStart();
    if (isFileModeEnabled) {
      loadSharedPreferencesFiles();
    } else if (Utils.isEmpty(fileName)) {
      loadAllSharedPreferences();
    } else {
      loadSharedPreferencesFromFile(fileName);
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.com_awesomedroidapps_inappstoragereader_shared_preference_menu, menu);
    MenuItem item = menu.findItem(R.id.shared_preferences_filter);
    item.setVisible(displayFilterMenu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    if (item.getItemId() == R.id.shared_preferences_filter) {
      View view = findViewById(R.id.shared_preferences_filter);
      showPopup(view);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * When the popup is shown.
   *
   * @param v
   */
  private void showPopup(View v) {
    PopupMenu popup = new PopupMenu(this, v);
    MenuInflater inflater = popup.getMenuInflater();
    inflater.inflate(R.menu.com_awesomedroidapps_inappstoragereader_shared_preferences_popup_menu,
        popup.getMenu());
    popup.setOnMenuItemClickListener(this);
    popup.show();
  }

  /**
   * This function is for loading all the shared preferences.
   */
  private void loadAllSharedPreferences() {
    ArrayList<SharedPreferenceObject> sharedPreferenceObjectArrayList =
        SharedPreferenceReader.getAllSharedPreferences(this);
    getSharedPreference(sharedPreferenceObjectArrayList);
  }

  /**
   * This function is for loading all the shared preferences of a file
   *
   * @param fileName
   */
  private void loadSharedPreferencesFromFile(String fileName) {
    ArrayList<SharedPreferenceObject> sharedPreferenceObjectArrayList =
        SharedPreferenceReader.getSharedPreferencesBaseOnFileName(this, fileName);
    getSharedPreference(sharedPreferenceObjectArrayList);
  }

  /**
   * This function is used for loading all the shared preferences files.
   */
  private void loadSharedPreferencesFiles() {
    List sharedPreferencesFileList = SharedPreferenceReader.getSharedPreferencesTags(this);
    if (Utils.isEmpty(sharedPreferencesFileList)) {
      return;
    }
    IconWithTextListAdapter
        adapter = new IconWithTextListAdapter(sharedPreferencesFileList, this);
    sharedPreferencesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    sharedPreferencesRecylerView.setAdapter(adapter);
    setActionBarTitle(null);
  }

  private void getSharedPreference(ArrayList sharedPreferenceObjectArrayList) {
    setActionBarTitle(sharedPreferenceObjectArrayList);
    if (Utils.isEmpty(sharedPreferenceObjectArrayList)) {
      handleError(ErrorType.NO_SHARED_PREFERENCES_FOUND);
      return;
    }
    sharedPreferencesRecylerView.setVisibility(View.VISIBLE);
    errorHandlerLayout.setVisibility(View.GONE);

    sharedPreferenceObjectArrayList.add(0, getSharedPreferenceHeadersInList());
    SharedPreferencesListAdapter
        adapter = new SharedPreferencesListAdapter(sharedPreferenceObjectArrayList, this,
        getRecyleViewWidthList(), this);
    sharedPreferencesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    sharedPreferencesRecylerView.setAdapter(adapter);
  }

  private ArrayList getSharedPreferenceHeadersInList() {
    ArrayList arrayList = new ArrayList();
    arrayList.add(getResources().getString(R.string
        .com_awesomedroidapps_inappstoragereader_sharedPreference_key));
    arrayList.add(getResources().getString(R.string
        .com_awesomedroidapps_inappstoragereader_sharedPreference_value));
    arrayList.add(getResources().getString(R.string
        .com_awesomedroidapps_inappstoragereader_sharedPreference_type));
    return arrayList;
  }

  /**
   * Handle Errors .
   *
   * @param errorType
   */
  @Override
  public void handleError(ErrorType errorType) {
    sharedPreferencesRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.VISIBLE);
    ErrorMessageHandler handler = new ErrorMessageHandler();
    handler.handleError(errorType, errorHandlerLayout);
  }

  /**
   * Callback for click on pop up items.
   *
   * @param item
   * @return
   */
  @Override
  public boolean onMenuItemClick(MenuItem item) {
    if (item == null) {
      return false;
    }

    //The view to show the user all the shared preferences at once.
    if (item.getItemId() == R.id.shared_preferences_all) {
      item.setChecked(item.isChecked());
      loadAllSharedPreferences();
      isFileModeEnabled = false;
      return true;
    }
    //The view to show the user all the shared preferences files.
    else if (item.getItemId() == R.id.shared_preferences_file) {
      item.setChecked(item.isChecked());
      loadSharedPreferencesFiles();
      isFileModeEnabled = true;
      return true;
    }
    return onMenuItemClick(item);
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
    int size = Constants.ZERO_INDEX;
    if (!Utils.isEmpty(sharedPreferenceObjectList)) {
      size = sharedPreferenceObjectList.size();
    }
    Utils.setActionBarTitle(getSupportActionBar(), sharedPreferenceTitle, size);
  }

  @Override
  public void onItemClicked(AppDataStorageItem appDataStorageItem) {
    Intent intent = new Intent(this, SharedPreferencesActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_FILE_NAME, appDataStorageItem.getStorageName());
    bundle.putBoolean(Constants.BUNDLE_DISPLAY_FILTER, false);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void onDataItemClicked(String data) {
    if (Utils.isEmpty(data)) {
      String toastMessage =
          getResources().getString(R.string.com_awesomedroidapps_inappstoragereader_item_empty);
      Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
      return;
    }
    DataItemDialogFragment dataItemDialogFragment = DataItemDialogFragment.newInstance(data);
    dataItemDialogFragment.show(getSupportFragmentManager(), "dialog");
  }
}
