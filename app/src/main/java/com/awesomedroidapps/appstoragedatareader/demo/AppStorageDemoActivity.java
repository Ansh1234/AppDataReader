package com.awesomedroidapps.appstoragedatareader.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.awesomedroidapps.appstoragedatareader.demo.entity.PersonInfo;
import com.awesomedroidapps.inappstoragereader.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppStorageDemoActivity extends AppCompatActivity implements
    AdapterView.OnItemClickListener {

  //Views in the database
  @BindView(R.id.database_container)
  LinearLayout databaseContainer;
  @BindView(R.id.first_name)
  EditText firstName;
  @BindView(R.id.last_name)
  EditText lastName;
  @BindView(R.id.address)
  EditText address;
  @BindView(R.id.submit)
  Button submit;

  //Views in the shared preference.
  @BindView(R.id.shared_preference_container)
  LinearLayout sharedPreferenceContainer;
  @BindView(R.id.shared_preference_key)
  EditText sharedPreferenceKey;
  @BindView(R.id.shared_preference_value)
  EditText sharedPreferenceValue;
  @BindView(R.id.shared_preference_spinner)
  Spinner sharedPreferenceSpinner;

  private int currentView = Constants.DATABASE_VIEW;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appstorage_demo);
    ButterKnife.bind(this);
    showSharedPreferences();
    preFillDatabase();
    saveInSharedPreferences();

    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
        getSpinnerArray());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sharedPreferenceSpinner.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.appstorage_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    if (item.getItemId() == R.id.menu_table) {
      currentView = Constants.DATABASE_VIEW;
      showTable();
      return true;
    } else if (item.getItemId() == R.id.menu_shared_preference) {
      currentView = Constants.SHARED_PREFERENCE_VIEW;
      showSharedPreferences();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showTable() {
    databaseContainer.setVisibility(View.VISIBLE);
    sharedPreferenceContainer.setVisibility(View.GONE);
  }

  private void showSharedPreferences() {
    sharedPreferenceContainer.setVisibility(View.VISIBLE);
    databaseContainer.setVisibility(View.GONE);
  }

  private void saveInSharedPreferences() {

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context
        .MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("name", "anshul");
    editor.putString("age", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref2", Context
        .MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name", "anshul");
    editor.putString("age", "24");
    editor.putString("NULL", null);
    editor.putStringSet("NULL SET", null);
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_APPEND);
    editor = sharedPreferences.edit();
    editor.putString("name1",
        "anshul FlowR is a wrapper class around the Fragment Manager. It’s mainly used to navigate between different fragments easily while providing a wide range of functionality. The following are the functionalities provided by the Flowr:");
    editor.putString("age2", "24");
    Set set = new HashSet<>();
    set.add("set1");
    set.add("set2");
    set.add("set3");
    editor.putStringSet("SET", set);
    editor.commit();

    sharedPreferences =
        getSharedPreferences("MySharedPref3", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name2", "anshul");
    editor.putString("age3", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name3", "anshul");
    editor.putString("age4", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name4", "anshul");
    editor.putString("age5", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name5", "anshul");
    editor.putString("age6", "24");
    editor.commit();

    sharedPreferences = getSharedPreferences("MySharedPref3", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name6", "anshul");
    editor.putString("age7", "24");
    editor.commit();


  }

  @OnClick(R.id.submit)
  public void submit() {

    switch (currentView) {
      case Constants.DATABASE_VIEW:
        enterValuesIntoDB();
        break;
      case Constants.SHARED_PREFERENCE_VIEW:
        enterValuesIntoSharedPreferences();
        break;
    }
  }

  private void enterValuesIntoDB() {
    String firstNameStr = firstName.getText().toString();
    String lastNameStr = lastName.getText().toString();
    String addressStr = address.getText().toString();

    if (Utils.isEmpty(firstNameStr) && Utils.isEmpty(lastNameStr) && Utils.isEmpty(addressStr)) {
      showToast(string(R.string.table_empty_values_error_msg));
      return;
    }

    PersonInfo personInfo = new PersonInfo();
    personInfo.setFirstName(firstNameStr);
    personInfo.setLastName(lastNameStr);
    personInfo.setAddress(addressStr);
    long insertResponse = insertIntoDatabase(personInfo);
    if (insertResponse == Constants.INVALID_INSERT_RESPONSE) {
      showToast(string(R.string.table_insert_failure));
    } else {
      showToast(string(R.string.table_insert_success));
    }
  }

  private void enterValuesIntoSharedPreferences() {
    String keyStr = sharedPreferenceKey.getText().toString();
    String valueStr = sharedPreferenceValue.getText().toString();

    if (Utils.isEmpty(keyStr) || Utils.isEmpty(valueStr)) {
      showToast(getString(R.string.shared_preference_key_value_error));
      return;
    }

    Object selectSpinnerObject = sharedPreferenceSpinner.getSelectedItem();

    if (!(selectSpinnerObject instanceof String)) {
      return;
    }

    SharedPreferenceDataType selectedDataType = SharedPreferenceDataType.getDataType((String)
        selectSpinnerObject);

    if (selectedDataType == SharedPreferenceDataType.UNKNOWN) {
      showToast(getString(R.string.shared_preference_datatype_error));
      return;
    }
    insertIntoSharedPreference(keyStr, valueStr, selectedDataType);

  }

  private void preFillDatabase() {
    String[] defaultFirstNames = getResources().getStringArray(R.array.defaultFirstNames);
    String[] defaultLastNames = getResources().getStringArray(R.array.defaultLastNames);
    String[] defaultAddresses = getResources().getStringArray(R.array.defaultAddresses);

    for (int i = 0; i < defaultFirstNames.length; i++) {
      PersonInfo personInfo = new PersonInfo();
      personInfo.setFirstName(defaultFirstNames[i]);
      personInfo.setLastName(defaultLastNames[i]);
      personInfo.setAddress(defaultAddresses[i]);
      insertIntoDatabase(personInfo);
    }
  }

  private long insertIntoDatabase(PersonInfo personInfo) {
    DataReaderSqliteOpenHelper helper = new DataReaderSqliteOpenHelper(this);
    return helper.insert(personInfo);
  }

  private void insertIntoSharedPreference(String key, String value, SharedPreferenceDataType
      dataType) {
    if (Utils.isEmpty(key) || value == null) {
      return;
    }
    SharedPreferences sharedPreferences =
        getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context
            .MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    switch (dataType) {
      case STRING:
        editor.putString(key, value);
        break;
      case INT:
        editor.putInt(key, Integer.parseInt(value));
        break;
    }
    editor.commit();
  }

  private String string(int resId) {
    return getResources().getString(resId);
  }

  private void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  private ArrayList<String> getSpinnerArray() {
    ArrayList<String> spinnerArrayList = new ArrayList<>();
    for (SharedPreferenceDataType sharedPreferenceDataType : SharedPreferenceDataType.values()) {
      spinnerArrayList.add(sharedPreferenceDataType.getType());
    }
    return spinnerArrayList;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

  }
}
